/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum SummaryStatusEnum {
    CANCELLED, //TODO: Write general description for this element
    COMPLETED, //TODO: Write general description for this element
    COUNTING, //TODO: Write general description for this element
    ENTERING; //TODO: Write general description for this element

    private static TreeMap<String, SummaryStatusEnum> valueMap = new TreeMap<String, SummaryStatusEnum>();
    private String value;

    static {
        CANCELLED.value = "Cancelled";
        COMPLETED.value = "Completed";
        COUNTING.value = "Counting";
        ENTERING.value = "Entering";

        valueMap.put("Cancelled", CANCELLED);
        valueMap.put("Completed", COMPLETED);
        valueMap.put("Counting", COUNTING);
        valueMap.put("Entering", ENTERING);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static SummaryStatusEnum fromString(String toConvert) {
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
     * Convert list of SummaryStatusEnum values to list of string values
     * @param toConvert The list of SummaryStatusEnum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<SummaryStatusEnum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (SummaryStatusEnum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 