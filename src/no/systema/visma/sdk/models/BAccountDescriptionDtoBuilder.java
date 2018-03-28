/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class BAccountDescriptionDtoBuilder {
    //the instance to build
    private BAccountDescriptionDto bAccountDescriptionDto;

    /**
     * Default constructor to initialize the instance
     */
    public BAccountDescriptionDtoBuilder() {
        bAccountDescriptionDto = new BAccountDescriptionDto();
    }

    public BAccountDescriptionDtoBuilder internalId(Integer internalId) {
        bAccountDescriptionDto.setInternalId(internalId);
        return this;
    }

    public BAccountDescriptionDtoBuilder name(String name) {
        bAccountDescriptionDto.setName(name);
        return this;
    }

    public BAccountDescriptionDtoBuilder number(String number) {
        bAccountDescriptionDto.setNumber(number);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public BAccountDescriptionDto build() {
        return bAccountDescriptionDto;
    }
}