package com.jinstagram.domain.follow.repository;

import com.jinstagram.domain.follow.dto.FollowResponse;
import com.jinstagram.domain.follow.entity.Follow;
import com.jinstagram.domain.follow.entity.QFollow;
import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.dto.MemberSearch;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.entity.QMember;
import com.jinstagram.domain.member.repository.MemberRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QMember member = QMember.member;
    QFollow follow = QFollow.follow;
    public FollowRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<FollowResponse> followings(Long fromMemberId, Pageable pageable) {
        List<FollowResponse> results = jpaQueryFactory
                .select(Projections.fields(
                                FollowResponse.class,
                                follow.id,
                                member.id.as("memberId"),
                                member.name,
                                member.email,
                                member.nickname,
                                member.imageUrl
                        )
                )
                .from(follow)
                .join(follow.toMember, member)
                .where(follow.fromMember.id.eq(fromMemberId)
                        ,member.deleted.isFalse())
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Follow> countQuery = jpaQueryFactory
                .select(follow)
                .from(follow)
                .join(follow.toMember, member)
                .where(follow.fromMember.id.eq(fromMemberId)
                        ,member.deleted.isFalse());

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<FollowResponse> followers(Long toMemberId, Pageable pageable) {
        List<FollowResponse> results = jpaQueryFactory
                .select(Projections.fields(
                                FollowResponse.class,
                                follow.id,
                                member.id.as("memberId"),
                                member.name,
                                member.email,
                                member.nickname,
                                member.imageUrl
                        )
                )
                .from(follow)
                .join(follow.fromMember, member)
                .where(follow.toMember.id.eq(toMemberId)
                        ,member.deleted.isFalse())
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Follow> countQuery = jpaQueryFactory
                .select(follow)
                .from(follow)
                .join(follow.fromMember, member)
                .where(follow.toMember.id.eq(toMemberId)
                        ,member.deleted.isFalse());

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

}


