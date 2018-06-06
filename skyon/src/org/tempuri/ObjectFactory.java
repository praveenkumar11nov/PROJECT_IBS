
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InstantDataResponseInstantDataResult_QNAME = new QName("http://tempuri.org/", "InstantDataResult");
    private final static QName _DailyDataMeterID_QNAME = new QName("http://tempuri.org/", "MeterID");
    private final static QName _DailyDataPassword_QNAME = new QName("http://tempuri.org/", "Password");
    private final static QName _DailyDataTransID_QNAME = new QName("http://tempuri.org/", "TransID");
    private final static QName _DailyDataArgFromDate_QNAME = new QName("http://tempuri.org/", "ArgFromDate");
    private final static QName _DailyDataTransDateTime_QNAME = new QName("http://tempuri.org/", "TransDateTime");
    private final static QName _DailyDataUserName_QNAME = new QName("http://tempuri.org/", "UserName");
    private final static QName _DailyDataArgToDate_QNAME = new QName("http://tempuri.org/", "ArgToDate");
    private final static QName _DailyDataResponseDailyDataResult_QNAME = new QName("http://tempuri.org/", "DailyDataResult");
    private final static QName _BillDataBillHistoryNo_QNAME = new QName("http://tempuri.org/", "BillHistoryNo");
    private final static QName _BillDataResponseBillDataResult_QNAME = new QName("http://tempuri.org/", "BillDataResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DailyData }
     * 
     */
    public DailyData createDailyData() {
        return new DailyData();
    }

    /**
     * Create an instance of {@link InstantDataResponse }
     * 
     */
    public InstantDataResponse createInstantDataResponse() {
        return new InstantDataResponse();
    }

    /**
     * Create an instance of {@link BillDataResponse }
     * 
     */
    public BillDataResponse createBillDataResponse() {
        return new BillDataResponse();
    }

    /**
     * Create an instance of {@link InstantData }
     * 
     */
    public InstantData createInstantData() {
        return new InstantData();
    }

    /**
     * Create an instance of {@link DailyDataResponse }
     * 
     */
    public DailyDataResponse createDailyDataResponse() {
        return new DailyDataResponse();
    }

    /**
     * Create an instance of {@link BillData }
     * 
     */
    public BillData createBillData() {
        return new BillData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "InstantDataResult", scope = InstantDataResponse.class)
    public JAXBElement<String> createInstantDataResponseInstantDataResult(String value) {
        return new JAXBElement<String>(_InstantDataResponseInstantDataResult_QNAME, String.class, InstantDataResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "MeterID", scope = DailyData.class)
    public JAXBElement<String> createDailyDataMeterID(String value) {
        return new JAXBElement<String>(_DailyDataMeterID_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Password", scope = DailyData.class)
    public JAXBElement<String> createDailyDataPassword(String value) {
        return new JAXBElement<String>(_DailyDataPassword_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransID", scope = DailyData.class)
    public JAXBElement<String> createDailyDataTransID(String value) {
        return new JAXBElement<String>(_DailyDataTransID_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArgFromDate", scope = DailyData.class)
    public JAXBElement<String> createDailyDataArgFromDate(String value) {
        return new JAXBElement<String>(_DailyDataArgFromDate_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransDateTime", scope = DailyData.class)
    public JAXBElement<String> createDailyDataTransDateTime(String value) {
        return new JAXBElement<String>(_DailyDataTransDateTime_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UserName", scope = DailyData.class)
    public JAXBElement<String> createDailyDataUserName(String value) {
        return new JAXBElement<String>(_DailyDataUserName_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArgToDate", scope = DailyData.class)
    public JAXBElement<String> createDailyDataArgToDate(String value) {
        return new JAXBElement<String>(_DailyDataArgToDate_QNAME, String.class, DailyData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "MeterID", scope = InstantData.class)
    public JAXBElement<String> createInstantDataMeterID(String value) {
        return new JAXBElement<String>(_DailyDataMeterID_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Password", scope = InstantData.class)
    public JAXBElement<String> createInstantDataPassword(String value) {
        return new JAXBElement<String>(_DailyDataPassword_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransID", scope = InstantData.class)
    public JAXBElement<String> createInstantDataTransID(String value) {
        return new JAXBElement<String>(_DailyDataTransID_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArgFromDate", scope = InstantData.class)
    public JAXBElement<String> createInstantDataArgFromDate(String value) {
        return new JAXBElement<String>(_DailyDataArgFromDate_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransDateTime", scope = InstantData.class)
    public JAXBElement<String> createInstantDataTransDateTime(String value) {
        return new JAXBElement<String>(_DailyDataTransDateTime_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UserName", scope = InstantData.class)
    public JAXBElement<String> createInstantDataUserName(String value) {
        return new JAXBElement<String>(_DailyDataUserName_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ArgToDate", scope = InstantData.class)
    public JAXBElement<String> createInstantDataArgToDate(String value) {
        return new JAXBElement<String>(_DailyDataArgToDate_QNAME, String.class, InstantData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "DailyDataResult", scope = DailyDataResponse.class)
    public JAXBElement<String> createDailyDataResponseDailyDataResult(String value) {
        return new JAXBElement<String>(_DailyDataResponseDailyDataResult_QNAME, String.class, DailyDataResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "MeterID", scope = BillData.class)
    public JAXBElement<String> createBillDataMeterID(String value) {
        return new JAXBElement<String>(_DailyDataMeterID_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Password", scope = BillData.class)
    public JAXBElement<String> createBillDataPassword(String value) {
        return new JAXBElement<String>(_DailyDataPassword_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransID", scope = BillData.class)
    public JAXBElement<String> createBillDataTransID(String value) {
        return new JAXBElement<String>(_DailyDataTransID_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TransDateTime", scope = BillData.class)
    public JAXBElement<String> createBillDataTransDateTime(String value) {
        return new JAXBElement<String>(_DailyDataTransDateTime_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "BillHistoryNo", scope = BillData.class)
    public JAXBElement<String> createBillDataBillHistoryNo(String value) {
        return new JAXBElement<String>(_BillDataBillHistoryNo_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UserName", scope = BillData.class)
    public JAXBElement<String> createBillDataUserName(String value) {
        return new JAXBElement<String>(_DailyDataUserName_QNAME, String.class, BillData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "BillDataResult", scope = BillDataResponse.class)
    public JAXBElement<String> createBillDataResponseBillDataResult(String value) {
        return new JAXBElement<String>(_BillDataResponseBillDataResult_QNAME, String.class, BillDataResponse.class, value);
    }

}
