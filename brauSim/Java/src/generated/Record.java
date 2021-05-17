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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *         &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}row" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}attribute" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}record" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{@boc-eu.com/boc-is/adonis.model.document;1}interref" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rowcount" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="skippedrows" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "row",
    "attributeOrRecordOrInterref"
})
@XmlRootElement(name = "record")
public class Record {

    protected List<Row> row;
    @XmlElements({
        @XmlElement(name = "attribute", type = Attribute.class),
        @XmlElement(name = "record", type = Record.class),
        @XmlElement(name = "interref", type = Interref.class)
    })
    protected List<Object> attributeOrRecordOrInterref;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "rowcount", required = true)
    protected String rowcount;
    @XmlAttribute(name = "skippedrows", required = true)
    protected String skippedrows;

    /**
     * Gets the value of the row property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the row property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Row }
     * 
     * 
     */
    public List<Row> getRow() {
        if (row == null) {
            row = new ArrayList<Row>();
        }
        return this.row;
    }

    /**
     * Gets the value of the attributeOrRecordOrInterref property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeOrRecordOrInterref property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeOrRecordOrInterref().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attribute }
     * {@link Record }
     * {@link Interref }
     * 
     * 
     */
    public List<Object> getAttributeOrRecordOrInterref() {
        if (attributeOrRecordOrInterref == null) {
            attributeOrRecordOrInterref = new ArrayList<Object>();
        }
        return this.attributeOrRecordOrInterref;
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
     * Ruft den Wert der rowcount-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowcount() {
        return rowcount;
    }

    /**
     * Legt den Wert der rowcount-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowcount(String value) {
        this.rowcount = value;
    }

    /**
     * Ruft den Wert der skippedrows-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkippedrows() {
        return skippedrows;
    }

    /**
     * Legt den Wert der skippedrows-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkippedrows(String value) {
        this.skippedrows = value;
    }

}
