package com.dionext.hiki.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Slf4j
@Getter
@Setter
public class GeoPageInfo {
    private String lat;
    private String lon;
    private String placename;
    private String region;

}