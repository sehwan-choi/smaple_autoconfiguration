package com.mylib.autoconfiguration;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.photo.EnablePhotoApiClients;
import com.mylib.photo.PhotoClientProperties;
import com.mylib.photo.client.PhotoApiClient;
import com.mylib.photo.service.PhotoApiClientService;
import com.mylib.photo.service.PhotoApiService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(PhotoClientProperties.class)
@ConditionalOnProperty(value = PhotoApiClientAutoConfiguration.PHOTO_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class PhotoApiClientAutoConfiguration extends ClientConfigurationSupport {

    public static final String PHOTO_API_CLIENT_AUTO_CONFIG = "custom.client.api.photo.enable";

    private final PhotoClientProperties photoProperties;

    public PhotoApiClientAutoConfiguration(PhotoClientProperties photoProperties) {
        super(EnablePhotoApiClients.class.getName());
        this.photoProperties = photoProperties;
    }

    @Bean
    public PhotoApiClient photoApiClient() {
        return Feign.builder().client(getClient())
                .decoder(getDecoder())
                .encoder(getEncoder())
                .errorDecoder(getErrorDecoder())
                .contract(ClientConfigFactory.contract())
                .logger(new Slf4jLogger(PhotoApiClient.class))
                .logLevel(annotationInfo.getLevel())
                .requestInterceptor(getCredentialsHeaderInterceptor())
                .target(PhotoApiClient.class, photoProperties.getBaseUrl());
    }

    @Bean
    public PhotoApiService photoApiService(PhotoApiClient client) {
        return new PhotoApiClientService(client);
    }

    public RequestInterceptor getCredentialsHeaderInterceptor() {
        if (StringUtils.hasText(super.annotationInfo.getCredentialsInterceptorName())) {
            return context.getBean(super.annotationInfo.getCredentialsInterceptorName(), RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(photoProperties.getClientId(), photoProperties.getSecretKey());

            return interceptor;
        }
    }
}
