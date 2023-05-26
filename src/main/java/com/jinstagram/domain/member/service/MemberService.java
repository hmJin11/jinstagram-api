package com.jinstagram.domain.member.service;

import com.jinstagram.domain.member.dto.MemberJoinRequest;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(MemberJoinRequest memberJoinRequest) {
        if(memberRepository.findByEmail(memberJoinRequest.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        if(memberRepository.findByNickname(memberJoinRequest.getNickname()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        Member member = memberJoinRequest.toEntity();
        member.passwordEncode(passwordEncoder);
        return memberRepository.save(member);
    }

    public Member getMember(Long id){
        return memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("등록된 회원이 아닙니다."));
    }
}
