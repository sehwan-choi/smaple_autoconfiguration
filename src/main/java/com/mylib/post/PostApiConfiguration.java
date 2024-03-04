package com.mylib.post;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.post.client.PostApiClient;
import com.mylib.post.service.PostApiClientService;
import com.mylib.post.service.PostApiService;
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

public class PostApiConfiguration extends ClientConfigurationSupport implements ImportAware, ApplicationContextAware {


    private String credentialsInterceptorName;

    private final PostClientProperties postProperties;

    public PostApiConfiguration(PostClientProperties postProperties) {
        super(EnablePostApiClients.class.getName());
        this.postProperties = postProperties;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        super.setImportMetadata(importMetadata);

        Map<String, Object> metadata = importMetadata.getAnnotationAttributes(EnablePostApiClients.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata);

        if (attributes != null) {
            this.credentialsInterceptorName = attributes.getString("credentialsInterceptor");
        }
    }

    @Bean
    public PostApiClient postApiClient() {
        return Feign.builder().client(getClient())
                .decoder(getDecoder())
                .encoder(getEncoder())
                .errorDecoder(getErrorDecoder())
                .contract(ClientConfigFactory.contract())
                .logger(new Slf4jLogger(PostApiClient.class))
                .logLevel(annotationInfo.getLevel())
                .requestInterceptor(getCredentialsHeaderInterceptor())
                .target(PostApiClient.class, postProperties.getBaseUrl());
    }

    @Bean
    public PostApiService postApiService(PostApiClient client) {
        return new PostApiClientService(client);
    }

    public RequestInterceptor getCredentialsHeaderInterceptor() {
        if (StringUtils.hasText(this.credentialsInterceptorName)) {
            return context.getBean(this.credentialsInterceptorName, RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(postProperties.getClientId(), postProperties.getSecretKey());

            return interceptor;
        }
    }
}
