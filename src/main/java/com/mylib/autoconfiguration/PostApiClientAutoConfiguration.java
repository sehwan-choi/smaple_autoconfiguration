package com.mylib.autoconfiguration;

import com.mylib.post.EnablePostApiClients;
import com.mylib.post.PostClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.mylib.autoconfiguration.PostApiClientAutoConfiguration.POST_API_CLIENT_AUTO_CONFIG;

@Configuration
//@EnablePostApiClients
@EnableConfigurationProperties(PostClientProperties.class)
@ConditionalOnProperty(value = POST_API_CLIENT_AUTO_CONFIG, havingValue = "true", matchIfMissing = true)
public class PostApiClientAutoConfiguration {
    public static final String POST_API_CLIENT_AUTO_CONFIG = "custom.client.api.post.enable";
}
