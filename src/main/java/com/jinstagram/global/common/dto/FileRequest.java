package com.jinstagram.global.common.dto;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileRequest {
    private String fileName;
    private String originalFileName;
    private Long fileSize;
    private String fileKey;
    private String path;
}
