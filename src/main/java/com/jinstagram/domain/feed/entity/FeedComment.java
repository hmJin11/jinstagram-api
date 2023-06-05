package com.jinstagram.domain.feed.entity;

import com.jinstagram.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicUpdate
public class FeedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
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
    @JoinColumn(name="parentCommentId")
    private FeedComment feedComment;

    public void updateComment(String contents){
        this.contents = contents;
    }
}
