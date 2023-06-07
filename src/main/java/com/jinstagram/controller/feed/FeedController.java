package com.jinstagram.controller.feed;

import com.jinstagram.domain.feed.dto.*;
import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedComment;
import com.jinstagram.domain.feed.entity.FeedImage;
import com.jinstagram.domain.feed.service.FeedCommentService;
import com.jinstagram.domain.feed.service.FeedImageService;
import com.jinstagram.domain.feed.service.FeedLikeService;
import com.jinstagram.domain.feed.service.FeedService;
import com.jinstagram.global.common.PageInfo;
import com.jinstagram.global.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/feeds")
@Tag(name = "FeedController" , description = "피드와 관련된 컨트롤러")
public class FeedController {

    private final FeedService feedService;
    private final FeedLikeService feedLikeService;
    private final FeedImageService feedImageService;
    private final FeedCommentService feedCommentService;
    @GetMapping(value = "/{id}")
    @Operation(summary = "단건조회", description = "단건조회")
    public Result getFeed(@PathVariable(value = "id") Long id){
        FeedResponse feedResponse = new FeedResponse(feedService.getFeed(id));
        feedResponse.setFeedImages(feedImageService.getFeedImages(id).stream().map(s -> new FeedImageResponse(s)).collect(Collectors.toList()));
        return new Result(feedResponse);
    }
    @GetMapping
    @Operation(summary = "다건조회", description = "다건조회(이메일, 이름, 닉네임)")
    public Result getFeeds(@RequestBody(required = false) FeedSearch feedSearch){
        FeedSearch search = new FeedSearch();
        if (feedSearch != null){
            search = feedSearch;
        }
        Pageable pageable = PageRequest.of(search.getPageNum() , search.getPageSize());
        Page<FeedResponse> feedResponses = feedService.getFeeds(search, pageable);
        for (FeedResponse feed : feedResponses.getContent()){
            feed.setFeedImages(feedImageService.getFeedImages(feed.getId()).stream().map(s -> new FeedImageResponse(s)).collect(Collectors.toList()));
        }
        return new Result(feedResponses.getContent(), new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), (int) feedResponses.getTotalElements(),feedResponses.getTotalPages()));
    }

    @PostMapping
    @Operation(summary = "피드 등록", description = "피드 등록(글, 이미지)")
    public Result postFeed(FeedPostRequest feedPostRequest) throws Exception{
        Feed feed = feedService.postFeed(feedPostRequest);
        if (feedPostRequest.getImageFile() != null){
            feedImageService.uploadImage(feed, feedPostRequest.getImageFile());
        }
        FeedResponse feedResponse = new FeedResponse(feedService.getFeed(feed.getId()));
        feedResponse.setFeedImages(feedImageService.getFeedImages(feed.getId()).stream().map(s -> new FeedImageResponse(s)).collect(Collectors.toList()));
        return new Result(feedResponse);
    }

    @PutMapping
    @Operation(summary = "피드 수정", description = "피드 수정(글, 이미지)")
    public Result updateFeed(@RequestBody FeedUpdateRequest feedUpdateRequest) throws Exception{
        Feed feed = feedService.updateFeed(feedUpdateRequest);
        if (feedUpdateRequest.getImageFile() != null){
            feedImageService.uploadImage(feed, feedUpdateRequest.getImageFile());
        }
        return new Result(new FeedResponse(feed));
    }

    @DeleteMapping(value="/{id}")
    @Operation(summary = "피드 삭제", description = "피드 삭제")
    public Result deleteFeed(@PathVariable(value = "id") Long id){
        feedService.deleteFeed(id);
        return new Result("");
    }


    @PostMapping(value="/like/{id}")
    @Operation(summary = "피드 좋아요, 취소", description = "피드 좋아요, 취소")
    public Result likeFeed(@PathVariable(value = "id") Long id){
        return new Result(feedLikeService.likeFeed(id));
    }

    @PostMapping(value="/comments")
    public Result saveFeedComment(@RequestBody FeedCommentRequest feedCommentRequest){
        FeedCommentResponse feedCommentResponse = new FeedCommentResponse(feedCommentService.registerComment(feedCommentRequest));
        return new Result(feedCommentResponse);
    }

    @PutMapping(value="/comments")
    public Result updateFeedComment(@RequestBody FeedCommentRequest feedCommentRequest){
        FeedCommentResponse feedCommentResponse = new FeedCommentResponse(feedCommentService.updateComment(feedCommentRequest));
        return new Result(feedCommentResponse);
    }

    @GetMapping(value="/comments")
    public Result getFeedComments(@RequestBody FeedCommentRequest feedCommentRequest){
        return new Result(feedCommentService.getComments(feedCommentRequest));
    }

    @GetMapping(value="/comments/{id}")
    public Result getFeedChildComments(@PathVariable(value = "id") Long parentCommentId ){
        return new Result(feedCommentService.getComments(parentCommentId));
    }

    @DeleteMapping(value="/comments/{id}")
    public Result deleteFeedComment(@PathVariable(value = "id") Long commentId){
        feedCommentService.deleteComment(commentId);
        return new Result("");
    }
}
