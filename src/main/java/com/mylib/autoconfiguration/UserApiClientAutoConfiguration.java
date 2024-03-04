package com.mylib.autoconfiguration;

import com.mylib.user.EnableUserApiClients;
import com.mylib.user.UserClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.autoconfiguration.UserApiClientAutoConfiguration.USER_API_CLIENT_AUTO_CONFIG;

@Configuration
//@EnableUserApiClients
@EnableConfigurationProperties(UserClientProperties.class)
@ConditionalOnProperty(value = USER_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class UserApiClientAutoConfiguration {
    public static final String USER_API_CLIENT_AUTO_CONFIG = "custom.client.api.user.enable";
}
