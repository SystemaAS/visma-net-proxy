/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class SalesOrderUpdateDtoBuilder {
    //the instance to build
    private SalesOrderUpdateDto salesOrderUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public SalesOrderUpdateDtoBuilder() {
        salesOrderUpdateDto = new SalesOrderUpdateDto();
    }

    public SalesOrderUpdateDtoBuilder branch(DtoValueNullableInt32 branch) {
        salesOrderUpdateDto.setBranch(branch);
        return this;
    }

    public SalesOrderUpdateDtoBuilder cancelBy(DtoValueNullableDateTime cancelBy) {
        salesOrderUpdateDto.setCancelBy(cancelBy);
        return this;
    }

    public SalesOrderUpdateDtoBuilder canceled(DtoValueNullableBoolean canceled) {
        salesOrderUpdateDto.setCanceled(canceled);
        return this;
    }

    public SalesOrderUpdateDtoBuilder currency(DtoValueString currency) {
        salesOrderUpdateDto.setCurrency(currency);
        return this;
    }

    public SalesOrderUpdateDtoBuilder customer(DtoValueString customer) {
        salesOrderUpdateDto.setCustomer(customer);
        return this;
    }

    public SalesOrderUpdateDtoBuilder customerOrder(DtoValueString customerOrder) {
        salesOrderUpdateDto.setCustomerOrder(customerOrder);
        return this;
    }

    public SalesOrderUpdateDtoBuilder customerRefNo(DtoValueString customerRefNo) {
        salesOrderUpdateDto.setCustomerRefNo(customerRefNo);
        return this;
    }

    public SalesOrderUpdateDtoBuilder customerVATZone(DtoValueString customerVATZone) {
        salesOrderUpdateDto.setCustomerVATZone(customerVATZone);
        return this;
    }

    public SalesOrderUpdateDtoBuilder date(DtoValueNullableDateTime date) {
        salesOrderUpdateDto.setDate(date);
        return this;
    }

    public SalesOrderUpdateDtoBuilder description(DtoValueString description) {
        salesOrderUpdateDto.setDescription(description);
        return this;
    }

    public SalesOrderUpdateDtoBuilder fobPoint(DtoValueString fobPoint) {
        salesOrderUpdateDto.setFobPoint(fobPoint);
        return this;
    }

    public SalesOrderUpdateDtoBuilder hold(DtoValueNullableBoolean hold) {
        salesOrderUpdateDto.setHold(hold);
        return this;
    }

    public SalesOrderUpdateDtoBuilder insurance(DtoValueNullableBoolean insurance) {
        salesOrderUpdateDto.setInsurance(insurance);
        return this;
    }

    public SalesOrderUpdateDtoBuilder invoiceSeparately(DtoValueNullableBoolean invoiceSeparately) {
        salesOrderUpdateDto.setInvoiceSeparately(invoiceSeparately);
        return this;
    }

    public SalesOrderUpdateDtoBuilder lines(List<SalesOrderLineUpdateDto> lines) {
        salesOrderUpdateDto.setLines(lines);
        return this;
    }

    public SalesOrderUpdateDtoBuilder location(DtoValueString location) {
        salesOrderUpdateDto.setLocation(location);
        return this;
    }

    public SalesOrderUpdateDtoBuilder orderNumber(DtoValueString orderNumber) {
        salesOrderUpdateDto.setOrderNumber(orderNumber);
        return this;
    }

    public SalesOrderUpdateDtoBuilder orderType(DtoValueString orderType) {
        salesOrderUpdateDto.setOrderType(orderType);
        return this;
    }

    public SalesOrderUpdateDtoBuilder origOrderNbr(DtoValueString origOrderNbr) {
        salesOrderUpdateDto.setOrigOrderNbr(origOrderNbr);
        return this;
    }

    public SalesOrderUpdateDtoBuilder origOrderType(DtoValueString origOrderType) {
        salesOrderUpdateDto.setOrigOrderType(origOrderType);
        return this;
    }

    public SalesOrderUpdateDtoBuilder owner(DtoValueNullableGuid owner) {
        salesOrderUpdateDto.setOwner(owner);
        return this;
    }

    public SalesOrderUpdateDtoBuilder preferredWarehouse(DtoValueString preferredWarehouse) {
        salesOrderUpdateDto.setPreferredWarehouse(preferredWarehouse);
        return this;
    }

    public SalesOrderUpdateDtoBuilder printDescriptionOnInvoice(DtoValueNullableBoolean printDescriptionOnInvoice) {
        salesOrderUpdateDto.setPrintDescriptionOnInvoice(printDescriptionOnInvoice);
        return this;
    }

    public SalesOrderUpdateDtoBuilder printNoteOnExternalDocuments(DtoValueNullableBoolean printNoteOnExternalDocuments) {
        salesOrderUpdateDto.setPrintNoteOnExternalDocuments(printNoteOnExternalDocuments);
        return this;
    }

    public SalesOrderUpdateDtoBuilder printNoteOnInternalDocuments(DtoValueNullableBoolean printNoteOnInternalDocuments) {
        salesOrderUpdateDto.setPrintNoteOnInternalDocuments(printNoteOnInternalDocuments);
        return this;
    }

    public SalesOrderUpdateDtoBuilder priority(DtoValueNullableInt32 priority) {
        salesOrderUpdateDto.setPriority(priority);
        return this;
    }

    public SalesOrderUpdateDtoBuilder project(DtoValueNullableInt32 project) {
        salesOrderUpdateDto.setProject(project);
        return this;
    }

    public SalesOrderUpdateDtoBuilder recalculateShipment(Boolean recalculateShipment) {
        salesOrderUpdateDto.setRecalculateShipment(recalculateShipment);
        return this;
    }

    public SalesOrderUpdateDtoBuilder requestOn(DtoValueNullableDateTime requestOn) {
        salesOrderUpdateDto.setRequestOn(requestOn);
        return this;
    }

    public SalesOrderUpdateDtoBuilder residentialDelivery(DtoValueNullableBoolean residentialDelivery) {
        salesOrderUpdateDto.setResidentialDelivery(residentialDelivery);
        return this;
    }

    public SalesOrderUpdateDtoBuilder salesPerson(DtoValueString salesPerson) {
        salesOrderUpdateDto.setSalesPerson(salesPerson);
        return this;
    }

    public SalesOrderUpdateDtoBuilder saturdayDelivery(DtoValueNullableBoolean saturdayDelivery) {
        salesOrderUpdateDto.setSaturdayDelivery(saturdayDelivery);
        return this;
    }

    public SalesOrderUpdateDtoBuilder schedShipment(DtoValueNullableDateTime schedShipment) {
        salesOrderUpdateDto.setSchedShipment(schedShipment);
        return this;
    }

    public SalesOrderUpdateDtoBuilder shipComplete(DtoValueNullableSalesOrderShipCompleteStatuses shipComplete) {
        salesOrderUpdateDto.setShipComplete(shipComplete);
        return this;
    }

    public SalesOrderUpdateDtoBuilder shippingTerms(DtoValueString shippingTerms) {
        salesOrderUpdateDto.setShippingTerms(shippingTerms);
        return this;
    }

    public SalesOrderUpdateDtoBuilder shippingZone(DtoValueString shippingZone) {
        salesOrderUpdateDto.setShippingZone(shippingZone);
        return this;
    }

    public SalesOrderUpdateDtoBuilder shipSeparately(DtoValueNullableBoolean shipSeparately) {
        salesOrderUpdateDto.setShipSeparately(shipSeparately);
        return this;
    }

    public SalesOrderUpdateDtoBuilder shipVia(DtoValueString shipVia) {
        salesOrderUpdateDto.setShipVia(shipVia);
        return this;
    }

    public SalesOrderUpdateDtoBuilder soBillingAddress(DtoValueSalesOrderAddressUpdateDto soBillingAddress) {
        salesOrderUpdateDto.setSoBillingAddress(soBillingAddress);
        return this;
    }

    public SalesOrderUpdateDtoBuilder soBillingContact(DtoValueSalesOrderContactUpdateDto soBillingContact) {
        salesOrderUpdateDto.setSoBillingContact(soBillingContact);
        return this;
    }

    public SalesOrderUpdateDtoBuilder soShippingAddress(DtoValueSalesOrderAddressUpdateDto soShippingAddress) {
        salesOrderUpdateDto.setSoShippingAddress(soShippingAddress);
        return this;
    }

    public SalesOrderUpdateDtoBuilder soShippingContact(DtoValueSalesOrderContactUpdateDto soShippingContact) {
        salesOrderUpdateDto.setSoShippingContact(soShippingContact);
        return this;
    }

    public SalesOrderUpdateDtoBuilder terms(DtoValueString terms) {
        salesOrderUpdateDto.setTerms(terms);
        return this;
    }

    public SalesOrderUpdateDtoBuilder transactionType(DtoValueNullableInt32 transactionType) {
        salesOrderUpdateDto.setTransactionType(transactionType);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public SalesOrderUpdateDto build() {
        return salesOrderUpdateDto;
    }
}