//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.02.21 um 11:00:27 AM CET 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}attributeprofile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}attrprofdir" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}interref" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attributeprofile",
    "attrprofdir",
    "interref"
})
@XmlRootElement(name = "attrprofdir")
public class Attrprofdir {

    protected List<Attributeprofile> attributeprofile;
    protected List<Attrprofdir> attrprofdir;
    protected List<Interref> interref;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the attributeprofile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeprofile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeprofile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attributeprofile }
     * 
     * 
     */
    public List<Attributeprofile> getAttributeprofile() {
        if (attributeprofile == null) {
            attributeprofile = new ArrayList<Attributeprofile>();
        }
        return this.attributeprofile;
    }

    /**
     * Gets the value of the attrprofdir property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attrprofdir property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttrprofdir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attrprofdir }
     * 
     * 
     */
    public List<Attrprofdir> getAttrprofdir() {
        if (attrprofdir == null) {
            attrprofdir = new ArrayList<Attrprofdir>();
        }
        return this.attrprofdir;
    }

    /**
     * Gets the value of the interref property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interref property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterref().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Interref }
     * 
     * 
     */
    public List<Interref> getInterref() {
        if (interref == null) {
            interref = new ArrayList<Interref>();
        }
        return this.interref;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
