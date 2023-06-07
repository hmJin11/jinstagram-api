package com.jinstagram.domain.feed.service;

import com.jinstagram.domain.feed.dto.FeedCommentRequest;
import com.jinstagram.domain.feed.dto.FeedCommentResponse;
import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedComment;
import com.jinstagram.domain.feed.repository.FeedCommentRepository;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedCommentService {

    private final FeedCommentRepository feedCommentRepository;
    private final MemberService memberService;
    private final FeedService feedService;

    public FeedComment registerComment(FeedCommentRequest FeedCommentReqeust){
        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        Feed feed = feedService.getFeed(FeedCommentReqeust.getFeedId());
        FeedCommentReqeust.setMember(member);
        FeedCommentReqeust.setFeed(feed);

        if (FeedCommentReqeust.getParentCommentId() != null){
            FeedComment comment = feedCommentRepository.findById(FeedCommentReqeust.getParentCommentId()).orElseThrow(()->new IllegalArgumentException("상위 댓글이 존재하지 않습니다."));
            FeedCommentReqeust.setParentFeedComment(comment);
        }

        return feedCommentRepository.save(FeedCommentReqeust.toEntity());
    }
    public FeedComment updateComment(FeedCommentRequest feedCommentRequest){
        FeedComment feedComment = feedCommentRepository.findById(feedCommentRequest.getId()).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        checkAuthority(feedComment, member);

        feedComment.updateComment(feedCommentRequest.getContents());

        return feedComment;
    }
    public void deleteComment(Long id){
        FeedComment feedComment = feedCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        checkAuthority(feedComment, member);
        feedComment.deleteComment();
    }
    @Transactional(readOnly=true)
    public List<FeedCommentResponse> getComments(FeedCommentRequest feedCommentRequest) {
        List<FeedCommentResponse> list = feedCommentRepository.findByFeedIdAndParentFeedCommentIsNull(feedCommentRequest.getFeedId()).stream().map(s -> new FeedCommentResponse(s)).collect(Collectors.toList());
        for (FeedCommentResponse response : list){
            response.setChildCommentCnt(feedCommentRepository.countByParentFeedCommentId(response.getId()));
        }
        return list;
    }
    @Transactional(readOnly=true)
    public List<FeedCommentResponse> getComments(Long parentComentId) {
        List<FeedCommentResponse> list = feedCommentRepository.findByParentFeedCommentId(parentComentId).stream().map(s -> new FeedCommentResponse(s)).collect(Collectors.toList());
        for (FeedCommentResponse response : list){
            response.setChildCommentCnt(feedCommentRepository.countByParentFeedCommentId(response.getId()));
        }
        return list;
    }
    private void checkAuthority(FeedComment feedComment, Member member){
        if(!feedComment.getMember().getId().equals(member.getId())) throw new IllegalArgumentException("권한이 없습니다.");
    }
}
