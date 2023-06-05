package com.jinstagram.domain.feed.service;

import com.jinstagram.domain.feed.dto.FeedCommentRequest;
import com.jinstagram.domain.feed.repository.FeedCommentRepository;
import com.jinstagram.domain.feed.repository.FeedfeedCommentRepository;
import com.jinstagram.domain.member.service.MemberService;
import com.vstagram.domain.comment.Comment;
import com.vstagram.domain.feed.Feed;
import com.vstagram.domain.member.Member;
import com.vstagram.dto.comment.FeedCommentReqeust;
import com.vstagram.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public Long registerComment(FeedCommentRequest FeedCommentReqeust){
        Member member = memberService.findByLoginId(SecurityUtil.getCurrentLoginId());
        Feed feed = feedService.getFeed(FeedCommentReqeust.getFeedId());
        FeedCommentReqeust.setMember(member);
        FeedCommentReqeust.setFeed(feed);

        if (FeedCommentReqeust.getParentCommentId() != null){
            Comment comment = feedCommentRepository.getById(FeedCommentReqeust.getParentCommentId());
            FeedCommentReqeust.setComment(comment);
        }

        return feedCommentRepository.save(FeedCommentReqeust.toEntity()).getId();
    }
    public Long updateComment(FeedCommentReqeust FeedCommentReqeust){
        Comment comment = feedCommentRepository.findById(FeedCommentReqeust.getId()).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        Member member = memberService.findByLoginId(SecurityUtil.getCurrentLoginId());
        checkAuthority(comment, member);

        comment.updateFeed(FeedCommentReqeust.getContents());

        return comment.getId();
    }
    public void deleteComment(Long id){
        Comment comment = feedCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        Member member = memberService.findByLoginId(SecurityUtil.getCurrentLoginId());
        checkAuthority(comment, member);

        feedCommentRepository.deleteById(comment.getId());
    }

    @Transactional(readOnly=true)
    public FeedCommentReqeust getChildComments(Long Comment_id) {
        Comment comment = feedCommentRepository.findById(Comment_id).orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));
        return new FeedCommentReqeust(comment);
    }

    @Transactional(readOnly=true)
    public List<FeedCommentReqeust> getComments(Long feed_id) {
        return feedCommentRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(FeedCommentReqeust::new).collect(Collectors.toList());
    }

    private void checkAuthority(Comment comment, Member member){
        if(!comment.getMember().getId().equals(member.getId())) throw new IllegalArgumentException("권한이 없습니다.");
    }
}
