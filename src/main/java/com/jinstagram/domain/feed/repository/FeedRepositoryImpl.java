package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.dto.response.FeedResponse;
import com.jinstagram.domain.feed.dto.FeedSearch;
import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.QFeed;
import com.jinstagram.domain.feed.entity.QFeedLike;
import com.jinstagram.domain.member.entity.QMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QMember member = QMember.member;
    QFeed feed = QFeed.feed;
    QFeedLike feedLike = QFeedLike.feedLike;
    public FeedRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<FeedResponse> searchFeeds(FeedSearch feedSearch, Pageable pageable) {
        List<FeedResponse> results = jpaQueryFactory
                .select(Projections.fields(
                                FeedResponse.class,
                                feed.id,
                                feed.contents,
                                member.id.as("memberId"),
                                member.nickname,
                                feed.modifiedDate,
                                ExpressionUtils.as(
                                JPAExpressions.select(feedLike.count())
                                        .from(feedLike)
                                        .where(feedLike.feed.eq(feed)),
                                "likeCount")
                        )
                )
                .from(feed)
                .join(feed.member, member)
                .where(member.deleted.isFalse()
                        ,feed.deleted.isFalse())
                .orderBy(feed.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Feed> countQuery = jpaQueryFactory
                .select(feed)
                .from(feed)
                .join(feed.member, member)
                .where(member.deleted.isFalse()
                        ,feed.deleted.isFalse());

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

}


