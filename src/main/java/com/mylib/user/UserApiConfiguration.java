package com.mylib.user;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.user.client.UserApiClient;
import com.mylib.user.service.UserApiClientService;
import com.mylib.user.service.UserApiService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

public class UserApiConfiguration extends ClientConfigurationSupport implements ImportAware, ApplicationContextAware {

    private String credentialsInterceptorName;

    private final UserClientProperties userProperties;

    public UserApiConfiguration(UserClientProperties userProperties) {
        super(EnableUserApiClients.class.getName());
        this.userProperties = userProperties;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        super.setImportMetadata(importMetadata);

        Map<String, Object> metadata = importMetadata.getAnnotationAttributes(EnableUserApiClients.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata);

        if (attributes != null) {
            this.credentialsInterceptorName = attributes.getString("credentialsInterceptor");
        }
    }

    @Bean
    public UserApiClient userApiClient() {
        return Feign.builder().client(getClient())
                .decoder(getDecoder())
                .encoder(getEncoder())
                .errorDecoder(getErrorDecoder())
                .contract(ClientConfigFactory.contract())
                .logger(new Slf4jLogger(UserApiClient.class))
                .logLevel(annotationInfo.getLevel())
                .requestInterceptor(getCredentialsHeaderInterceptor())
                .target(UserApiClient.class, userProperties.getBaseUrl());
    }

    @Bean
    public UserApiService userApiService(UserApiClient client) {
        return new UserApiClientService(client);
    }

    public RequestInterceptor getCredentialsHeaderInterceptor() {
        if (StringUtils.hasText(this.credentialsInterceptorName)) {
            return context.getBean(this.credentialsInterceptorName, RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(userProperties.getClientId(), userProperties.getSecretKey());

            return interceptor;
        }
    }
}
