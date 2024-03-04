package com.mylib.photo.client;

import com.mylib.photo.client.dto.PhotoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PhotoApiClient {

    /**
     * Json Place Holder에서 제공하는 photos 데이터 전부 가져오기
     *
     * @return Photo 리스트
     * @see <a href="https://jsonplaceholder.typicode.com/photos">Json Place Holder에서 제공하는 photos 데이터 가져오기</a>
     */
    @GetMapping
    List<PhotoResponse> getPhotos();


    /**
     * Json Place Holder에서 제공하는 photos 데이터 가져오기
     *
     * @param id 검색할 photo 고유번호
     * @return   id로 찾아온 Photo
     * @see <a href="https://jsonplaceholder.typicode.com/photos/{id}">Json Place Holder에서 제공하는 photos 데이터 id값으로 가져오기</a>
     */
    @GetMapping("/{id}")
    PhotoResponse getPhoto(@PathVariable("id") String id);

}
