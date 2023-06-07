package com.jinstagram.domain.follow.repository;

import com.jinstagram.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

/*    @Query("select f.id, m.nickname, m.imageUrl from Follow f left outer join Member m on f.toMember.id = m.id where f.fromMember.id = :fromMemberId order by m.id DESC ")
    List<FollowResponse> followings(@Param(value = "fromMemberId") Long fromMemberId);

    @Query("select f.id, m.id as member_id, m.nickname, m.imageUrl from Member m, Follow f where m.id = f.fromMember.id and f.toMember.id = :toMemberId order by m.id DESC ")
    List<FollowResponse> followers(@Param(value = "toMemberId") Long toMemberId);*/

    Boolean existsByFromMemberIdAndToMemberId(@Param(value = "fromMemberId") Long fromMemberId, @Param(value = "toMemberId") Long toMemberId);
    Follow findAllByFromMemberIdAndToMemberId(@Param(value = "fromMemberId") Long fromMemberId, @Param(value = "toMemberId") Long toMemberId);

    Long countAllByFromMemberId(@Param(value = "fromMemberId") Long fromMemberId);
    Long countAllByToMemberId(@Param(value = "toMemberId") Long toMemberId);
}
