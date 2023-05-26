package com.jinstagram.domain.follow.entity;

import com.jinstagram.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "follow",
        uniqueConstraints = {
                @UniqueConstraint(name = "follow_uk", columnNames = {"fromMemberId", "toMemberId"})})
public class Follow {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fromMemberId")
    private Member fromMember;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="toMemberId")
    private Member toMember;

    @Builder
    public Follow(Member fromMember, Member toMember){
        validateFollow(fromMember,toMember);
        this.fromMember = fromMember;
        this.toMember = toMember;
    }

    private void validateFollow (Member fromMember, Member toMember){
        if (fromMember.getId() == toMember.getId()) throw new IllegalArgumentException("자기 자신은 팔로우 할 수 없습니다.");
    }
}
