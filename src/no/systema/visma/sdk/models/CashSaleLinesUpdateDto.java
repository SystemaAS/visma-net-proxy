/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CashSaleLinesUpdateDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5638832934204191360L;
    private DtoValueString accountNumber;
    private DtoValueString deferralCode;
    private DtoValueInt32 deferralSchedule;
    private DtoValueString description;
    private DtoValueDecimal discountAmountInCurrency;
    private DtoValueDecimal discountPercent;
    private DtoValueString inventoryNumber;
    private DtoValueInt32 lineNumber;
    private DtoValueDecimal manualAmountInCurrency;
    private DtoValueBoolean manualDiscount;
    private DtoValueString note;
    private OperationEnum operation;
    private DtoValueDecimal quantity;
    private DtoValueString salesperson;
    private List<SegmentUpdateDto> subaccount;
    private DtoValueDecimal unitPriceInCurrency;
    private DtoValueString uom;
    private DtoValueString vatCodeId;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("accountNumber")
    public DtoValueString getAccountNumber ( ) { 
        return this.accountNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("accountNumber")
    public void setAccountNumber (DtoValueString value) { 
        this.accountNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("deferralCode")
    public DtoValueString getDeferralCode ( ) { 
        return this.deferralCode;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("deferralCode")
    public void setDeferralCode (DtoValueString value) { 
        this.deferralCode = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("deferralSchedule")
    public DtoValueInt32 getDeferralSchedule ( ) { 
        return this.deferralSchedule;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("deferralSchedule")
    public void setDeferralSchedule (DtoValueInt32 value) { 
        this.deferralSchedule = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("description")
    public DtoValueString getDescription ( ) { 
        return this.description;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("description")
    public void setDescription (DtoValueString value) { 
        this.description = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("discountAmountInCurrency")
    public DtoValueDecimal getDiscountAmountInCurrency ( ) { 
        return this.discountAmountInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("discountAmountInCurrency")
    public void setDiscountAmountInCurrency (DtoValueDecimal value) { 
        this.discountAmountInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("discountPercent")
    public DtoValueDecimal getDiscountPercent ( ) { 
        return this.discountPercent;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("discountPercent")
    public void setDiscountPercent (DtoValueDecimal value) { 
        this.discountPercent = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("inventoryNumber")
    public DtoValueString getInventoryNumber ( ) { 
        return this.inventoryNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("inventoryNumber")
    public void setInventoryNumber (DtoValueString value) { 
        this.inventoryNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lineNumber")
    public DtoValueInt32 getLineNumber ( ) { 
        return this.lineNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lineNumber")
    public void setLineNumber (DtoValueInt32 value) { 
        this.lineNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("manualAmountInCurrency")
    public DtoValueDecimal getManualAmountInCurrency ( ) { 
        return this.manualAmountInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("manualAmountInCurrency")
    public void setManualAmountInCurrency (DtoValueDecimal value) { 
        this.manualAmountInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("manualDiscount")
    public DtoValueBoolean getManualDiscount ( ) { 
        return this.manualDiscount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("manualDiscount")
    public void setManualDiscount (DtoValueBoolean value) { 
        this.manualDiscount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("note")
    public DtoValueString getNote ( ) { 
        return this.note;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("note")
    public void setNote (DtoValueString value) { 
        this.note = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("operation")
    public OperationEnum getOperation ( ) { 
        return this.operation;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("operation")
    public void setOperation (OperationEnum value) { 
        this.operation = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("quantity")
    public DtoValueDecimal getQuantity ( ) { 
        return this.quantity;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("quantity")
    public void setQuantity (DtoValueDecimal value) { 
        this.quantity = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("salesperson")
    public DtoValueString getSalesperson ( ) { 
        return this.salesperson;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("salesperson")
    public void setSalesperson (DtoValueString value) { 
        this.salesperson = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("subaccount")
    public List<SegmentUpdateDto> getSubaccount ( ) { 
        return this.subaccount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("subaccount")
    public void setSubaccount (List<SegmentUpdateDto> value) { 
        this.subaccount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("unitPriceInCurrency")
    public DtoValueDecimal getUnitPriceInCurrency ( ) { 
        return this.unitPriceInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("unitPriceInCurrency")
    public void setUnitPriceInCurrency (DtoValueDecimal value) { 
        this.unitPriceInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("uom")
    public DtoValueString getUom ( ) { 
        return this.uom;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("uom")
    public void setUom (DtoValueString value) { 
        this.uom = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatCodeId")
    public DtoValueString getVatCodeId ( ) { 
        return this.vatCodeId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatCodeId")
    public void setVatCodeId (DtoValueString value) { 
        this.vatCodeId = value;
    }
 
}
 