/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoValueAddressUpdateDtoBuilder {
    //the instance to build
    private DtoValueAddressUpdateDto dtoValueAddressUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public DtoValueAddressUpdateDtoBuilder() {
        dtoValueAddressUpdateDto = new DtoValueAddressUpdateDto();
    }

    public DtoValueAddressUpdateDtoBuilder value(AddressUpdateDto value) {
        dtoValueAddressUpdateDto.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoValueAddressUpdateDto build() {
        return dtoValueAddressUpdateDto;
    }
}