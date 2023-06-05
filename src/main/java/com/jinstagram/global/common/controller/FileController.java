package com.jinstagram.global.common.controller;

import com.jinstagram.domain.feed.entity.FeedImage;
import com.jinstagram.domain.feed.service.FeedImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class FileController {
    private final FeedImageService feedImageService;
    @GetMapping("/{type}/{fileKey}")
    public ResponseEntity<Resource> imageView(@PathVariable("type") String type, @PathVariable("fileKey") String fileKey) throws Exception {
        String filePath = null;
        String fileName = null;
        if (type.equals("feeds")){
            FeedImage feedImage = feedImageService.getFeedImageFileInfo(fileKey);
            filePath = feedImage.getFilePath();
            fileName = feedImage.getFileName();
        }

        if(filePath != null){
            UrlResource urlResource = new UrlResource("file:" + filePath);
            if(!urlResource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String resourceName = fileName;
            Tika tika = new Tika();
            String mediaType = tika.detect(urlResource.getFile());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + UriUtils.encode(resourceName, StandardCharsets.UTF_8) + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, mediaType)
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=" + (365 * 86400))
                    .body(urlResource);
        }
        return ResponseEntity.notFound().build();
    }

}
