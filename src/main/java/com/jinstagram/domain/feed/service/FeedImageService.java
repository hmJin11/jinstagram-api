package com.jinstagram.domain.feed.service;

import com.jinstagram.domain.feed.entity.Feed;
import com.jinstagram.domain.feed.entity.FeedImage;
import com.jinstagram.domain.feed.repository.FeedImageRepository;
import com.jinstagram.domain.member.entity.Member;
import com.jinstagram.domain.member.service.MemberService;
import com.jinstagram.global.common.dto.FileRequest;
import com.jinstagram.global.common.service.FileService;
import com.jinstagram.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedImageService {
    private final FeedImageRepository feedImageRepository;
    private final FileService fileService;
    private final MemberService memberService;
    public void uploadImage(Feed feed, MultipartFile[] file) throws Exception{
        Member member = memberService.findByEmail(SecurityUtil.getCurrentEmail());
        feedImageRepository.findByFeedId(feed.getId()).forEach(i -> i.deleteImage());

        for (MultipartFile multipartFile : file) {
            FileRequest fileRequest = fileService.uploadFile(multipartFile, String.format("/feed/%s/%s/", member.getId(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            FeedImage feedImage = FeedImage.builder()
                    .feed(feed)
                    .filePath(fileRequest.getPath())
                    .fileKey(fileRequest.getFileKey())
                    .fileSize(fileRequest.getFileSize())
                    .fileName(fileRequest.getFileName())
                    .origizalFileName(fileRequest.getOriginalFileName())
                    .build();
            feedImageRepository.save(feedImage);
        }
    }

    public List<FeedImage> getFeedImages(Long feedId){
        List<FeedImage> feedImages= feedImageRepository.findByFeedId(feedId);
        return feedImages;
    }
    public FeedImage getFeedImageFileInfo(String fileKey){
        return feedImageRepository.findByFileKey(fileKey);
    }


}
