/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoValueSupplierChargeBearerBuilder {
    //the instance to build
    private DtoValueSupplierChargeBearer dtoValueSupplierChargeBearer;

    /**
     * Default constructor to initialize the instance
     */
    public DtoValueSupplierChargeBearerBuilder() {
        dtoValueSupplierChargeBearer = new DtoValueSupplierChargeBearer();
    }

    public DtoValueSupplierChargeBearerBuilder value(Value335Enum value) {
        dtoValueSupplierChargeBearer.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoValueSupplierChargeBearer build() {
        return dtoValueSupplierChargeBearer;
    }
}