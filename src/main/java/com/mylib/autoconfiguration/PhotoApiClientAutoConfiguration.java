package com.mylib.autoconfiguration;

import com.mylib.photo.EnablePhotoApiClients;
import com.mylib.photo.PhotoClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnablePhotoApiClients
@EnableConfigurationProperties(PhotoClientProperties.class)
@ConditionalOnProperty(value = PhotoApiClientAutoConfiguration.PHOTO_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class PhotoApiClientAutoConfiguration {
    public static final String PHOTO_API_CLIENT_AUTO_CONFIG = "custom.client.api.photo.enable";
}
