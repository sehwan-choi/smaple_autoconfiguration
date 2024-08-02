package com.mylib.autoconfiguration;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.post.EnablePostApiClients;
import com.mylib.post.PostClientProperties;
import com.mylib.post.client.PostApiClient;
import com.mylib.post.service.PostApiClientService;
import com.mylib.post.service.PostApiService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mylib.autoconfiguration.PostApiClientAutoConfiguration.POST_API_CLIENT_AUTO_CONFIG;

@Configuration
@EnableConfigurationProperties(PostClientProperties.class)
@ConditionalOnProperty(value = POST_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class PostApiClientAutoConfiguration extends ClientConfigurationSupport {

    public static final String POST_API_CLIENT_AUTO_CONFIG = "custom.client.api.post.enable";

    private final PostClientProperties postProperties;

    public PostApiClientAutoConfiguration(PostClientProperties postProperties) {
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
                .logLevel(getLevel())
                .requestInterceptor(getCredentialsHeaderInterceptor())
                .target(PostApiClient.class, postProperties.getBaseUrl());
    }

    @Bean
    public PostApiService postApiService(PostApiClient client) {
        return new PostApiClientService(client);
    }

    public RequestInterceptor getCredentialsHeaderInterceptor() {
        if (super.hasCredentialsInterceptorName()) {
            return context.getBean(super.annotationInfo.getCredentialsInterceptorName(), RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(postProperties.getClientId(), postProperties.getSecretKey());

            return interceptor;
        }
    }
}
