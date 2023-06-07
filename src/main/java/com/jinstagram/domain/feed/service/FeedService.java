package com.jinstagram.domain.feed.service;

import com.jinstagram.domain.feed.dto.FeedPostRequest;
import com.jinstagram.domain.feed.dto.FeedResponse;
import com.jinstagram.domain.feed.dto.FeedSearch;
import com.jinstagram.domain.feed.dto.FeedUpdateRequest;
import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.repository.FeedRepository;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.common.service.FileService;
import com.jinstagram.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final MemberService memberService;

    public Feed postFeed(FeedPostRequest feedPostRequest) throws Exception {
        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        feedPostRequest.setMember(member);
        Feed saveFeed = feedRepository.save(feedPostRequest.toEntity());
        return saveFeed;
    }
    public Feed updateFeed(FeedUpdateRequest feedUpdateRequest) throws Exception{
        Feed feed = feedRepository.findById(feedUpdateRequest.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));
        if(isGrant(feed)){
            feed.updateFeed(feedUpdateRequest.getContents());
        }
        return feed;
    }
    public void deleteFeed(Long id){
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));
        if(isGrant(feed)){
            feed.deleteFeed();
        }
    }
    public Feed getFeed(Long id){
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));
        return feed;
    }
    public Page<FeedResponse> getFeeds(FeedSearch feedSearch, Pageable pageable){
        return feedRepository.searchFeeds(feedSearch, pageable);
    }

    public Boolean isGrant(Feed feed){
        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        if(feed.getMember().getId() != member.getId()){
            throw new IllegalArgumentException("권한이 없습니다.");
        }else{
            return true;
        }
    }

}
