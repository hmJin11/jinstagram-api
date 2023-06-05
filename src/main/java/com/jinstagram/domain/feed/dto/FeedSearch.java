package com.jinstagram.domain.feed.dto;

import com.jinstagram.global.common.CommonSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedSearch extends CommonSearch {
    private Long id;
    private Long memberId;
}
