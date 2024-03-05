package com.mylib.post.service;

import com.mylib.post.client.PostApiClient;
import com.mylib.post.client.dto.PostResponse;

import java.util.List;

public class PostApiClientService implements PostApiService {

    private final PostApiClient apiClient;

    public PostApiClientService(PostApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public List<PostResponse> getPosts() {
        return apiClient.getPosts();
    }

    @Override
    public PostResponse getPost(String id) {
        return apiClient.getPost(id);
    }
}
