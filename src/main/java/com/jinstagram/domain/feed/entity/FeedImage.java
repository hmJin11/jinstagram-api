package com.jinstagram.domain.feed.entity;

import com.jinstagram.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FeedImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId")
    private Feed feed;

    private String fileKey;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String origizalFileName;
    private Boolean deleted;
    public void deleteImage(){
        this.deleted = true;
    }

}
