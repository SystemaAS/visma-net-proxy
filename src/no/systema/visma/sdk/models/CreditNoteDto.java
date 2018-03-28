/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CreditNoteDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5209379310043963608L;
    private Double amount;
    private Double amountInCurrency;
    private List<AttachmentDto> attachments;
    private Double balance;
    private Double balanceInCurrency;
    private Double cashDiscount;
    private Double cashDiscountInCurrency;
    private Date createdDateTime;
    private String currencyId;
    private CustomerNumberDto customer;
    private String customerRefNumber;
    private Double detailTotal;
    private Double detailTotalInCurrency;
    private Date documentDate;
    private DocumentTypeEnum documentType;
    private String externalReference;
    private String financialPeriod;
    private Boolean hold;
    private List<CreditNoteLineDto> invoiceLines;
    private String invoiceText;
    private Date lastModifiedDateTime;
    private LocationDto location;
    private String note;
    private PaymentMethodIdDescriptionDto paymentMethod;
    private String postPeriod;
    private String referenceNumber;
    private String salesPersonDescr;
    private Integer salesPersonID;
    private StatusEnum status;
    private Double vatExemptTotal;
    private Double vatExemptTotalInCurrency;
    private Double vatTaxableTotal;
    private Double vatTaxableTotalInCurrency;
    private Double vatTotal;
    private Double vatTotalInCurrency;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("amount")
    public Double getAmount ( ) { 
        return this.amount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("amount")
    public void setAmount (Double value) { 
        this.amount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("amountInCurrency")
    public Double getAmountInCurrency ( ) { 
        return this.amountInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("amountInCurrency")
    public void setAmountInCurrency (Double value) { 
        this.amountInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("attachments")
    public List<AttachmentDto> getAttachments ( ) { 
        return this.attachments;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("attachments")
    public void setAttachments (List<AttachmentDto> value) { 
        this.attachments = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("balance")
    public Double getBalance ( ) { 
        return this.balance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("balance")
    public void setBalance (Double value) { 
        this.balance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("balanceInCurrency")
    public Double getBalanceInCurrency ( ) { 
        return this.balanceInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("balanceInCurrency")
    public void setBalanceInCurrency (Double value) { 
        this.balanceInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("cashDiscount")
    public Double getCashDiscount ( ) { 
        return this.cashDiscount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("cashDiscount")
    public void setCashDiscount (Double value) { 
        this.cashDiscount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("cashDiscountInCurrency")
    public Double getCashDiscountInCurrency ( ) { 
        return this.cashDiscountInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("cashDiscountInCurrency")
    public void setCashDiscountInCurrency (Double value) { 
        this.cashDiscountInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("createdDateTime")
    public Date getCreatedDateTime ( ) { 
        return this.createdDateTime;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("createdDateTime")
    public void setCreatedDateTime (Date value) { 
        this.createdDateTime = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currencyId")
    public String getCurrencyId ( ) { 
        return this.currencyId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currencyId")
    public void setCurrencyId (String value) { 
        this.currencyId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("customer")
    public CustomerNumberDto getCustomer ( ) { 
        return this.customer;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("customer")
    public void setCustomer (CustomerNumberDto value) { 
        this.customer = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("customerRefNumber")
    public String getCustomerRefNumber ( ) { 
        return this.customerRefNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("customerRefNumber")
    public void setCustomerRefNumber (String value) { 
        this.customerRefNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("detailTotal")
    public Double getDetailTotal ( ) { 
        return this.detailTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("detailTotal")
    public void setDetailTotal (Double value) { 
        this.detailTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("detailTotalInCurrency")
    public Double getDetailTotalInCurrency ( ) { 
        return this.detailTotalInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("detailTotalInCurrency")
    public void setDetailTotalInCurrency (Double value) { 
        this.detailTotalInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("documentDate")
    public Date getDocumentDate ( ) { 
        return this.documentDate;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("documentDate")
    public void setDocumentDate (Date value) { 
        this.documentDate = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("documentType")
    public DocumentTypeEnum getDocumentType ( ) { 
        return this.documentType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("documentType")
    public void setDocumentType (DocumentTypeEnum value) { 
        this.documentType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("externalReference")
    public String getExternalReference ( ) { 
        return this.externalReference;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("externalReference")
    public void setExternalReference (String value) { 
        this.externalReference = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("financialPeriod")
    public String getFinancialPeriod ( ) { 
        return this.financialPeriod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("financialPeriod")
    public void setFinancialPeriod (String value) { 
        this.financialPeriod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("hold")
    public Boolean getHold ( ) { 
        return this.hold;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("hold")
    public void setHold (Boolean value) { 
        this.hold = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("invoiceLines")
    public List<CreditNoteLineDto> getInvoiceLines ( ) { 
        return this.invoiceLines;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("invoiceLines")
    public void setInvoiceLines (List<CreditNoteLineDto> value) { 
        this.invoiceLines = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("invoiceText")
    public String getInvoiceText ( ) { 
        return this.invoiceText;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("invoiceText")
    public void setInvoiceText (String value) { 
        this.invoiceText = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lastModifiedDateTime")
    public Date getLastModifiedDateTime ( ) { 
        return this.lastModifiedDateTime;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lastModifiedDateTime")
    public void setLastModifiedDateTime (Date value) { 
        this.lastModifiedDateTime = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("location")
    public LocationDto getLocation ( ) { 
        return this.location;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("location")
    public void setLocation (LocationDto value) { 
        this.location = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("note")
    public String getNote ( ) { 
        return this.note;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("note")
    public void setNote (String value) { 
        this.note = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("paymentMethod")
    public PaymentMethodIdDescriptionDto getPaymentMethod ( ) { 
        return this.paymentMethod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("paymentMethod")
    public void setPaymentMethod (PaymentMethodIdDescriptionDto value) { 
        this.paymentMethod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("postPeriod")
    public String getPostPeriod ( ) { 
        return this.postPeriod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("postPeriod")
    public void setPostPeriod (String value) { 
        this.postPeriod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("referenceNumber")
    public String getReferenceNumber ( ) { 
        return this.referenceNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("referenceNumber")
    public void setReferenceNumber (String value) { 
        this.referenceNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("salesPersonDescr")
    public String getSalesPersonDescr ( ) { 
        return this.salesPersonDescr;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("salesPersonDescr")
    public void setSalesPersonDescr (String value) { 
        this.salesPersonDescr = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("salesPersonID")
    public Integer getSalesPersonID ( ) { 
        return this.salesPersonID;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("salesPersonID")
    public void setSalesPersonID (Integer value) { 
        this.salesPersonID = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("status")
    public StatusEnum getStatus ( ) { 
        return this.status;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("status")
    public void setStatus (StatusEnum value) { 
        this.status = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatExemptTotal")
    public Double getVatExemptTotal ( ) { 
        return this.vatExemptTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatExemptTotal")
    public void setVatExemptTotal (Double value) { 
        this.vatExemptTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatExemptTotalInCurrency")
    public Double getVatExemptTotalInCurrency ( ) { 
        return this.vatExemptTotalInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatExemptTotalInCurrency")
    public void setVatExemptTotalInCurrency (Double value) { 
        this.vatExemptTotalInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatTaxableTotal")
    public Double getVatTaxableTotal ( ) { 
        return this.vatTaxableTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatTaxableTotal")
    public void setVatTaxableTotal (Double value) { 
        this.vatTaxableTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatTaxableTotalInCurrency")
    public Double getVatTaxableTotalInCurrency ( ) { 
        return this.vatTaxableTotalInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatTaxableTotalInCurrency")
    public void setVatTaxableTotalInCurrency (Double value) { 
        this.vatTaxableTotalInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatTotal")
    public Double getVatTotal ( ) { 
        return this.vatTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatTotal")
    public void setVatTotal (Double value) { 
        this.vatTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatTotalInCurrency")
    public Double getVatTotalInCurrency ( ) { 
        return this.vatTotalInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatTotalInCurrency")
    public void setVatTotalInCurrency (Double value) { 
        this.vatTotalInCurrency = value;
    }
 
}
 