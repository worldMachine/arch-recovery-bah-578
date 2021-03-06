//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.09 at 10:24:49 AM PDT 
//


package net.gexf._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Tree
 * 
 * <p>Java class for gexf-content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="gexf-content">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.gexf.net/1.2draft}meta" minOccurs="0"/>
 *         &lt;element ref="{http://www.gexf.net/1.2draft}graph"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="1.2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="variant" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gexf-content", propOrder = {
    "meta",
    "graph"
})
@XmlRootElement
public class GexfContent {

    protected MetaContent meta;
    @XmlElement(required = true)
    protected GraphContent graph;
    @XmlAttribute(required = true)
    protected String version;
    @XmlAttribute
    protected String variant;

    /**
     * Gets the value of the meta property.
     * 
     * @return
     *     possible object is
     *     {@link MetaContent }
     *     
     */
    public MetaContent getMeta() {
        return meta;
    }

    /**
     * Sets the value of the meta property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaContent }
     *     
     */
    public void setMeta(MetaContent value) {
        this.meta = value;
    }

    /**
     * Gets the value of the graph property.
     * 
     * @return
     *     possible object is
     *     {@link GraphContent }
     *     
     */
    public GraphContent getGraph() {
        return graph;
    }

    /**
     * Sets the value of the graph property.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphContent }
     *     
     */
    public void setGraph(GraphContent value) {
        this.graph = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the variant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Sets the value of the variant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariant(String value) {
        this.variant = value;
    }

}
