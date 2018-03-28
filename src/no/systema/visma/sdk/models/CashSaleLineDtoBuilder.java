/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class CashSaleLineDtoBuilder {
    //the instance to build
    private CashSaleLineDto cashSaleLineDto;

    /**
     * Default constructor to initialize the instance
     */
    public CashSaleLineDtoBuilder() {
        cashSaleLineDto = new CashSaleLineDto();
    }

    public CashSaleLineDtoBuilder account(AccountNumberDto account) {
        cashSaleLineDto.setAccount(account);
        return this;
    }

    public CashSaleLineDtoBuilder amount(Double amount) {
        cashSaleLineDto.setAmount(amount);
        return this;
    }

    public CashSaleLineDtoBuilder amountInCurrency(Double amountInCurrency) {
        cashSaleLineDto.setAmountInCurrency(amountInCurrency);
        return this;
    }

    public CashSaleLineDtoBuilder deferralCode(String deferralCode) {
        cashSaleLineDto.setDeferralCode(deferralCode);
        return this;
    }

    public CashSaleLineDtoBuilder deferralSchedule(Integer deferralSchedule) {
        cashSaleLineDto.setDeferralSchedule(deferralSchedule);
        return this;
    }

    public CashSaleLineDtoBuilder description(String description) {
        cashSaleLineDto.setDescription(description);
        return this;
    }

    public CashSaleLineDtoBuilder discountAmount(Double discountAmount) {
        cashSaleLineDto.setDiscountAmount(discountAmount);
        return this;
    }

    public CashSaleLineDtoBuilder discountAmountInCurrency(Double discountAmountInCurrency) {
        cashSaleLineDto.setDiscountAmountInCurrency(discountAmountInCurrency);
        return this;
    }

    public CashSaleLineDtoBuilder discountCode(String discountCode) {
        cashSaleLineDto.setDiscountCode(discountCode);
        return this;
    }

    public CashSaleLineDtoBuilder discountPercent(Double discountPercent) {
        cashSaleLineDto.setDiscountPercent(discountPercent);
        return this;
    }

    public CashSaleLineDtoBuilder inventoryNumber(String inventoryNumber) {
        cashSaleLineDto.setInventoryNumber(inventoryNumber);
        return this;
    }

    public CashSaleLineDtoBuilder lineNumber(Integer lineNumber) {
        cashSaleLineDto.setLineNumber(lineNumber);
        return this;
    }

    public CashSaleLineDtoBuilder manualAmount(Double manualAmount) {
        cashSaleLineDto.setManualAmount(manualAmount);
        return this;
    }

    public CashSaleLineDtoBuilder manualAmountInCurrency(Double manualAmountInCurrency) {
        cashSaleLineDto.setManualAmountInCurrency(manualAmountInCurrency);
        return this;
    }

    public CashSaleLineDtoBuilder manualDiscount(Boolean manualDiscount) {
        cashSaleLineDto.setManualDiscount(manualDiscount);
        return this;
    }

    public CashSaleLineDtoBuilder note(String note) {
        cashSaleLineDto.setNote(note);
        return this;
    }

    public CashSaleLineDtoBuilder quantity(Double quantity) {
        cashSaleLineDto.setQuantity(quantity);
        return this;
    }

    public CashSaleLineDtoBuilder salesperson(String salesperson) {
        cashSaleLineDto.setSalesperson(salesperson);
        return this;
    }

    public CashSaleLineDtoBuilder subaccount(SubAccountDto subaccount) {
        cashSaleLineDto.setSubaccount(subaccount);
        return this;
    }

    public CashSaleLineDtoBuilder unitPrice(Double unitPrice) {
        cashSaleLineDto.setUnitPrice(unitPrice);
        return this;
    }

    public CashSaleLineDtoBuilder unitPriceInCurrency(Double unitPriceInCurrency) {
        cashSaleLineDto.setUnitPriceInCurrency(unitPriceInCurrency);
        return this;
    }

    public CashSaleLineDtoBuilder uom(String uom) {
        cashSaleLineDto.setUom(uom);
        return this;
    }

    public CashSaleLineDtoBuilder vatCode(VatCodeDto vatCode) {
        cashSaleLineDto.setVatCode(vatCode);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public CashSaleLineDto build() {
        return cashSaleLineDto;
    }
}