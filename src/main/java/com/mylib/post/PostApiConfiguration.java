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
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class PostApiConfiguration extends ClientConfigurationSupport {

    private final PostClientProperties postProperties;

    public PostApiConfiguration(PostClientProperties postProperties) {
        super(EnablePostApiClients.class.getName());
        this.postProperties = postProperties;
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
        if (StringUtils.hasText(super.annotationInfo.getCredentialsInterceptorName())) {
            return context.getBean(super.annotationInfo.getCredentialsInterceptorName(), RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(postProperties.getClientId(), postProperties.getSecretKey());

            return interceptor;
        }
    }
}
