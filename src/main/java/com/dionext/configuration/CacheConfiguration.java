package com.dionext.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings({"java:S1604"})
public class CacheConfiguration extends  BaseCacheConfiguration {
    static final public String CACHE_PLACES = "cache-places";
    static final public String CACHE_PLACES_INFO = "cache-places-info";

    ///////////////////////////////////////
    ////// ConcurrentMapCacheManager //////
    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
        return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
            @Override
            public void customize(ConcurrentMapCacheManager cacheManager) {
                //to do
            }
        };
    }

    //////////////////////////
    //////// Caffeine  ///////
    //@Bean
    //public Caffeine<Object, Object> caffeineConfig() {
      //  return Caffeine.newBuilder().recordStats();
    //}

    @Bean
    public CacheManager cacheManager() {
    //public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        //CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("sitemap", "JGeoWikidata");//, "JGeoWikidata");
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().recordStats());//default

        caffeineCacheManager.registerCustomCache(CACHE_COMMON,
                Caffeine.newBuilder()
                        .recordStats()
                        .maximumSize(1000)
                        //.maximumWeight(Integer.MAX_VALUE)  - !Note: this is not size in bytes
                        .build());
        caffeineCacheManager.registerCustomCache(CACHE_PLACES,
                Caffeine.newBuilder()
                        .recordStats()
                        .maximumSize(50000)//maximum number of entries the cache may contain
                        .build());
        caffeineCacheManager.registerCustomCache(CACHE_PLACES_INFO,
                Caffeine.newBuilder()
                        .recordStats()
                        .maximumSize(50000)//maximum number of entries the cache may contain
                        .build());

        return caffeineCacheManager;
    }
}
