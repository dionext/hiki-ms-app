package com.dionext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;


@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = "com.dionext.utils")
@ComponentScan(basePackages = "com.dionext.site")
@Slf4j
@EnableCaching
public class HikiMsAppApplication implements ApplicationRunner {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(HikiMsAppApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> log.debug("ShutdownHook...")));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String s = ":d::d:d:";
        String[] ss = s.split(":");
        log.debug("Application running");
    }

}
