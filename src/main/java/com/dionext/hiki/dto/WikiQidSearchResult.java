package com.dionext.hiki.dto;

import java.util.HashMap;
import java.util.Map;

public class WikiQidSearchResult {
    public Map<String, WikiQidSearchResultItem> entities = new HashMap<>();
    public int success;
}

