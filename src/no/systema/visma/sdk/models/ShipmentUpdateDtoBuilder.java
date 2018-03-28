/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class ShipmentUpdateDtoBuilder {
    //the instance to build
    private ShipmentUpdateDto shipmentUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public ShipmentUpdateDtoBuilder() {
        shipmentUpdateDto = new ShipmentUpdateDto();
    }

    public ShipmentUpdateDtoBuilder addressLine1(DtoValueString addressLine1) {
        shipmentUpdateDto.setAddressLine1(addressLine1);
        return this;
    }

    public ShipmentUpdateDtoBuilder addressLine2(DtoValueString addressLine2) {
        shipmentUpdateDto.setAddressLine2(addressLine2);
        return this;
    }

    public ShipmentUpdateDtoBuilder attention(DtoValueString attention) {
        shipmentUpdateDto.setAttention(attention);
        return this;
    }

    public ShipmentUpdateDtoBuilder businessName(DtoValueString businessName) {
        shipmentUpdateDto.setBusinessName(businessName);
        return this;
    }

    public ShipmentUpdateDtoBuilder city(DtoValueString city) {
        shipmentUpdateDto.setCity(city);
        return this;
    }

    public ShipmentUpdateDtoBuilder container(DtoValueNullableBoolean container) {
        shipmentUpdateDto.setContainer(container);
        return this;
    }

    public ShipmentUpdateDtoBuilder controlQuantity(DtoValueNullableDecimal controlQuantity) {
        shipmentUpdateDto.setControlQuantity(controlQuantity);
        return this;
    }

    public ShipmentUpdateDtoBuilder country(DtoValueString country) {
        shipmentUpdateDto.setCountry(country);
        return this;
    }

    public ShipmentUpdateDtoBuilder county(DtoValueString county) {
        shipmentUpdateDto.setCounty(county);
        return this;
    }

    public ShipmentUpdateDtoBuilder email(DtoValueString email) {
        shipmentUpdateDto.setEmail(email);
        return this;
    }

    public ShipmentUpdateDtoBuilder fobPoint(DtoValueString fobPoint) {
        shipmentUpdateDto.setFobPoint(fobPoint);
        return this;
    }

    public ShipmentUpdateDtoBuilder freightAmt(DtoValueNullableDecimal freightAmt) {
        shipmentUpdateDto.setFreightAmt(freightAmt);
        return this;
    }

    public ShipmentUpdateDtoBuilder fromWarehouse(DtoValueString fromWarehouse) {
        shipmentUpdateDto.setFromWarehouse(fromWarehouse);
        return this;
    }

    public ShipmentUpdateDtoBuilder hold(DtoValueNullableBoolean hold) {
        shipmentUpdateDto.setHold(hold);
        return this;
    }

    public ShipmentUpdateDtoBuilder insurance(DtoValueNullableBoolean insurance) {
        shipmentUpdateDto.setInsurance(insurance);
        return this;
    }

    public ShipmentUpdateDtoBuilder modeOfTrasport(DtoValueNullableTransportationModes modeOfTrasport) {
        shipmentUpdateDto.setModeOfTrasport(modeOfTrasport);
        return this;
    }

    public ShipmentUpdateDtoBuilder overrideAddress(DtoValueNullableBoolean overrideAddress) {
        shipmentUpdateDto.setOverrideAddress(overrideAddress);
        return this;
    }

    public ShipmentUpdateDtoBuilder overrideContact(DtoValueNullableBoolean overrideContact) {
        shipmentUpdateDto.setOverrideContact(overrideContact);
        return this;
    }

    public ShipmentUpdateDtoBuilder phone1(DtoValueString phone1) {
        shipmentUpdateDto.setPhone1(phone1);
        return this;
    }

    public ShipmentUpdateDtoBuilder postalCode(DtoValueString postalCode) {
        shipmentUpdateDto.setPostalCode(postalCode);
        return this;
    }

    public ShipmentUpdateDtoBuilder residentialDelivery(DtoValueNullableBoolean residentialDelivery) {
        shipmentUpdateDto.setResidentialDelivery(residentialDelivery);
        return this;
    }

    public ShipmentUpdateDtoBuilder saturdayDelivery(DtoValueNullableBoolean saturdayDelivery) {
        shipmentUpdateDto.setSaturdayDelivery(saturdayDelivery);
        return this;
    }

    public ShipmentUpdateDtoBuilder shipmentDetailLines(List<ShipmentDetailLineUpdateDto> shipmentDetailLines) {
        shipmentUpdateDto.setShipmentDetailLines(shipmentDetailLines);
        return this;
    }

    public ShipmentUpdateDtoBuilder shipmentPackageLines(List<ShipmentPackageLineUpdateDto> shipmentPackageLines) {
        shipmentUpdateDto.setShipmentPackageLines(shipmentPackageLines);
        return this;
    }

    public ShipmentUpdateDtoBuilder shippingTerms(DtoValueString shippingTerms) {
        shipmentUpdateDto.setShippingTerms(shippingTerms);
        return this;
    }

    public ShipmentUpdateDtoBuilder shippingZone(DtoValueString shippingZone) {
        shipmentUpdateDto.setShippingZone(shippingZone);
        return this;
    }

    public ShipmentUpdateDtoBuilder shipVia(DtoValueString shipVia) {
        shipmentUpdateDto.setShipVia(shipVia);
        return this;
    }

    public ShipmentUpdateDtoBuilder toWarehouse(DtoValueString toWarehouse) {
        shipmentUpdateDto.setToWarehouse(toWarehouse);
        return this;
    }

    public ShipmentUpdateDtoBuilder transactionType(DtoValueNullableInt32 transactionType) {
        shipmentUpdateDto.setTransactionType(transactionType);
        return this;
    }

    public ShipmentUpdateDtoBuilder useCustomerAccount(DtoValueNullableBoolean useCustomerAccount) {
        shipmentUpdateDto.setUseCustomerAccount(useCustomerAccount);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public ShipmentUpdateDto build() {
        return shipmentUpdateDto;
    }
}