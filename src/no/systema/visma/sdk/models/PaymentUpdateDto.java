/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PaymentUpdateDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5075891476478027466L;
    private DtoValueDateTime applicationDate;
    private DtoValueString applicationPeriod;
    private DtoValueString cashAccount;
    private DtoValueString currency;
    private DtoValueString customer;
    private DtoValueBoolean hold;
    private DtoValueString invoiceText;
    private DtoValueString location;
    private DtoValueDecimal paymentAmount;
    private List<PaymentLinesUpdateDto> paymentLines;
    private DtoValueString paymentMethod;
    private DtoValueString paymentRef;
    private DtoValueNullablePaymentTypes type;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("applicationDate")
    public DtoValueDateTime getApplicationDate ( ) { 
        return this.applicationDate;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("applicationDate")
    public void setApplicationDate (DtoValueDateTime value) { 
        this.applicationDate = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("applicationPeriod")
    public DtoValueString getApplicationPeriod ( ) { 
        return this.applicationPeriod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("applicationPeriod")
    public void setApplicationPeriod (DtoValueString value) { 
        this.applicationPeriod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("cashAccount")
    public DtoValueString getCashAccount ( ) { 
        return this.cashAccount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("cashAccount")
    public void setCashAccount (DtoValueString value) { 
        this.cashAccount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currency")
    public DtoValueString getCurrency ( ) { 
        return this.currency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currency")
    public void setCurrency (DtoValueString value) { 
        this.currency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("customer")
    public DtoValueString getCustomer ( ) { 
        return this.customer;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("customer")
    public void setCustomer (DtoValueString value) { 
        this.customer = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("hold")
    public DtoValueBoolean getHold ( ) { 
        return this.hold;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("hold")
    public void setHold (DtoValueBoolean value) { 
        this.hold = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("invoiceText")
    public DtoValueString getInvoiceText ( ) { 
        return this.invoiceText;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("invoiceText")
    public void setInvoiceText (DtoValueString value) { 
        this.invoiceText = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("location")
    public DtoValueString getLocation ( ) { 
        return this.location;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("location")
    public void setLocation (DtoValueString value) { 
        this.location = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("paymentAmount")
    public DtoValueDecimal getPaymentAmount ( ) { 
        return this.paymentAmount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("paymentAmount")
    public void setPaymentAmount (DtoValueDecimal value) { 
        this.paymentAmount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("paymentLines")
    public List<PaymentLinesUpdateDto> getPaymentLines ( ) { 
        return this.paymentLines;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("paymentLines")
    public void setPaymentLines (List<PaymentLinesUpdateDto> value) { 
        this.paymentLines = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("paymentMethod")
    public DtoValueString getPaymentMethod ( ) { 
        return this.paymentMethod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("paymentMethod")
    public void setPaymentMethod (DtoValueString value) { 
        this.paymentMethod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("paymentRef")
    public DtoValueString getPaymentRef ( ) { 
        return this.paymentRef;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("paymentRef")
    public void setPaymentRef (DtoValueString value) { 
        this.paymentRef = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("type")
    public DtoValueNullablePaymentTypes getType ( ) { 
        return this.type;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("type")
    public void setType (DtoValueNullablePaymentTypes value) { 
        this.type = value;
    }
 
}
 