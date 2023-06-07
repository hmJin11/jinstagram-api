package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedLikeRepository extends JpaRepository<FeedLike, Long>{
    Boolean existsByFeedIdAndMemberId(Long feedId, Long memberId);
    FeedLike findAllByFeedIdAndMemberId(Long feedId, Long memberId);
}
