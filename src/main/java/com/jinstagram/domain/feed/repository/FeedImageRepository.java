package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {

    List<FeedImage> findByFeedId(Long FeedId);
    FeedImage findByFileKey(String fileKey);
}
