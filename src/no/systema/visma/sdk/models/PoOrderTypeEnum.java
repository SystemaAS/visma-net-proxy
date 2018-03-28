/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum PoOrderTypeEnum {
    REGULARORDER, //TODO: Write general description for this element
    DROPSHIP, //TODO: Write general description for this element
    BLANKET, //TODO: Write general description for this element
    STANDARDBLANKET; //TODO: Write general description for this element

    private static TreeMap<String, PoOrderTypeEnum> valueMap = new TreeMap<String, PoOrderTypeEnum>();
    private String value;

    static {
        REGULARORDER.value = "RegularOrder";
        DROPSHIP.value = "DropShip";
        BLANKET.value = "Blanket";
        STANDARDBLANKET.value = "StandardBlanket";

        valueMap.put("RegularOrder", REGULARORDER);
        valueMap.put("DropShip", DROPSHIP);
        valueMap.put("Blanket", BLANKET);
        valueMap.put("StandardBlanket", STANDARDBLANKET);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static PoOrderTypeEnum fromString(String toConvert) {
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
     * Convert list of PoOrderTypeEnum values to list of string values
     * @param toConvert The list of PoOrderTypeEnum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<PoOrderTypeEnum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (PoOrderTypeEnum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 