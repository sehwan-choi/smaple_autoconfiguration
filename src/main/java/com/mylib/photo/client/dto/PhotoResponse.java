package com.mylib.photo.client.dto;

public class PhotoResponse {

    private Long id;

    private Long albumId;

    private String title;

    private String url;

    private String thumbnailUrl;

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
