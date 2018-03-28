/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoDimensionBuilder {
    //the instance to build
    private DtoDimension dtoDimension;

    /**
     * Default constructor to initialize the instance
     */
    public DtoDimensionBuilder() {
        dtoDimension = new DtoDimension();
    }

    public DtoDimensionBuilder description(String description) {
        dtoDimension.setDescription(description);
        return this;
    }

    public DtoDimensionBuilder id(String id) {
        dtoDimension.setId(id);
        return this;
    }

    public DtoDimensionBuilder length(Integer length) {
        dtoDimension.setLength(length);
        return this;
    }

    public DtoDimensionBuilder segments(List<DtoSegment> segments) {
        dtoDimension.setSegments(segments);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoDimension build() {
        return dtoDimension;
    }
}