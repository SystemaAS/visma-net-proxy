/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class AddressUpdateDtoBuilder {
    //the instance to build
    private AddressUpdateDto addressUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public AddressUpdateDtoBuilder() {
        addressUpdateDto = new AddressUpdateDto();
    }

    public AddressUpdateDtoBuilder addressLine1(DtoValueString addressLine1) {
        addressUpdateDto.setAddressLine1(addressLine1);
        return this;
    }

    public AddressUpdateDtoBuilder addressLine2(DtoValueString addressLine2) {
        addressUpdateDto.setAddressLine2(addressLine2);
        return this;
    }

    public AddressUpdateDtoBuilder addressLine3(DtoValueString addressLine3) {
        addressUpdateDto.setAddressLine3(addressLine3);
        return this;
    }

    public AddressUpdateDtoBuilder city(DtoValueString city) {
        addressUpdateDto.setCity(city);
        return this;
    }

    public AddressUpdateDtoBuilder countryId(DtoValueString countryId) {
        addressUpdateDto.setCountryId(countryId);
        return this;
    }

    public AddressUpdateDtoBuilder county(DtoValueString county) {
        addressUpdateDto.setCounty(county);
        return this;
    }

    public AddressUpdateDtoBuilder postalCode(DtoValueString postalCode) {
        addressUpdateDto.setPostalCode(postalCode);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public AddressUpdateDto build() {
        return addressUpdateDto;
    }
}