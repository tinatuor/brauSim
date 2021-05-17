//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2021.02.21 um 11:07:03 AM CET 
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
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}attrprofdir" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}attributeprofiles" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}models" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}modelgroups" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="time" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="database" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="username" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="adoversion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attrprofdir",
    "attributeprofiles",
    "models",
    "modelgroups"
})
@XmlRootElement(name = "adoxml")
public class Adoxml {

    protected List<Attrprofdir> attrprofdir;
    protected List<Attributeprofiles> attributeprofiles;
    protected List<Models> models;
    protected List<Modelgroups> modelgroups;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "date", required = true)
    protected String date;
    @XmlAttribute(name = "time", required = true)
    protected String time;
    @XmlAttribute(name = "database", required = true)
    protected String database;
    @XmlAttribute(name = "username", required = true)
    protected String username;
    @XmlAttribute(name = "adoversion", required = true)
    protected String adoversion;

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
     * Gets the value of the attributeprofiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeprofiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeprofiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attributeprofiles }
     * 
     * 
     */
    public List<Attributeprofiles> getAttributeprofiles() {
        if (attributeprofiles == null) {
            attributeprofiles = new ArrayList<Attributeprofiles>();
        }
        return this.attributeprofiles;
    }

    /**
     * Gets the value of the models property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the models property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Models }
     * 
     * 
     */
    public List<Models> getModels() {
        if (models == null) {
            models = new ArrayList<Models>();
        }
        return this.models;
    }

    /**
     * Gets the value of the modelgroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelgroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelgroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Modelgroups }
     * 
     * 
     */
    public List<Modelgroups> getModelgroups() {
        if (modelgroups == null) {
            modelgroups = new ArrayList<Modelgroups>();
        }
        return this.modelgroups;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
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
     * Legt den Wert der version-Eigenschaft fest.
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
     * Ruft den Wert der date-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Legt den Wert der date-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Ruft den Wert der time-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTime() {
        return time;
    }

    /**
     * Legt den Wert der time-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Ruft den Wert der database-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Legt den Wert der database-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabase(String value) {
        this.database = value;
    }

    /**
     * Ruft den Wert der username-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Legt den Wert der username-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Ruft den Wert der adoversion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdoversion() {
        return adoversion;
    }

    /**
     * Legt den Wert der adoversion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdoversion(String value) {
        this.adoversion = value;
    }

}
