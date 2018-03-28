/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum OperationEnum {
    DELETE, //TODO: Write general description for this element
    INSERT, //TODO: Write general description for this element
    UPDATE; //TODO: Write general description for this element

    private static TreeMap<String, OperationEnum> valueMap = new TreeMap<String, OperationEnum>();
    private String value;

    static {
        DELETE.value = "Delete";
        INSERT.value = "Insert";
        UPDATE.value = "Update";

        valueMap.put("Delete", DELETE);
        valueMap.put("Insert", INSERT);
        valueMap.put("Update", UPDATE);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static OperationEnum fromString(String toConvert) {
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
     * Convert list of OperationEnum values to list of string values
     * @param toConvert The list of OperationEnum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<OperationEnum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (OperationEnum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 