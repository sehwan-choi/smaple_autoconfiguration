package com.mylib.common;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import lombok.Value;

public class AuthClientRequestInterceptor implements RequestInterceptor {

    private static final String DEFAULT_CLIENT_ID_NAME = "Client-Id";
    private static final String DEFAULT_CLIENT_SECRET_NAME = "Client-Secret";

    private ClientAuthentication authentication;

    @Setter
    private String clientIdName = DEFAULT_CLIENT_ID_NAME;

    @Setter
    private String clientSecretName = DEFAULT_CLIENT_SECRET_NAME;

    public void setAuthentication(String clientId, String clientSecret) {
        this.authentication = new ClientAuthentication(clientId, clientSecret);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(clientIdName, authentication.getClientId());
        requestTemplate.header(clientSecretName, authentication.getClientSecret());
    }

    @Value
    public static class ClientAuthentication {
        String clientId;

        String clientSecret;
    }
}
