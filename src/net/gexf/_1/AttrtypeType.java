//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.09 at 10:24:49 AM PDT 
//


package net.gexf._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attrtype-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="attrtype-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="integer"/>
 *     &lt;enumeration value="long"/>
 *     &lt;enumeration value="double"/>
 *     &lt;enumeration value="float"/>
 *     &lt;enumeration value="boolean"/>
 *     &lt;enumeration value="liststring"/>
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="anyURI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "attrtype-type")
@XmlEnum
public enum AttrtypeType {

    @XmlEnumValue("integer")
    INTEGER("integer"),
    @XmlEnumValue("long")
    LONG("long"),
    @XmlEnumValue("double")
    DOUBLE("double"),
    @XmlEnumValue("float")
    FLOAT("float"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean"),
    @XmlEnumValue("liststring")
    LISTSTRING("liststring"),
    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("anyURI")
    ANY_URI("anyURI");
    private final String value;

    AttrtypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttrtypeType fromValue(String v) {
        for (AttrtypeType c: AttrtypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
