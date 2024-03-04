package com.mylib.user.service;

import com.mylib.user.client.dto.UserResponse;

import java.util.List;

public interface UserApiService {

    List<UserResponse> getUsers();

    UserResponse getUser(String userId);
}
