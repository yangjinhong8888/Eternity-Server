package com.jinhongs.eternity.admin.web.controller;

import com.jinhongs.eternity.admin.web.utils.ResultUtils;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.common.exception.ServerException;
import com.jinhongs.eternity.common.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
@Tag(name = "文件上传接口")
public class FileController {

    @Value("${eternity.upload.dir:./uploads}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L; // 5MB

    private static final java.util.Set<String> ALLOWED_TYPES = java.util.Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    @Operation(summary = "上传图片")
    @PostMapping("/upload")
    public ResponseEntity<Result<Map<String, String>>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ClientException("上传文件不能为空");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new ClientException("仅支持 JPG、PNG、GIF、WEBP 格式图片");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ClientException("图片大小不能超过 5MB");
        }

        try {
            // 按日期分目录存储
            String datePath = java.time.LocalDate.now().toString().replace("-", "/");
            Path dirPath = Paths.get(uploadDir, "images", datePath);
            Files.createDirectories(dirPath);

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";
            String filename = UUID.randomUUID().toString().replace("-", "") + suffix;

            Path filePath = dirPath.resolve(filename);
            Files.write(filePath, file.getBytes());

            String url = "/uploads/images/" + datePath + "/" + filename;
            log.info("图片上传成功：{}", url);
            return ResultUtils.ok(Map.of("url", url));

        } catch (IOException e) {
            log.error("图片上传失败", e);
            throw new ServerException("图片上传失败，请重试");
        }
    }
}
