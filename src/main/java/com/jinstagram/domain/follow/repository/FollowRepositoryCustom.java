package com.jinstagram.domain.follow.repository;

import com.jinstagram.domain.follow.dto.FollowResponse;
import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.dto.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowRepositoryCustom {
    Page<FollowResponse> followings(Long fromMemberId, Pageable pageable);
    Page<FollowResponse> followers(Long toMemberId, Pageable pageable);

}


