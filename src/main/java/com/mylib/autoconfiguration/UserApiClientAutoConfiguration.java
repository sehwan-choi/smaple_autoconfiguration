package com.mylib.autoconfiguration;

import com.mylib.common.AuthClientRequestInterceptor;
import com.mylib.common.ClientConfigFactory;
import com.mylib.common.ClientConfigurationSupport;
import com.mylib.user.EnableUserApiClients;
import com.mylib.user.UserClientProperties;
import com.mylib.user.client.UserApiClient;
import com.mylib.user.service.UserApiClientService;
import com.mylib.user.service.UserApiService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mylib.autoconfiguration.UserApiClientAutoConfiguration.USER_API_CLIENT_AUTO_CONFIG;

@Configuration
@EnableConfigurationProperties(UserClientProperties.class)
@ConditionalOnProperty(value = USER_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class UserApiClientAutoConfiguration extends ClientConfigurationSupport {

    public static final String USER_API_CLIENT_AUTO_CONFIG = "custom.client.api.user.enable";

    private final UserClientProperties userProperties;

    public UserApiClientAutoConfiguration(UserClientProperties userProperties) {
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
                .logLevel(getLevel())
                .requestInterceptor(getCredentialsHeaderInterceptor())
                .target(UserApiClient.class, userProperties.getBaseUrl());
    }

    @Bean
    public UserApiService userApiService(UserApiClient client) {
        return new UserApiClientService(client);
    }

    public RequestInterceptor getCredentialsHeaderInterceptor() {
        if (super.hasCredentialsInterceptorName()) {
            return context.getBean(super.annotationInfo.getCredentialsInterceptorName(), RequestInterceptor.class);
        } else {
            AuthClientRequestInterceptor interceptor = new AuthClientRequestInterceptor();

            interceptor.setAuthentication(userProperties.getClientId(), userProperties.getSecretKey());

            return interceptor;
        }
    }
}
