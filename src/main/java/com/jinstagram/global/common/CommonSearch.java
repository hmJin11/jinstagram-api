package com.jinstagram.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonSearch {
    private int pageNum = 1;
    private int pageSize = 10;
    private String searchString;
}
