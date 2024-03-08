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
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class UserApiConfiguration extends ClientConfigurationSupport {

    private final UserClientProperties userProperties;

    public UserApiConfiguration(UserClientProperties userProperties) {
        super(EnableUserApiClients.class.getName());
        this.userProperties = userProperties;
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
        if (StringUtils.hasText(super.annotationInfo.getCredentialsInterceptorName())) {
            return context.getBean(super.annotationInfo.getCredentialsInterceptorName(), RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(userProperties.getClientId(), userProperties.getSecretKey());

            return interceptor;
        }
    }
}
