package com.mylib.post.service;

import com.mylib.post.client.PostApiClient;
import com.mylib.post.client.dto.PostResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostApiClientService implements PostApiService {

    private final PostApiClient apiClient;

    @Override
    public List<PostResponse> getPosts() {
        return apiClient.getPosts();
    }

    @Override
    public PostResponse getPost(String id) {
        return apiClient.getPost(id);
    }
}
