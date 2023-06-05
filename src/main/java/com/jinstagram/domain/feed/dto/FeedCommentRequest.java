package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedComment;
import com.jinstagram.domain.member.entity.Member;
import com.vstagram.domain.comment.Comment;
import com.vstagram.domain.feed.Feed;
import com.vstagram.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedCommentRequest {
    private Long id;
    private String contents;
    private Long feedId;
    private Long memberId;
    private Long parentCommentId;
    private Feed feed;
    private Member member;
    private FeedComment feedComment;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public FeedComment toEntity(){
        return FeedComment.builder()
                .id(this.id)
                .feed(this.feed)
                .contents(this.contents)
                .member(this.member)
                .feedComment(this.feedComment)
                .build();
    }

    public FeedCommentRequest(Comment comment){
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.feed = comment.getFeed();
        this.member = comment.getMember();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }

}
