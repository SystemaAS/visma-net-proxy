/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum Type358Enum {
    DIRECTVAT, //TODO: Write general description for this element
    EXEMPTVAT, //TODO: Write general description for this element
    PENDINGVAT, //TODO: Write general description for this element
    REVERSEVAT, //TODO: Write general description for this element
    SALES, //TODO: Write general description for this element
    STATISTICALVAT, //TODO: Write general description for this element
    USE, //TODO: Write general description for this element
    VAT, //TODO: Write general description for this element
    WITHHOLDING; //TODO: Write general description for this element

    private static TreeMap<String, Type358Enum> valueMap = new TreeMap<String, Type358Enum>();
    private String value;

    static {
        DIRECTVAT.value = "DirectVat";
        EXEMPTVAT.value = "ExemptVat";
        PENDINGVAT.value = "PendingVat";
        REVERSEVAT.value = "ReverseVat";
        SALES.value = "Sales";
        STATISTICALVAT.value = "StatisticalVat";
        USE.value = "Use";
        VAT.value = "Vat";
        WITHHOLDING.value = "Withholding";

        valueMap.put("DirectVat", DIRECTVAT);
        valueMap.put("ExemptVat", EXEMPTVAT);
        valueMap.put("PendingVat", PENDINGVAT);
        valueMap.put("ReverseVat", REVERSEVAT);
        valueMap.put("Sales", SALES);
        valueMap.put("StatisticalVat", STATISTICALVAT);
        valueMap.put("Use", USE);
        valueMap.put("Vat", VAT);
        valueMap.put("Withholding", WITHHOLDING);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static Type358Enum fromString(String toConvert) {
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
     * Convert list of Type358Enum values to list of string values
     * @param toConvert The list of Type358Enum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<Type358Enum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (Type358Enum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 