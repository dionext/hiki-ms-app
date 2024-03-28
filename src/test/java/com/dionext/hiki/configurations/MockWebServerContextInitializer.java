package com.dionext.hiki.configurations;

import okhttp3.mockwebserver.MockWebServer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.io.IOException;

public class MockWebServerContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            MockWebServer mockWebServer = new MockWebServer();
            mockWebServer.start();
            String mockWebServerUrl = String.format("http://localhost:%s", mockWebServer.getPort());

            applicationContext
                    .getBeanFactory()
                    .registerSingleton("mockWebServer", mockWebServer);

            applicationContext.addApplicationListener(applicationEvent -> {
                if (applicationEvent instanceof ContextClosedEvent) {
                    try {
                        mockWebServer.shutdown();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
