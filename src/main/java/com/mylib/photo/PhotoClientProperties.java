package com.mylib.photo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.photo.PhotoClientProperties.PHOTO_CLIENT_PREFIX;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = PHOTO_CLIENT_PREFIX)
public class PhotoClientProperties {
    public static final String PHOTO_CLIENT_PREFIX = "custom.client.photo";

    private String baseUrl;

    private String clientId;

    private String secretKey;

}
