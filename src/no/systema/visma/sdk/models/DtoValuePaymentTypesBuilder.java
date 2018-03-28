/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoValuePaymentTypesBuilder {
    //the instance to build
    private DtoValuePaymentTypes dtoValuePaymentTypes;

    /**
     * Default constructor to initialize the instance
     */
    public DtoValuePaymentTypesBuilder() {
        dtoValuePaymentTypes = new DtoValuePaymentTypes();
    }

    public DtoValuePaymentTypesBuilder value(Value216Enum value) {
        dtoValuePaymentTypes.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoValuePaymentTypes build() {
        return dtoValuePaymentTypes;
    }
}