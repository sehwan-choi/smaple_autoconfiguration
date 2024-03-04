package com.mylib.photo.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PhotoResponse {

    private Long id;

    private Long albumId;

    private String title;

    private String url;

    private String thumbnailUrl;
}
