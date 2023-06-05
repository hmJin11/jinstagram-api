package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.dto.FeedResponse;
import com.jinstagram.domain.feed.dto.FeedSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedRepositoryCustom {
    Page<FeedResponse> searchFeeds(FeedSearch search, Pageable pageable);

}


