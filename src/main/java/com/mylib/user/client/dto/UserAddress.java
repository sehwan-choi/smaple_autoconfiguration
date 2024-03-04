package com.mylib.user.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class UserAddress {
    private String street;

    private String suite;

    private String city;

    private String zipcode;

    @JsonProperty("geo")
    private Geolocation geolocation;
}
