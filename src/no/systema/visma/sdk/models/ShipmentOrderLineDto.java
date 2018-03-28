/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ShipmentOrderLineDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5226512764956722278L;
    private String inventoryDocType;
    private String inventoryRefNbr;
    private String invoiceNbr;
    private String invoiceType;
    private String orderNbr;
    private String orderType;
    private Double shippedQty;
    private Double shippedVolume;
    private Double shippedWeight;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("inventoryDocType")
    public String getInventoryDocType ( ) { 
        return this.inventoryDocType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("inventoryDocType")
    public void setInventoryDocType (String value) { 
        this.inventoryDocType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("inventoryRefNbr")
    public String getInventoryRefNbr ( ) { 
        return this.inventoryRefNbr;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("inventoryRefNbr")
    public void setInventoryRefNbr (String value) { 
        this.inventoryRefNbr = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("invoiceNbr")
    public String getInvoiceNbr ( ) { 
        return this.invoiceNbr;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("invoiceNbr")
    public void setInvoiceNbr (String value) { 
        this.invoiceNbr = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("invoiceType")
    public String getInvoiceType ( ) { 
        return this.invoiceType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("invoiceType")
    public void setInvoiceType (String value) { 
        this.invoiceType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("orderNbr")
    public String getOrderNbr ( ) { 
        return this.orderNbr;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("orderNbr")
    public void setOrderNbr (String value) { 
        this.orderNbr = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("orderType")
    public String getOrderType ( ) { 
        return this.orderType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("orderType")
    public void setOrderType (String value) { 
        this.orderType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("shippedQty")
    public Double getShippedQty ( ) { 
        return this.shippedQty;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("shippedQty")
    public void setShippedQty (Double value) { 
        this.shippedQty = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("shippedVolume")
    public Double getShippedVolume ( ) { 
        return this.shippedVolume;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("shippedVolume")
    public void setShippedVolume (Double value) { 
        this.shippedVolume = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("shippedWeight")
    public Double getShippedWeight ( ) { 
        return this.shippedWeight;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("shippedWeight")
    public void setShippedWeight (Double value) { 
        this.shippedWeight = value;
    }
 
}
 