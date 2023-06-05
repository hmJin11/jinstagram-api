package com.jinstagram.domain.feed.service;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedLike;
import com.jinstagram.domain.feed.repository.FeedLikeRepository;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;
    private final FeedService feedService;
    private final MemberService memberService;

    public Boolean likeFeed(Long feedId){
        Feed feed = feedService.getFeed(feedId);
        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        if (isExist(feedId,member.getId())){
            FeedLike feedLike = getFeedLike(feedId, member.getId());
            unLikeFeed(feedLike.getId());
            return false;
        }else{
            FeedLike feedLike = FeedLike.builder()
                    .feed(feed)
                    .member(member)
                    .build();
            feedLikeRepository.save(feedLike);
            return true;
        }
    }
    public void unLikeFeed(Long id){
        FeedLike feedLike = feedLikeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 피드에 좋아요를 누르고있지 않습니다."));
        feedLikeRepository.delete(feedLike);
    }

    public Boolean isExist(Long feedId, Long memberId){
        return feedLikeRepository.existsByFeedIdAndMemberId(feedId,memberId);
    }

    public FeedLike getFeedLike(Long feedId, Long memberId){
        return feedLikeRepository.findAllByFeedIdAndMemberId(feedId, memberId);
    }
}
