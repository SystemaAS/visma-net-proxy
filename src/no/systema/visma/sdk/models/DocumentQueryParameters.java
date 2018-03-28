/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class DocumentQueryParameters 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4779635116454486298L;
    private String greaterThanValue;
    private String lastModifiedDateTime;
    private String lastModifiedDateTimeCondition;
    private Integer numberToRead;
    private String orderBy;
    private Integer released;
    private Integer skipRecords;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("greaterThanValue")
    public String getGreaterThanValue ( ) { 
        return this.greaterThanValue;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("greaterThanValue")
    public void setGreaterThanValue (String value) { 
        this.greaterThanValue = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lastModifiedDateTime")
    public String getLastModifiedDateTime ( ) { 
        return this.lastModifiedDateTime;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lastModifiedDateTime")
    public void setLastModifiedDateTime (String value) { 
        this.lastModifiedDateTime = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lastModifiedDateTimeCondition")
    public String getLastModifiedDateTimeCondition ( ) { 
        return this.lastModifiedDateTimeCondition;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lastModifiedDateTimeCondition")
    public void setLastModifiedDateTimeCondition (String value) { 
        this.lastModifiedDateTimeCondition = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("numberToRead")
    public Integer getNumberToRead ( ) { 
        return this.numberToRead;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("numberToRead")
    public void setNumberToRead (Integer value) { 
        this.numberToRead = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("orderBy")
    public String getOrderBy ( ) { 
        return this.orderBy;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("orderBy")
    public void setOrderBy (String value) { 
        this.orderBy = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("released")
    public Integer getReleased ( ) { 
        return this.released;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("released")
    public void setReleased (Integer value) { 
        this.released = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("skipRecords")
    public Integer getSkipRecords ( ) { 
        return this.skipRecords;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("skipRecords")
    public void setSkipRecords (Integer value) { 
        this.skipRecords = value;
    }
 
}
 