package com.dionext.hiki.dto;

import com.dionext.hiki.db.entity.JWikiProperty;

public class JWikiPropertyWithValue {
    private JWikiProperty prop;
    private String value;

    public final JWikiProperty getProp() {
        return prop;
    }

    public final void setProp(JWikiProperty value) {
        prop = value;
    }

    public final String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

