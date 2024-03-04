package com.mylib.post.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PostResponse {

    private Long id;

    private Long userId;

    private String title;

    private String body;
}
