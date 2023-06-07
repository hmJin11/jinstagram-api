package com.jinstagram.domain.feed.dto;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRequest {
    private String contents;
    private MultipartFile[] imageFile;
    private String fileKey;
    private Member member;

    public Feed toEntity(){
        return Feed.builder()
                .contents(this.contents)
                .member(this.member)
                .deleted(false)
                .build();
    }
}
