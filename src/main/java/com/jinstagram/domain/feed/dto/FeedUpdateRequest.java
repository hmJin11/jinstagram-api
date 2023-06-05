package com.jinstagram.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedUpdateRequest {
    private Long id;
    private String contents;
    private MultipartFile[] imageFile;
}
