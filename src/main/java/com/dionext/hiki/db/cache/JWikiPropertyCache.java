package com.dionext.hiki.db.cache;

import com.dionext.hiki.db.entity.JWikiProperty;
import com.dionext.site.services.ResourceService;
import com.dionext.utils.OUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JWikiPropertyCache {

    ResourceService resourceService;
    Map<String, JWikiProperty> map = new HashMap<>();
    @Value("${web-config.multiSites.hiking.siteStaticStoragePath}")
    private String siteStoragePath;

    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            String src = resourceService.findResourceAsString(new String[]{siteStoragePath}, "cache/JWikiProperty.json");
            List<JWikiProperty> list = OUtils.readList(src, JWikiProperty.class);
            for (JWikiProperty l : list) {
                map.put(l.getJWikiPropertyId(), l);
            }
        } catch (Exception ex) {
            log.error("Unable load cache for JWikiProperty from rel path " + "cache/JWikiProperty.json");
        }
    }

    public JWikiProperty getById(String id) {
        return map.get(id);
    }

}
