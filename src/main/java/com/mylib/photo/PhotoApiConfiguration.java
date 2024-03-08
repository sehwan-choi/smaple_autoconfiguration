package com.mylib.photo;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.photo.client.PhotoApiClient;
import com.mylib.photo.service.PhotoApiClientService;
import com.mylib.photo.service.PhotoApiService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class PhotoApiConfiguration extends ClientConfigurationSupport {

    private final PhotoClientProperties photoProperties;

    public PhotoApiConfiguration(PhotoClientProperties photoProperties) {
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
