package com.jinstagram.domain.follow.service;

import com.jinstagram.domain.follow.dto.FollowResponse;
import com.jinstagram.domain.follow.entity.Follow;
import com.jinstagram.domain.follow.repository.FollowRepository;
import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;

    public Boolean follow (Long toMemberId){
        Member toMember = memberService.getMember(toMemberId);
        Member fromMember = memberService.findByEmail(SecurityUtil.getCurrentEmail());

        if (isFollow(fromMember.getId(),toMemberId)){
            Follow follow = getFollow(fromMember.getId(), toMemberId);
            unFollow(follow.getId());
            return false;
        }else{
            Follow follow = Follow.builder()
                    .fromMember(fromMember)
                    .toMember(toMember)
                    .build();
            followRepository.save(follow);
            return true;
        }
    }
    public void unFollow (Long id){
        Follow follow = followRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("팔로잉 하고 있지 않습니다."));
        followRepository.delete(follow);
    }

    public Page<FollowResponse> followings(Long fromMemberId, Pageable pageable) {
        Page<FollowResponse> followings = followRepository.followings(fromMemberId,pageable);
        Member loginMember = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        for (FollowResponse followResponse : followings.getContent()){
            followResponse.setFollow(isFollow(loginMember.getId(), followResponse.getMemberId()));
        }
        return followings;
    }
    public Page<FollowResponse> followers(Long toMemberId, Pageable pageable) {
        Page<FollowResponse> followers = followRepository.followers(toMemberId,pageable);
        Member loginMember = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        for (FollowResponse followResponse : followers.getContent()){
            followResponse.setFollow(isFollow(loginMember.getId(), followResponse.getMemberId()));
        }
        return followers;
    }

    private Boolean isFollow(Long fromMemberId, Long toMemberId) {
        return followRepository.existsByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
    }
    private Follow getFollow(Long fromMemberId, Long toMemberId) {
        return followRepository.findAllByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
    }


    public MemberResponse countFollow(MemberResponse memberResponse){
        memberResponse.setFollowings(followRepository.countAllByFromMemberId(memberResponse.getId()));
        memberResponse.setFollowers(followRepository.countAllByToMemberId(memberResponse.getId()));
        return memberResponse;
    }
}
