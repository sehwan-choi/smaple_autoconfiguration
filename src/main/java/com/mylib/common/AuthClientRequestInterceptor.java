package com.mylib.common;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthClientRequestInterceptor implements RequestInterceptor {

    private static final String DEFAULT_CLIENT_ID_NAME = "Client-Id";
    private static final String DEFAULT_CLIENT_SECRET_NAME = "Client-Secret";

    private ClientAuthentication authentication;

    private String clientIdName = DEFAULT_CLIENT_ID_NAME;

    private String clientSecretName = DEFAULT_CLIENT_SECRET_NAME;

    public void setAuthentication(String clientId, String clientSecret) {
        this.authentication = new ClientAuthentication(clientId, clientSecret);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(clientIdName, authentication.getClientId());
        requestTemplate.header(clientSecretName, authentication.getClientSecret());
    }


    public static class ClientAuthentication {
        String clientId;

        String clientSecret;

        public ClientAuthentication(String clientId, String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }
    }

    public void setClientIdName(String clientIdName) {
        this.clientIdName = clientIdName;
    }

    public void setClientSecretName(String clientSecretName) {
        this.clientSecretName = clientSecretName;
    }
}
