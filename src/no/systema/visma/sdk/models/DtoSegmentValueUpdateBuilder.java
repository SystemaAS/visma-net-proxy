/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class DtoSegmentValueUpdateBuilder {
    //the instance to build
    private DtoSegmentValueUpdate dtoSegmentValueUpdate;

    /**
     * Default constructor to initialize the instance
     */
    public DtoSegmentValueUpdateBuilder() {
        dtoSegmentValueUpdate = new DtoSegmentValueUpdate();
    }

    public DtoSegmentValueUpdateBuilder active(DtoValueBoolean active) {
        dtoSegmentValueUpdate.setActive(active);
        return this;
    }

    public DtoSegmentValueUpdateBuilder description(DtoValueString description) {
        dtoSegmentValueUpdate.setDescription(description);
        return this;
    }

    public DtoSegmentValueUpdateBuilder operation(OperationEnum operation) {
        dtoSegmentValueUpdate.setOperation(operation);
        return this;
    }

    public DtoSegmentValueUpdateBuilder publicId(DtoValueNullableGuid publicId) {
        dtoSegmentValueUpdate.setPublicId(publicId);
        return this;
    }

    public DtoSegmentValueUpdateBuilder value(String value) {
        dtoSegmentValueUpdate.setValue(value);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public DtoSegmentValueUpdate build() {
        return dtoSegmentValueUpdate;
    }
}