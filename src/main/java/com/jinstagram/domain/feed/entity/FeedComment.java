package com.jinstagram.domain.feed.entity;

import com.jinstagram.domain.BaseTimeEntity;
import com.jinstagram.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicUpdate
public class FeedComment  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_comment_id")
    private Long id;

    @NotNull
    @NotBlank
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feedId")
    @NotNull
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parentFeedCommentId")
    private FeedComment parentFeedComment;

    @Column(nullable = false)
    private Boolean deleted;

    public void updateComment(String contents){
        this.contents = contents;
    }

    public void deleteComment(){
        this.deleted = true;
    }
}
