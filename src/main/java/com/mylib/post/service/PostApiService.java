package com.mylib.post.service;

import com.mylib.post.client.dto.PostResponse;

import java.util.List;

public interface PostApiService {

    List<PostResponse> getPosts();

    PostResponse getPost(String id);
}
