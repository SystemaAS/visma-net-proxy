/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class PurchaseOrderBasicDtoBuilder {
    //the instance to build
    private PurchaseOrderBasicDto purchaseOrderBasicDto;

    /**
     * Default constructor to initialize the instance
     */
    public PurchaseOrderBasicDtoBuilder() {
        purchaseOrderBasicDto = new PurchaseOrderBasicDto();
    }

    public PurchaseOrderBasicDtoBuilder controlTotal(Double controlTotal) {
        purchaseOrderBasicDto.setControlTotal(controlTotal);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder currency(String currency) {
        purchaseOrderBasicDto.setCurrency(currency);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder date(Date date) {
        purchaseOrderBasicDto.setDate(date);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder description(String description) {
        purchaseOrderBasicDto.setDescription(description);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder hold(Boolean hold) {
        purchaseOrderBasicDto.setHold(hold);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder lastModifiedDateTime(Date lastModifiedDateTime) {
        purchaseOrderBasicDto.setLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder lines(List<PurchaseOrderLineDto> lines) {
        purchaseOrderBasicDto.setLines(lines);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder lineTotal(Double lineTotal) {
        purchaseOrderBasicDto.setLineTotal(lineTotal);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder location(LocationIdNameDto location) {
        purchaseOrderBasicDto.setLocation(location);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder orderNbr(String orderNbr) {
        purchaseOrderBasicDto.setOrderNbr(orderNbr);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder orderTotal(Double orderTotal) {
        purchaseOrderBasicDto.setOrderTotal(orderTotal);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder orderType(OrderTypeEnum orderType) {
        purchaseOrderBasicDto.setOrderType(orderType);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder owner(UserDescriptionDto owner) {
        purchaseOrderBasicDto.setOwner(owner);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder promisedOn(Date promisedOn) {
        purchaseOrderBasicDto.setPromisedOn(promisedOn);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder status(Status238Enum status) {
        purchaseOrderBasicDto.setStatus(status);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder supplier(SupplierDescriptionDto supplier) {
        purchaseOrderBasicDto.setSupplier(supplier);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder supplierRef(String supplierRef) {
        purchaseOrderBasicDto.setSupplierRef(supplierRef);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder taxDetails(List<TaxDetailDto> taxDetails) {
        purchaseOrderBasicDto.setTaxDetails(taxDetails);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder taxTotal(Double taxTotal) {
        purchaseOrderBasicDto.setTaxTotal(taxTotal);
        return this;
    }

    public PurchaseOrderBasicDtoBuilder vatExemptTotal(Double vatExemptTotal) {
        purchaseOrderBasicDto.setVatExemptTotal(vatExemptTotal);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public PurchaseOrderBasicDto build() {
        return purchaseOrderBasicDto;
    }
}