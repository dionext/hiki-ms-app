package com.dionext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Locale;


@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = "com.dionext")
@Slf4j
@EnableCaching
@EnableRetry
public class HikiMsAppApplication implements ApplicationRunner {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(HikiMsAppApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> log.debug("ShutdownHook...")));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.debug("Application running");
    }

}
