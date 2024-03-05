package com.mylib.post;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.post.PostClientProperties.POST_CLIENT_PREFIX;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = POST_CLIENT_PREFIX)
public class PostClientProperties {
    public static final String POST_CLIENT_PREFIX = "custom.client.post";

    private String baseUrl;

    private String clientId;

    private String secretKey;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
