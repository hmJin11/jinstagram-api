package com.jinstagram.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jinstagram.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","email"})
@Builder
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // 이메일

    @Column(length = 50, nullable = false)
    private String name; // 이름

    private String password; // 비밀번호

    @Column(length = 100)
    private String nickname;

    private String mobile;

    @Column(columnDefinition = "Text")
    private String description;

    private String imageUrl; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE, APPLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    @JsonIgnore
    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private boolean smsAgree;

    @Column(nullable = false)
    private boolean emailAgree;

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
