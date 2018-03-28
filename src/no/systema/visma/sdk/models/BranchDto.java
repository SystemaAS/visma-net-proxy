/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BranchDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4640860088170989646L;
    private String corporateId;
    private CurrencyDto currency;
    private CountryDto defaultCountry;
    private AddressDto deliveryAddress;
    private ContactInfoDto deliveryContact;
    private IndustryCodeDto industryCode;
    private Date lastModifiedDateTime;
    private AddressDto mainAddress;
    private ContactInfoDto mainContact;
    private String name;
    private String number;
    private String vatRegistrationId;
    private VatZoneDto vatZone;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("corporateId")
    public String getCorporateId ( ) { 
        return this.corporateId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("corporateId")
    public void setCorporateId (String value) { 
        this.corporateId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currency")
    public CurrencyDto getCurrency ( ) { 
        return this.currency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currency")
    public void setCurrency (CurrencyDto value) { 
        this.currency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("defaultCountry")
    public CountryDto getDefaultCountry ( ) { 
        return this.defaultCountry;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("defaultCountry")
    public void setDefaultCountry (CountryDto value) { 
        this.defaultCountry = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("deliveryAddress")
    public AddressDto getDeliveryAddress ( ) { 
        return this.deliveryAddress;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("deliveryAddress")
    public void setDeliveryAddress (AddressDto value) { 
        this.deliveryAddress = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("deliveryContact")
    public ContactInfoDto getDeliveryContact ( ) { 
        return this.deliveryContact;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("deliveryContact")
    public void setDeliveryContact (ContactInfoDto value) { 
        this.deliveryContact = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("industryCode")
    public IndustryCodeDto getIndustryCode ( ) { 
        return this.industryCode;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("industryCode")
    public void setIndustryCode (IndustryCodeDto value) { 
        this.industryCode = value;
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
    @JsonGetter("mainAddress")
    public AddressDto getMainAddress ( ) { 
        return this.mainAddress;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("mainAddress")
    public void setMainAddress (AddressDto value) { 
        this.mainAddress = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("mainContact")
    public ContactInfoDto getMainContact ( ) { 
        return this.mainContact;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("mainContact")
    public void setMainContact (ContactInfoDto value) { 
        this.mainContact = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("name")
    public String getName ( ) { 
        return this.name;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("name")
    public void setName (String value) { 
        this.name = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("number")
    public String getNumber ( ) { 
        return this.number;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("number")
    public void setNumber (String value) { 
        this.number = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatRegistrationId")
    public String getVatRegistrationId ( ) { 
        return this.vatRegistrationId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatRegistrationId")
    public void setVatRegistrationId (String value) { 
        this.vatRegistrationId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("vatZone")
    public VatZoneDto getVatZone ( ) { 
        return this.vatZone;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("vatZone")
    public void setVatZone (VatZoneDto value) { 
        this.vatZone = value;
    }
 
}
 