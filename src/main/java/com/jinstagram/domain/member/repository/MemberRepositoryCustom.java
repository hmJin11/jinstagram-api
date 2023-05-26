package com.jinstagram.domain.member.repository;

import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.dto.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<MemberResponse> searchMembers(MemberSearch memberSearch, Pageable pageable);

}


