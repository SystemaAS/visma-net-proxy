/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class DtoValueNullableDateTime 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5362746260564933244L;
    private Date value;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("value")
    public Date getValue ( ) { 
        return this.value;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("value")
    public void setValue (Date value) { 
        this.value = value;
    }
 
}
 