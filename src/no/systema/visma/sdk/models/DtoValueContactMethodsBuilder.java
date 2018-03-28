/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoValueContactMethodsBuilder {
    //the instance to build
    private DtoValueContactMethods dtoValueContactMethods;

    /**
     * Default constructor to initialize the instance
     */
    public DtoValueContactMethodsBuilder() {
        dtoValueContactMethods = new DtoValueContactMethods();
    }

    public DtoValueContactMethodsBuilder value(Value51Enum value) {
        dtoValueContactMethods.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoValueContactMethods build() {
        return dtoValueContactMethods;
    }
}