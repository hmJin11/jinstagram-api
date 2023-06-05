package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
