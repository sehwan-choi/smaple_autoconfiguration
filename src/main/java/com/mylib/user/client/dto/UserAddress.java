package com.mylib.user.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserAddress {
    private String street;

    private String suite;

    private String city;

    private String zipcode;

    @JsonProperty("geo")
    private Geolocation geolocation;

    @Override
    public String toString() {
        return "UserAddress{" +
                "street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", geolocation=" + geolocation +
                '}';
    }

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }
}
