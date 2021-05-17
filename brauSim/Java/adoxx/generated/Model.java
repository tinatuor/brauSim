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
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}modelattributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}instance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}connector" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="modeltype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="libtype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="applib" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "modelattributes",
    "instance",
    "connector"
})
@XmlRootElement(name = "model")
public class Model {

    protected List<Modelattributes> modelattributes;
    protected List<Instance> instance;
    protected List<Connector> connector;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "modeltype", required = true)
    protected String modeltype;
    @XmlAttribute(name = "libtype", required = true)
    protected String libtype;
    @XmlAttribute(name = "applib", required = true)
    protected String applib;

    /**
     * Gets the value of the modelattributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelattributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelattributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Modelattributes }
     * 
     * 
     */
    public List<Modelattributes> getModelattributes() {
        if (modelattributes == null) {
            modelattributes = new ArrayList<Modelattributes>();
        }
        return this.modelattributes;
    }

    /**
     * Gets the value of the instance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Instance }
     * 
     * 
     */
    public List<Instance> getInstance() {
        if (instance == null) {
            instance = new ArrayList<Instance>();
        }
        return this.instance;
    }

    /**
     * Gets the value of the connector property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the connector property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnector().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Connector }
     * 
     * 
     */
    public List<Connector> getConnector() {
        if (connector == null) {
            connector = new ArrayList<Connector>();
        }
        return this.connector;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * Ruft den Wert der modeltype-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeltype() {
        return modeltype;
    }

    /**
     * Legt den Wert der modeltype-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeltype(String value) {
        this.modeltype = value;
    }

    /**
     * Ruft den Wert der libtype-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibtype() {
        return libtype;
    }

    /**
     * Legt den Wert der libtype-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibtype(String value) {
        this.libtype = value;
    }

    /**
     * Ruft den Wert der applib-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplib() {
        return applib;
    }

    /**
     * Legt den Wert der applib-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplib(String value) {
        this.applib = value;
    }

}
