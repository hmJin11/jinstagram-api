package com.jinstagram.domain.feed.dto.response;

import com.jinstagram.global.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedListResponse {

    private List<FeedResponse> feeds;
    private PageInfo pageInfo;
}
