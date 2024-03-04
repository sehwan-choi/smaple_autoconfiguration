package com.mylib.user.client;

import com.mylib.user.client.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserApiClient {

    @GetMapping
    List<UserResponse> getUsers();

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId);

    @PutMapping("/{userId}")
    UserResponse updateUserWithAll(@PathVariable("userId") String userId);

    @PatchMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId);

    @DeleteMapping("/{userId}")
    UserResponse deleteUser(@PathVariable("userId") String userId);

}
