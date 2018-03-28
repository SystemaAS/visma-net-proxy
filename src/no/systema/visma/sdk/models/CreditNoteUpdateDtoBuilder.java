/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class CreditNoteUpdateDtoBuilder {
    //the instance to build
    private CreditNoteUpdateDto creditNoteUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public CreditNoteUpdateDtoBuilder() {
        creditNoteUpdateDto = new CreditNoteUpdateDto();
    }

    public CreditNoteUpdateDtoBuilder currencyId(DtoValueString currencyId) {
        creditNoteUpdateDto.setCurrencyId(currencyId);
        return this;
    }

    public CreditNoteUpdateDtoBuilder customerNumber(DtoValueString customerNumber) {
        creditNoteUpdateDto.setCustomerNumber(customerNumber);
        return this;
    }

    public CreditNoteUpdateDtoBuilder customerRefNumber(DtoValueString customerRefNumber) {
        creditNoteUpdateDto.setCustomerRefNumber(customerRefNumber);
        return this;
    }

    public CreditNoteUpdateDtoBuilder documentDate(DtoValueDateTime documentDate) {
        creditNoteUpdateDto.setDocumentDate(documentDate);
        return this;
    }

    public CreditNoteUpdateDtoBuilder externalReference(DtoValueString externalReference) {
        creditNoteUpdateDto.setExternalReference(externalReference);
        return this;
    }

    public CreditNoteUpdateDtoBuilder financialPeriod(DtoValueString financialPeriod) {
        creditNoteUpdateDto.setFinancialPeriod(financialPeriod);
        return this;
    }

    public CreditNoteUpdateDtoBuilder hold(DtoValueBoolean hold) {
        creditNoteUpdateDto.setHold(hold);
        return this;
    }

    public CreditNoteUpdateDtoBuilder invoiceLines(List<CreditNoteLineUpdateDto> invoiceLines) {
        creditNoteUpdateDto.setInvoiceLines(invoiceLines);
        return this;
    }

    public CreditNoteUpdateDtoBuilder invoiceText(DtoValueString invoiceText) {
        creditNoteUpdateDto.setInvoiceText(invoiceText);
        return this;
    }

    public CreditNoteUpdateDtoBuilder locationId(DtoValueString locationId) {
        creditNoteUpdateDto.setLocationId(locationId);
        return this;
    }

    public CreditNoteUpdateDtoBuilder note(DtoValueString note) {
        creditNoteUpdateDto.setNote(note);
        return this;
    }

    public CreditNoteUpdateDtoBuilder postPeriod(DtoValueString postPeriod) {
        creditNoteUpdateDto.setPostPeriod(postPeriod);
        return this;
    }

    public CreditNoteUpdateDtoBuilder referenceNumber(DtoValueString referenceNumber) {
        creditNoteUpdateDto.setReferenceNumber(referenceNumber);
        return this;
    }

    public CreditNoteUpdateDtoBuilder salesPersonID(DtoValueNullableInt32 salesPersonID) {
        creditNoteUpdateDto.setSalesPersonID(salesPersonID);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public CreditNoteUpdateDto build() {
        return creditNoteUpdateDto;
    }
}