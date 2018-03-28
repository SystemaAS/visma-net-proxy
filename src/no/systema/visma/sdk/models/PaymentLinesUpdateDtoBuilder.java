/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class PaymentLinesUpdateDtoBuilder {
    //the instance to build
    private PaymentLinesUpdateDto paymentLinesUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public PaymentLinesUpdateDtoBuilder() {
        paymentLinesUpdateDto = new PaymentLinesUpdateDto();
    }

    public PaymentLinesUpdateDtoBuilder amountPaid(DtoValueDecimal amountPaid) {
        paymentLinesUpdateDto.setAmountPaid(amountPaid);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder balanceWriteOff(DtoValueDecimal balanceWriteOff) {
        paymentLinesUpdateDto.setBalanceWriteOff(balanceWriteOff);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder cashDiscountTaken(DtoValueDecimal cashDiscountTaken) {
        paymentLinesUpdateDto.setCashDiscountTaken(cashDiscountTaken);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder crossRate(DtoValueDecimal crossRate) {
        paymentLinesUpdateDto.setCrossRate(crossRate);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder documentType(DtoValueCustomerDocumentTypes documentType) {
        paymentLinesUpdateDto.setDocumentType(documentType);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder operation(OperationEnum operation) {
        paymentLinesUpdateDto.setOperation(operation);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder refNbr(DtoValueString refNbr) {
        paymentLinesUpdateDto.setRefNbr(refNbr);
        return this;
    }

    public PaymentLinesUpdateDtoBuilder writeOffReasonCode(DtoValueString writeOffReasonCode) {
        paymentLinesUpdateDto.setWriteOffReasonCode(writeOffReasonCode);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public PaymentLinesUpdateDto build() {
        return paymentLinesUpdateDto;
    }
}