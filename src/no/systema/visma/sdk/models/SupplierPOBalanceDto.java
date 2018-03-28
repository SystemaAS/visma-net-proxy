/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SupplierPOBalanceDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4900828401654133791L;
    private Date lastModifiedDateTime;
    private SupplierDescriptionDto supplier;
    private Double totalClosedPOLineTotal;
    private Double totalClosedPOOrderTotal;
    private Double totalOpenPOLineTotal;
    private Double totalOpenPOOrderTotal;
    private Double totalPOOnHoldLineTotal;
    private Double totalPOOnHoldOrderTotal;
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
    @JsonGetter("supplier")
    public SupplierDescriptionDto getSupplier ( ) { 
        return this.supplier;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("supplier")
    public void setSupplier (SupplierDescriptionDto value) { 
        this.supplier = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalClosedPOLineTotal")
    public Double getTotalClosedPOLineTotal ( ) { 
        return this.totalClosedPOLineTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalClosedPOLineTotal")
    public void setTotalClosedPOLineTotal (Double value) { 
        this.totalClosedPOLineTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalClosedPOOrderTotal")
    public Double getTotalClosedPOOrderTotal ( ) { 
        return this.totalClosedPOOrderTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalClosedPOOrderTotal")
    public void setTotalClosedPOOrderTotal (Double value) { 
        this.totalClosedPOOrderTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalOpenPOLineTotal")
    public Double getTotalOpenPOLineTotal ( ) { 
        return this.totalOpenPOLineTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalOpenPOLineTotal")
    public void setTotalOpenPOLineTotal (Double value) { 
        this.totalOpenPOLineTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalOpenPOOrderTotal")
    public Double getTotalOpenPOOrderTotal ( ) { 
        return this.totalOpenPOOrderTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalOpenPOOrderTotal")
    public void setTotalOpenPOOrderTotal (Double value) { 
        this.totalOpenPOOrderTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalPOOnHoldLineTotal")
    public Double getTotalPOOnHoldLineTotal ( ) { 
        return this.totalPOOnHoldLineTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalPOOnHoldLineTotal")
    public void setTotalPOOnHoldLineTotal (Double value) { 
        this.totalPOOnHoldLineTotal = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("totalPOOnHoldOrderTotal")
    public Double getTotalPOOnHoldOrderTotal ( ) { 
        return this.totalPOOnHoldOrderTotal;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("totalPOOnHoldOrderTotal")
    public void setTotalPOOnHoldOrderTotal (Double value) { 
        this.totalPOOnHoldOrderTotal = value;
    }
 
}
 