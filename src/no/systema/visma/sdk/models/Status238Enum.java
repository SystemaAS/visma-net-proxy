/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum Status238Enum {
    BALANCED, //TODO: Write general description for this element
    CANCELLED, //TODO: Write general description for this element
    CLOSED, //TODO: Write general description for this element
    HOLD, //TODO: Write general description for this element
    OPEN, //TODO: Write general description for this element
    PENDINGEMAIL, //TODO: Write general description for this element
    PENDINGPRINT, //TODO: Write general description for this element
    PRINTED, //TODO: Write general description for this element
    VOIDED; //TODO: Write general description for this element

    private static TreeMap<String, Status238Enum> valueMap = new TreeMap<String, Status238Enum>();
    private String value;

    static {
        BALANCED.value = "Balanced";
        CANCELLED.value = "Cancelled";
        CLOSED.value = "Closed";
        HOLD.value = "Hold";
        OPEN.value = "Open";
        PENDINGEMAIL.value = "PendingEmail";
        PENDINGPRINT.value = "PendingPrint";
        PRINTED.value = "Printed";
        VOIDED.value = "Voided";

        valueMap.put("Balanced", BALANCED);
        valueMap.put("Cancelled", CANCELLED);
        valueMap.put("Closed", CLOSED);
        valueMap.put("Hold", HOLD);
        valueMap.put("Open", OPEN);
        valueMap.put("PendingEmail", PENDINGEMAIL);
        valueMap.put("PendingPrint", PENDINGPRINT);
        valueMap.put("Printed", PRINTED);
        valueMap.put("Voided", VOIDED);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static Status238Enum fromString(String toConvert) {
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
     * Convert list of Status238Enum values to list of string values
     * @param toConvert The list of Status238Enum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<Status238Enum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (Status238Enum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 