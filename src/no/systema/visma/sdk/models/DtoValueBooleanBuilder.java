/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoValueBooleanBuilder {
    //the instance to build
    private DtoValueBoolean dtoValueBoolean;

    /**
     * Default constructor to initialize the instance
     */
    public DtoValueBooleanBuilder() {
        dtoValueBoolean = new DtoValueBoolean();
    }

    public DtoValueBooleanBuilder value(Boolean value) {
        dtoValueBoolean.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoValueBoolean build() {
        return dtoValueBoolean;
    }
}