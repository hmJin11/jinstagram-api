package com.jinstagram.domain.feed.entity;

import com.jinstagram.domain.BaseTimeEntity;
import com.jinstagram.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name="feed_like",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "feedLike_uk",
                        columnNames={"feedId","memberId"}
                )
        }
) //중복으로 될수있는걸 막기
public class FeedLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feedId")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @Builder
    public FeedLike(Feed feed, Member member){
        this.member = member;
        this.feed = feed;
    }
}
