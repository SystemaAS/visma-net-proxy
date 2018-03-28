/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class PurchaseOrderQueryParametersBuilder {
    //the instance to build
    private PurchaseOrderQueryParameters purchaseOrderQueryParameters;

    /**
     * Default constructor to initialize the instance
     */
    public PurchaseOrderQueryParametersBuilder() {
        purchaseOrderQueryParameters = new PurchaseOrderQueryParameters();
    }

    public PurchaseOrderQueryParametersBuilder greaterThanValue(String greaterThanValue) {
        purchaseOrderQueryParameters.setGreaterThanValue(greaterThanValue);
        return this;
    }

    public PurchaseOrderQueryParametersBuilder lastModifiedDateTime(String lastModifiedDateTime) {
        purchaseOrderQueryParameters.setLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    public PurchaseOrderQueryParametersBuilder lastModifiedDateTimeCondition(String lastModifiedDateTimeCondition) {
        purchaseOrderQueryParameters.setLastModifiedDateTimeCondition(lastModifiedDateTimeCondition);
        return this;
    }

    public PurchaseOrderQueryParametersBuilder numberToRead(Integer numberToRead) {
        purchaseOrderQueryParameters.setNumberToRead(numberToRead);
        return this;
    }

    public PurchaseOrderQueryParametersBuilder skipRecords(Integer skipRecords) {
        purchaseOrderQueryParameters.setSkipRecords(skipRecords);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public PurchaseOrderQueryParameters build() {
        return purchaseOrderQueryParameters;
    }
}