package com.mylib.photo.service;

import com.mylib.photo.client.PhotoApiClient;
import com.mylib.photo.client.dto.PhotoResponse;

import java.util.List;

public class PhotoApiClientService implements PhotoApiService {

    private final PhotoApiClient apiClient;

    public PhotoApiClientService(PhotoApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public List<PhotoResponse> getPhotos() {
        return apiClient.getPhotos();
    }

    @Override
    public PhotoResponse getPhoto(String userId) {
        return apiClient.getPhoto(userId);
    }
}
