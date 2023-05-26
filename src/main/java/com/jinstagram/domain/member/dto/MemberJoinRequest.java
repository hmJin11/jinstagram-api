package com.jinstagram.domain.member.dto;

import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String mobile;
    private Boolean smsAgree;
    private Boolean emailAgree;

    public Member toEntity(){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .nickname(this.nickname)
                .mobile(this.mobile)
                .smsAgree(this.smsAgree)
                .emailAgree(this.emailAgree)
                .role(Role.USER)
                .build();
    }
}
