package com.mylib.photo.service;

import com.mylib.photo.client.dto.PhotoResponse;

import java.util.List;

public interface PhotoApiService {

    List<PhotoResponse> getPhotos();

    PhotoResponse getPhoto(String id);
}
