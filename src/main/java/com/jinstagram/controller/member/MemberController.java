package com.jinstagram.controller.member;

import com.jinstagram.domain.member.dto.MemberJoinRequest;
import com.jinstagram.domain.member.dto.MemberResponse;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.common.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/join")
    public Result join(@RequestBody MemberJoinRequest memberJoinRequest){
        MemberResponse memberResponse = new MemberResponse(memberService.join(memberJoinRequest));
        return new Result(memberResponse);
    }

    @GetMapping(value = "/{id}")
    public Result getMember(@PathVariable Long id){
        MemberResponse memberResponse = new MemberResponse(memberService.getMember(id));
        return new Result(memberResponse);
    }

}
