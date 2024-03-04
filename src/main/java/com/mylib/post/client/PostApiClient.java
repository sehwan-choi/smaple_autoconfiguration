package com.mylib.post.client;

import com.mylib.post.client.dto.PostResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PostApiClient {


    /**
     * Json Place Holder에서 제공하는 Post 데이터 전부 가져오기
     *
     * @return Photo 리스트
     * @see <a href="https://jsonplaceholder.typicode.com/posts">Json Place Holder에서 제공하는 posts 데이터 전부 가져오기</a>
     */
    @GetMapping
    List<PostResponse> getPosts();

    /**
     * Json Place Holder에서 제공하는 photos 데이터 가져오기
     *
     * @param id 검색할 photo 고유번호
     * @return   id로 찾아온 Photo
     * @see <a href="https://jsonplaceholder.typicode.com/post/{id}">Json Place Holder에서 제공하는 post 데이터 id값으로 가져오기</a>
     */
    @GetMapping("/{id}")
    PostResponse getPost(@PathVariable("id") String id);

}
