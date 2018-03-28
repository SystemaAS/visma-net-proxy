/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum ShipmentTypeEnum {
    ISSUE, //TODO: Write general description for this element
    DROPSHIP, //TODO: Write general description for this element
    TRANSFER; //TODO: Write general description for this element

    private static TreeMap<String, ShipmentTypeEnum> valueMap = new TreeMap<String, ShipmentTypeEnum>();
    private String value;

    static {
        ISSUE.value = "Issue";
        DROPSHIP.value = "DropShip";
        TRANSFER.value = "Transfer";

        valueMap.put("Issue", ISSUE);
        valueMap.put("DropShip", DROPSHIP);
        valueMap.put("Transfer", TRANSFER);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static ShipmentTypeEnum fromString(String toConvert) {
        return valueMap.get(toConvert);
    }

    /**
     * Returns the string value associated with the enum member
     * @return The string value against enum member */
    @com.fasterxml.jackson.annotation.JsonValue
    public String value() {
        return value;
    }
        
    /**
     * Get string representation of this enum
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Convert list of ShipmentTypeEnum values to list of string values
     * @param toConvert The list of ShipmentTypeEnum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<ShipmentTypeEnum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (ShipmentTypeEnum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 