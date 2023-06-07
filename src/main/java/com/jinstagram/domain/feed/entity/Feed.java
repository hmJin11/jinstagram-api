package com.jinstagram.domain.feed.entity;

import com.jinstagram.domain.BaseTimeEntity;
import com.jinstagram.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Feed extends BaseTimeEntity {

    @Id
    @Column(name = "feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @Column(columnDefinition = "Text")
    private String contents;

    @Column(nullable = false)
    private Boolean deleted;
    @Builder
    public Feed (Member member, String contents, Boolean deleted){
        this.member = member;
        this.contents = contents;
        this.deleted = deleted;
    }

    public void updateFeed(String contents){
        this.contents = contents;
    }

    public void deleteFeed(){
        this.deleted = true;
    }

}
