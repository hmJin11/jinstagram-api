package com.jinstagram.domain.member.dto;

import com.jinstagram.global.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListResponse {
    private List<MemberResponse> members;
    private PageInfo pageInfo;
}
