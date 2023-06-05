package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {

    private Long id;
    private Long memberId;
    private String nickname;
    private String contents;
    private LocalDateTime modifiedDate;
    private Long likeCount;
    private List<FeedImageResponse> feedImages;
    public FeedResponse(Feed feed){
        this.id = feed.getId();
        this.memberId = feed.getMember().getId();
        this.nickname = feed.getMember().getNickname();
        this.contents = feed.getContents();
        this.modifiedDate = feed.getModifiedDate();
        this.likeCount = 0L;
    }
}
