package com.mylib.api;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

//@Configuration(proxyBeanMethods = false)
@AutoConfiguration
//@EnableConfigurationProperties
public class MyLibConfig {


    @PostConstruct
    void init() {
        System.out.println("==============init=============");
    }

    @Bean
    public ApiUtil apiUtil() {
        return new ApiUtil();
    }

    public static class ApiUtil {

        public LocalDateTime get() {
            return LocalDateTime.now();
        }
    }
}
