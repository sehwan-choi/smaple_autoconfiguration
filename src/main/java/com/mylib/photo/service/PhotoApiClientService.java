package com.mylib.photo.service;

import com.mylib.photo.client.PhotoApiClient;
import com.mylib.photo.client.dto.PhotoResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PhotoApiClientService implements PhotoApiService {

    private final PhotoApiClient apiClient;

    @Override
    public List<PhotoResponse> getPhotos() {
        return apiClient.getPhotos();
    }

    @Override
    public PhotoResponse getPhoto(String userId) {
        return apiClient.getPhoto(userId);
    }
}
