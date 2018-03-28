/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class DtoSegmentValueUpdate 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5293405885624166486L;
    private DtoValueBoolean active;
    private DtoValueString description;
    private OperationEnum operation;
    private DtoValueNullableGuid publicId;
    private String value;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("active")
    public DtoValueBoolean getActive ( ) { 
        return this.active;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("active")
    public void setActive (DtoValueBoolean value) { 
        this.active = value;
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
    @JsonGetter("publicId")
    public DtoValueNullableGuid getPublicId ( ) { 
        return this.publicId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("publicId")
    public void setPublicId (DtoValueNullableGuid value) { 
        this.publicId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("value")
    public String getValue ( ) { 
        return this.value;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("value")
    public void setValue (String value) { 
        this.value = value;
    }
 
}
 