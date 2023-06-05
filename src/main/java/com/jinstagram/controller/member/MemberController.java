package com.jinstagram.controller.member;

import com.jinstagram.domain.follow.dto.FollowResponse;
import com.jinstagram.domain.follow.service.FollowService;
import com.jinstagram.domain.member.dto.MemberJoinRequest;
import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.dto.MemberSearch;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.common.CommonSearch;
import com.jinstagram.global.common.PageInfo;
import com.jinstagram.global.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "MemberController" , description = "회원과 관련된 컨트롤러")
public class MemberController {

    private final MemberService memberService;
    private final FollowService followService;

    @PostMapping(value = "/join")
    @Operation(summary = "회원가입", description = "회원가입 진행")
    public Result join(@RequestBody MemberJoinRequest memberJoinRequest){
        MemberResponse memberResponse = new MemberResponse(memberService.join(memberJoinRequest));
        return new Result(memberResponse);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "단건조회", description = "단건조회 id값필요")
    public Result getMember(@PathVariable Long id){
        MemberResponse memberResponse = new MemberResponse(memberService.getMember(id));
        MemberResponse memberResponse1 = followService.countFollow(memberResponse);
        return new Result(memberResponse1);
    }

    @GetMapping
    @Operation(summary = "다건조회", description = "다건조회(이메일, 이름, 닉네임)")
    public Result members(@RequestBody(required = false) MemberSearch memberSearch){
        MemberSearch search = new MemberSearch();
        if (memberSearch != null)
            search = memberSearch;
        Pageable pageable = PageRequest.of(search.getPageNum() , search.getPageSize());
        Page<MemberResponse> memberResponses = memberService.searchMembers(search, pageable);
        return new Result(memberResponses.getContent(), new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), (int) memberResponses.getTotalElements(),memberResponses.getTotalPages()));
    }

    @PostMapping(value = "/follow/{id}")
    @Operation(summary = "팔로우", description = "팔로우")
    public Result follow(@PathVariable(value = "id") Long toMemberId){
        return new Result(followService.follow(toMemberId));
    }
    @GetMapping(value = "/followings/{id}")
    @Operation(summary = "팔로윙 조회", description = "팔로우 하고있는 사람")
    public Result followings(@PathVariable(value = "id") Long fromMemberId, @RequestBody(required = false) CommonSearch commonSearch){
        CommonSearch search = new CommonSearch();
        if (commonSearch != null)
            search = commonSearch;
        Pageable pageable = PageRequest.of(search.getPageNum() , search.getPageSize());
        Page<FollowResponse> followings = followService.followings(fromMemberId, pageable);
        return new Result(followings.getContent(), new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), (int) followings.getTotalElements(),followings.getTotalPages()));
    }
    @GetMapping(value = "/followers/{id}")
    @Operation(summary = "팔로워 조회", description = "나를 팔로우 하고 있는사람")
    public Result followers(@PathVariable(value = "id") Long toMemberId, @RequestBody(required = false) CommonSearch commonSearch){
        CommonSearch search = new CommonSearch();
        if (commonSearch != null)
            search = commonSearch;
        Pageable pageable = PageRequest.of(search.getPageNum() , search.getPageSize());
        Page<FollowResponse> followings = followService.followers(toMemberId, pageable);
        return new Result(followings.getContent(), new PageInfo(pageable.getPageNumber(), pageable.getPageSize(), (int) followings.getTotalElements(),followings.getTotalPages()));
    }

}
