package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedCommentResponse {

    private Long id;
    private Long feedId;
    private String contents;
    private String nickname;
    private Long childCommentCnt;
    private LocalDateTime modifiedDate;
    public FeedCommentResponse(FeedComment feedComment){
        this.id = feedComment.getId();
        this.feedId = feedComment.getFeed().getId();
        this.nickname = feedComment.getMember().getNickname();
        this.contents = feedComment.getContents();
        this.modifiedDate = feedComment.getModifiedDate();
    }

}
