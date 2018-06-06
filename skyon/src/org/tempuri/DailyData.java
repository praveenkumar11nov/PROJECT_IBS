
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UtilityID" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *         &lt;element name="MeterID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArgFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArgToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transID",
    "transDateTime",
    "userName",
    "password",
    "utilityID",
    "meterID",
    "argFromDate",
    "argToDate"
})
@XmlRootElement(name = "DailyData")
public class DailyData {

    @XmlElementRef(name = "TransID", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> transID;
    @XmlElementRef(name = "TransDateTime", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> transDateTime;
    @XmlElementRef(name = "UserName", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> userName;
    @XmlElementRef(name = "Password", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> password;
    @XmlElement(name = "UtilityID")
    @XmlSchemaType(name = "unsignedInt")
    protected Long utilityID;
    @XmlElementRef(name = "MeterID", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> meterID;
    @XmlElementRef(name = "ArgFromDate", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> argFromDate;
    @XmlElementRef(name = "ArgToDate", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> argToDate;

    /**
     * Gets the value of the transID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTransID() {
        return transID;
    }

    /**
     * Sets the value of the transID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTransID(JAXBElement<String> value) {
        this.transID = value;
    }

    /**
     * Gets the value of the transDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTransDateTime() {
        return transDateTime;
    }

    /**
     * Sets the value of the transDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTransDateTime(JAXBElement<String> value) {
        this.transDateTime = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserName(JAXBElement<String> value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPassword(JAXBElement<String> value) {
        this.password = value;
    }

    /**
     * Gets the value of the utilityID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUtilityID() {
        return utilityID;
    }

    /**
     * Sets the value of the utilityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUtilityID(Long value) {
        this.utilityID = value;
    }

    /**
     * Gets the value of the meterID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMeterID() {
        return meterID;
    }

    /**
     * Sets the value of the meterID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMeterID(JAXBElement<String> value) {
        this.meterID = value;
    }

    /**
     * Gets the value of the argFromDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArgFromDate() {
        return argFromDate;
    }

    /**
     * Sets the value of the argFromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArgFromDate(JAXBElement<String> value) {
        this.argFromDate = value;
    }

    /**
     * Gets the value of the argToDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArgToDate() {
        return argToDate;
    }

    /**
     * Sets the value of the argToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArgToDate(JAXBElement<String> value) {
        this.argToDate = value;
    }

}
