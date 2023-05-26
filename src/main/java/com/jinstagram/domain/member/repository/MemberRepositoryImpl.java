package com.jinstagram.domain.member.repository;

import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.dto.MemberSearch;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    QMember member = QMember.member;
    public MemberRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberResponse> searchMembers(MemberSearch memberSearch, Pageable pageable) {
        List<MemberResponse> results = jpaQueryFactory
                .select(Projections.fields(
                                MemberResponse.class,
                                member.id,
                                member.name,
                                member.nickname,
                                member.email,
                                member.mobile,
                                member.description,
                                member.imageUrl
                        )
                )
                .from(member)
                .where(member.id.ne(memberSearch.getId())
                        ,member.deleted.isFalse()
                        ,search(memberSearch.getSearchString()))
                .orderBy(member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.id.ne(memberSearch.getId())
                        ,member.deleted.isFalse()
                        ,search(memberSearch.getSearchString()));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    public BooleanExpression search(String searchString){
        return searchString != null ? member.email.contains(searchString)
                .or(member.nickname.contains(searchString))
                .or(member.name.contains(searchString)) : null;



    }
}


