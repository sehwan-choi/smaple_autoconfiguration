package com.mylib.user.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    @JsonProperty("username")
    private String userName;

    private String email;

    private UserAddress address;

    private String phone;

    private String website;

    private UserCompany company;
}
