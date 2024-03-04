package com.mylib.user.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class Geolocation {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;
}
