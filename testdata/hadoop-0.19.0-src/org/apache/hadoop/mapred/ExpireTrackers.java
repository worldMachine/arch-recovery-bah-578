/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*******************************************************
 * JobTracker is the central location for submitting and 
 * tracking MR jobs in a network environment.
 *
 *******************************************************/
package org.apache.hadoop.mapred;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.ipc.RPC.VersionMismatch;
import org.apache.hadoop.mapred.JobHistory.Keys;
import org.apache.hadoop.mapred.JobHistory.Listener;
import org.apache.hadoop.mapred.JobHistory.Values;
import org.apache.hadoop.mapred.JobStatusChangeEvent.EventType;
import org.apache.hadoop.net.DNSToSwitchMapping;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.net.NetworkTopology;
import org.apache.hadoop.net.Node;
import org.apache.hadoop.net.NodeBase;
import org.apache.hadoop.net.ScriptBasedMapping;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.HostsFileReader;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.VersionInfo;

class ExpireTrackers implements Runnable {
    public ExpireTrackers() {
    }
    /**
     * The run method lives for the life of the JobTracker, and removes TaskTrackers
     * that have not checked in for some time.
     */
    public void run() {
      while (true) {
        try {
          //
          // Thread runs periodically to check whether trackers should be expired.
          // The sleep interval must be no more than half the maximum expiry time
          // for a task tracker.
          //
          Thread.sleep(TASKTRACKER_EXPIRY_INTERVAL / 3);

          //
          // Loop through all expired items in the queue
          //
          // Need to lock the JobTracker here since we are
          // manipulating it's data-structures via
          // ExpireTrackers.run -> JobTracker.lostTaskTracker ->
          // JobInProgress.failedTask -> JobTracker.markCompleteTaskAttempt
          // Also need to lock JobTracker before locking 'taskTracker' &
          // 'trackerExpiryQueue' to prevent deadlock:
          // @see {@link JobTracker.processHeartbeat(TaskTrackerStatus, boolean)} 
          synchronized (JobTracker.this) {
            synchronized (taskTrackers) {
              synchronized (trackerExpiryQueue) {
                long now = System.currentTimeMillis();
                TaskTrackerStatus leastRecent = null;
                while ((trackerExpiryQueue.size() > 0) &&
                       ((leastRecent = trackerExpiryQueue.first()) != null) &&
                       (now - leastRecent.getLastSeen() > TASKTRACKER_EXPIRY_INTERVAL)) {
                        
                  // Remove profile from head of queue
                  trackerExpiryQueue.remove(leastRecent);
                  String trackerName = leastRecent.getTrackerName();
                        
                  // Figure out if last-seen time should be updated, or if tracker is dead
                  TaskTrackerStatus newProfile = taskTrackers.get(leastRecent.getTrackerName());
                  // Items might leave the taskTracker set through other means; the
                  // status stored in 'taskTrackers' might be null, which means the
                  // tracker has already been destroyed.
                  if (newProfile != null) {
                    if (now - newProfile.getLastSeen() > TASKTRACKER_EXPIRY_INTERVAL) {
                      // Remove completely after marking the tasks as 'KILLED'
                      lostTaskTracker(leastRecent.getTrackerName());
                      updateTaskTrackerStatus(trackerName, null);
                    } else {
                      // Update time by inserting latest profile
                      trackerExpiryQueue.add(newProfile);
                    }
                  }
                }
              }
            }
          }
        } catch (InterruptedException iex) {
          break;
        } catch (Exception t) {
          LOG.error("Tracker Expiry Thread got exception: " +
                    StringUtils.stringifyException(t));
        }
      }
    }
        
  }