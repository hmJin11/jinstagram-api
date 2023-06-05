package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment,Long> {

}
