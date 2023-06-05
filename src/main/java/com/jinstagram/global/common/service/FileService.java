package com.jinstagram.global.common.service;

import com.jinstagram.global.common.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${custom.upload.folder}")
    String DEFAULT_PATH;
    public FileRequest uploadFile(MultipartFile multipartFile, String path) throws Exception {
        return uploadFile(multipartFile, path, "");
    }

    public FileRequest uploadFile(MultipartFile multipartFile, String path, String refName) throws Exception {
        File uploadPath = new File(DEFAULT_PATH, path);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = multipartFile.getOriginalFilename();
        String resultFileName = createStoreFileName(originalFilename, uuid);
        Path pathFile = Paths.get(uploadPath.getPath(), resultFileName);
        multipartFile.transferTo(pathFile);

        Map<String, String> map = new HashMap<>();
        map.put("fileName", resultFileName);
        map.put("pathFile", String.valueOf(pathFile));
        map.put("originalFileName", originalFilename);
        map.put("fileSize", multipartFile.getSize() + "");
        map.put("fileKey", uuid);
        map.put("fileRef", refName);
        FileRequest fileRequest = FileRequest.builder()
                .fileName(resultFileName)
                .originalFileName(originalFilename)
                .fileSize(multipartFile.getSize())
                .fileKey(uuid)
                .path(String.valueOf(pathFile))
                .build();
        return fileRequest;
    }

    private String createStoreFileName(String originalFilename, String uuid) {
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
