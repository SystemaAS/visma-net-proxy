/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class CarrierDtoBuilder {
    //the instance to build
    private CarrierDto carrierDto;

    /**
     * Default constructor to initialize the instance
     */
    public CarrierDtoBuilder() {
        carrierDto = new CarrierDto();
    }

    public CarrierDtoBuilder carrierDescription(String carrierDescription) {
        carrierDto.setCarrierDescription(carrierDescription);
        return this;
    }

    public CarrierDtoBuilder carrierId(String carrierId) {
        carrierDto.setCarrierId(carrierId);
        return this;
    }

    public CarrierDtoBuilder lastModifiedDateTime(Date lastModifiedDateTime) {
        carrierDto.setLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public CarrierDto build() {
        return carrierDto;
    }
}