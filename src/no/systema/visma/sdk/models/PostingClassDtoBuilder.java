/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class PostingClassDtoBuilder {
    //the instance to build
    private PostingClassDto postingClassDto;

    /**
     * Default constructor to initialize the instance
     */
    public PostingClassDtoBuilder() {
        postingClassDto = new PostingClassDto();
    }

    public PostingClassDtoBuilder description(String description) {
        postingClassDto.setDescription(description);
        return this;
    }

    public PostingClassDtoBuilder id(String id) {
        postingClassDto.setId(id);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public PostingClassDto build() {
        return postingClassDto;
    }
}