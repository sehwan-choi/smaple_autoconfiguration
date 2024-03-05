package com.mylib.user.service;

import com.mylib.user.client.UserApiClient;
import com.mylib.user.client.dto.UserResponse;

import java.util.List;

public class UserApiClientService implements UserApiService {

    private final UserApiClient apiClient;

    public UserApiClientService(UserApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public List<UserResponse> getUsers() {
        return apiClient.getUsers();
    }

    @Override
    public UserResponse getUser(String userId) {
        return apiClient.getUser(userId);
    }
}
