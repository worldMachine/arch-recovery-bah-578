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
/**************************************************
 * DatanodeDescriptor tracks stats on a given DataNode,
 * such as available storage capacity, last update time, etc.,
 * and maintains a set of blocks stored on the datanode. 
 *
 * This data structure is a data structure that is internal
 * to the namenode. It is *not* sent over-the-wire to the Client
 * or the Datnodes. Neither is it stored persistently in the
 * fsImage.

 **************************************************/
package org.apache.hadoop.hdfs.server.namenode;
import java.io.DataInput;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockListAsLongs;
import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.server.namenode.BlocksMap.BlockInfo;
import org.apache.hadoop.hdfs.server.protocol.BlockCommand;
import org.apache.hadoop.hdfs.server.protocol.DatanodeProtocol;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.UTF8;
import org.apache.hadoop.io.WritableUtils;

static private class BlockIterator implements Iterator<Block> {
    private BlockInfo current;
    private DatanodeDescriptor node;
      
    BlockIterator(BlockInfo head, DatanodeDescriptor dn) {
      this.current = head;
      this.node = dn;
    }

    public boolean hasNext() {
      return current != null;
    }

    public BlockInfo next() {
      BlockInfo res = current;
      current = current.getNext(current.findDatanode(node));
      return res;
    }

    public void remove()  {
      throw new UnsupportedOperationException("Sorry. can't remove.");
    }
  }