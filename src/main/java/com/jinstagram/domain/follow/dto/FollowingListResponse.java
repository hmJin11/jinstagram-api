package com.jinstagram.domain.follow.dto;

import com.jinstagram.global.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowingListResponse {
    private List<FollowResponse> followings;
    private PageInfo pageInfo;
}
