package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.BaseTimeEntity;
import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedComment;
import com.jinstagram.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedCommentRequest{
    private Long id;
    private String contents;
    private Long feedId;
    private Long parentCommentId;

    private Feed feed;
    private Member member;
    private FeedComment parentFeedComment;

    public FeedComment toEntity(){
        return FeedComment.builder()
                .id(this.id)
                .feed(this.feed)
                .contents(this.contents)
                .member(this.member)
                .parentFeedComment(this.parentFeedComment)
                .deleted(false)
                .build();
    }

}
