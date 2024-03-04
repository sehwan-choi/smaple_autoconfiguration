package com.mylib.post;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.post.PostClientProperties.POST_CLIENT_PREFIX;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = POST_CLIENT_PREFIX)
public class PostClientProperties {
    public static final String POST_CLIENT_PREFIX = "custom.client.post";

    private String baseUrl;

    private String clientId;

    private String secretKey;

}
