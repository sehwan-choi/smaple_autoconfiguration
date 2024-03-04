package com.mylib.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.user.UserClientProperties.USER_CLIENT_PREFIX;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = USER_CLIENT_PREFIX)
public class UserClientProperties {
    public static final String USER_CLIENT_PREFIX = "custom.client.user";

    private String baseUrl;

    private String clientId;

    private String secretKey;

}
