package com.dionext.hiki.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


import java.util.concurrent.TimeUnit;

@Configuration
public class HikiWebConfiguration {
    private static final String WIKI_REST_API_BASE_URL = "https://www.wikidata.org/w/rest.php/wikibase/v1/";

    private static final String WIKI_API_BASE_URL = "https://www.wikidata.org/w/api.php";

    public static final int TIMEOUT = 5000;

    @Bean
    public WebClient wikiRestWebClient() {


        return WebClient.builder()
                .baseUrl(WIKI_REST_API_BASE_URL)
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(2048 * 1024))
                        .build())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                        .doOnConnected(connection -> {
                            connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                            connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                        })))
                .build();
    }
    @Bean
    public WebClient wikiApiWebClient() {
        return WebClient.builder()
                .baseUrl(WIKI_API_BASE_URL)
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(2048 * 1024))
                        .build())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                        .doOnConnected(connection -> {
                            connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                            connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                        })))
                .build();
    }
}
