package com.jinstagram.domain.feed.repository;

import com.jinstagram.domain.feed.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    List<FeedComment> findByFeedIdAndParentFeedCommentIsNull(Long feedId);
    List<FeedComment> findByParentFeedCommentId(Long parentFeedCommetId);
    Long countByParentFeedCommentId(Long parentFeedCommentId);
}
