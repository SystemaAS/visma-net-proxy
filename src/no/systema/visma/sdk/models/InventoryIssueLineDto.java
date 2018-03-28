/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class InventoryIssueLineDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4849496012949062586L;
    private String description;
    private Double extCost;
    private Double extPrice;
    private InventoryNumberDescriptionDto inventoryItem;
    private Integer lineNumber;
    private LocationDto location;
    private Double quantity;
    private ReasonCodeDto reasonCode;
    private TransactionTypeEnum transactionType;
    private Double unitCost;
    private Double unitPrice;
    private String uom;
    private WarehouseIdDescriptionDto warehouse;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("description")
    public String getDescription ( ) { 
        return this.description;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("description")
    public void setDescription (String value) { 
        this.description = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("extCost")
    public Double getExtCost ( ) { 
        return this.extCost;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("extCost")
    public void setExtCost (Double value) { 
        this.extCost = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("extPrice")
    public Double getExtPrice ( ) { 
        return this.extPrice;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("extPrice")
    public void setExtPrice (Double value) { 
        this.extPrice = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("inventoryItem")
    public InventoryNumberDescriptionDto getInventoryItem ( ) { 
        return this.inventoryItem;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("inventoryItem")
    public void setInventoryItem (InventoryNumberDescriptionDto value) { 
        this.inventoryItem = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lineNumber")
    public Integer getLineNumber ( ) { 
        return this.lineNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lineNumber")
    public void setLineNumber (Integer value) { 
        this.lineNumber = value;
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
    @JsonGetter("quantity")
    public Double getQuantity ( ) { 
        return this.quantity;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("quantity")
    public void setQuantity (Double value) { 
        this.quantity = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("reasonCode")
    public ReasonCodeDto getReasonCode ( ) { 
        return this.reasonCode;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("reasonCode")
    public void setReasonCode (ReasonCodeDto value) { 
        this.reasonCode = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("transactionType")
    public TransactionTypeEnum getTransactionType ( ) { 
        return this.transactionType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("transactionType")
    public void setTransactionType (TransactionTypeEnum value) { 
        this.transactionType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("unitCost")
    public Double getUnitCost ( ) { 
        return this.unitCost;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("unitCost")
    public void setUnitCost (Double value) { 
        this.unitCost = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("unitPrice")
    public Double getUnitPrice ( ) { 
        return this.unitPrice;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("unitPrice")
    public void setUnitPrice (Double value) { 
        this.unitPrice = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("uom")
    public String getUom ( ) { 
        return this.uom;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("uom")
    public void setUom (String value) { 
        this.uom = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("warehouse")
    public WarehouseIdDescriptionDto getWarehouse ( ) { 
        return this.warehouse;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("warehouse")
    public void setWarehouse (WarehouseIdDescriptionDto value) { 
        this.warehouse = value;
    }
 
}
 