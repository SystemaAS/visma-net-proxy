/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class VniToken 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4671612739734625046L;
    private String scope;
    private String token;
    private String tokenType;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("scope")
    public String getScope ( ) { 
        return this.scope;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("scope")
    public void setScope (String value) { 
        this.scope = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("token")
    public String getToken ( ) { 
        return this.token;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("token")
    public void setToken (String value) { 
        this.token = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("token_type")
    public String getTokenType ( ) { 
        return this.tokenType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("token_type")
    public void setTokenType (String value) { 
        this.tokenType = value;
    }
 
}
 