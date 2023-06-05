package com.jinstagram.domain.member.dto;

import com.jinstagram.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String mobile;
    private String description;
    private String imageUrl;
    private Long followings;
    private Long followers;
    private Long feedCnt;
    public MemberResponse(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.mobile = member.getMobile();
        this.description = member.getDescription();
        this.imageUrl = member.getImageUrl();
    }
}
