package com.jinstagram.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
}
