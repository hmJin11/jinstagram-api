package com.jinstagram.domain.member.dto;

import com.jinstagram.global.common.CommonSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSearch extends CommonSearch {
    private Long id;
}
