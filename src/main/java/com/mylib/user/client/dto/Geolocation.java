package com.mylib.user.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Geolocation {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;

    @Override
    public String toString() {
        return "Geolocation{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
