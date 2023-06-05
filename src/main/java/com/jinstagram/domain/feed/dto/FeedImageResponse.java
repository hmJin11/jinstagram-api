package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.feed.entity.FeedImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedImageResponse {
    private Long id;
    private String imageUrl;
    public FeedImageResponse (FeedImage feedImage){
        this.id = feedImage.getId();
        this.imageUrl = "/api/v1/image/feeds/"+feedImage.getFileKey();
    }
}
