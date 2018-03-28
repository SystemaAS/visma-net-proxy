/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum Value51Enum {
    ANY, //TODO: Write general description for this element
    EMAIL, //TODO: Write general description for this element
    FAX, //TODO: Write general description for this element
    MAIL, //TODO: Write general description for this element
    PHONE; //TODO: Write general description for this element

    private static TreeMap<String, Value51Enum> valueMap = new TreeMap<String, Value51Enum>();
    private String value;

    static {
        ANY.value = "Any";
        EMAIL.value = "Email";
        FAX.value = "Fax";
        MAIL.value = "Mail";
        PHONE.value = "Phone";

        valueMap.put("Any", ANY);
        valueMap.put("Email", EMAIL);
        valueMap.put("Fax", FAX);
        valueMap.put("Mail", MAIL);
        valueMap.put("Phone", PHONE);
    }

    /**
     * Returns the enum member associated with the given string value
     * @return The enum member against the given string value */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static Value51Enum fromString(String toConvert) {
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
     * Convert list of Value51Enum values to list of string values
     * @param toConvert The list of Value51Enum values to convert
     * @return List of representative string values */
    public static List<String> toValue(List<Value51Enum> toConvert) {
        if(toConvert == null)
            return null;
        List<String> convertedValues = new ArrayList<String>();
        for (Value51Enum enumValue : toConvert) {
            convertedValues.add(enumValue.value);
        }
        return convertedValues;
    }
} 