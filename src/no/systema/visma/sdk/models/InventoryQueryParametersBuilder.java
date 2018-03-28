/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class InventoryQueryParametersBuilder {
    //the instance to build
    private InventoryQueryParameters inventoryQueryParameters;

    /**
     * Default constructor to initialize the instance
     */
    public InventoryQueryParametersBuilder() {
        inventoryQueryParameters = new InventoryQueryParameters();
    }

    public InventoryQueryParametersBuilder alternateID(String alternateID) {
        inventoryQueryParameters.setAlternateID(alternateID);
        return this;
    }

    public InventoryQueryParametersBuilder greaterThanValue(String greaterThanValue) {
        inventoryQueryParameters.setGreaterThanValue(greaterThanValue);
        return this;
    }

    public InventoryQueryParametersBuilder lastModifiedDateTime(String lastModifiedDateTime) {
        inventoryQueryParameters.setLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    public InventoryQueryParametersBuilder lastModifiedDateTimeCondition(String lastModifiedDateTimeCondition) {
        inventoryQueryParameters.setLastModifiedDateTimeCondition(lastModifiedDateTimeCondition);
        return this;
    }

    public InventoryQueryParametersBuilder numberToRead(Integer numberToRead) {
        inventoryQueryParameters.setNumberToRead(numberToRead);
        return this;
    }

    public InventoryQueryParametersBuilder orderBy(String orderBy) {
        inventoryQueryParameters.setOrderBy(orderBy);
        return this;
    }

    public InventoryQueryParametersBuilder skipRecords(Integer skipRecords) {
        inventoryQueryParameters.setSkipRecords(skipRecords);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public InventoryQueryParameters build() {
        return inventoryQueryParameters;
    }
}