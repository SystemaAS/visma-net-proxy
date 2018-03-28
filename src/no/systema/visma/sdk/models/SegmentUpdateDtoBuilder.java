/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class SegmentUpdateDtoBuilder {
    //the instance to build
    private SegmentUpdateDto segmentUpdateDto;

    /**
     * Default constructor to initialize the instance
     */
    public SegmentUpdateDtoBuilder() {
        segmentUpdateDto = new SegmentUpdateDto();
    }

    public SegmentUpdateDtoBuilder segmentId(String segmentId) {
        segmentUpdateDto.setSegmentId(segmentId);
        return this;
    }

    public SegmentUpdateDtoBuilder segmentValue(String segmentValue) {
        segmentUpdateDto.setSegmentValue(segmentValue);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public SegmentUpdateDto build() {
        return segmentUpdateDto;
    }
}