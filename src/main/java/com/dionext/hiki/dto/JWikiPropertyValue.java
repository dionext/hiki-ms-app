package com.dionext.hiki.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWikiPropertyValue {
    @JsonProperty("Prop")
    private String prop;
    @JsonProperty("Value")
    private String value;


    public final String getProp() {
        return prop;
    }

    public final String getValue() {
        return value;
    }

}

