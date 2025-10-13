package com.design.utils;

import com.design.base.api.UtilCode;
import com.design.handler.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class ImageUtil {

    private static String UPLOAD_DIR;

    @Value("${file.upload-dir}")
    public void setUploadDir(String uploadDir) {
        ImageUtil.UPLOAD_DIR = uploadDir;
    }

    /**
     * 上傳圖片
     * @param subDir 子資料夾（例如 Common.IMAGE_USER_PATH）
     * @param file 上傳的圖片
     * @return 前端可用的圖片 URL（例如 /images/user/xxxx.png）
     */
    public static String uploadImage(String subDir, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dirPath = Paths.get(UPLOAD_DIR, subDir);
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return subDir + filename;
        } catch (IOException e) {
            throw new BusinessException(UtilCode.IMAGE_ERROR);
        }
    }

    /**
     * 刪除圖片
     * @param imageUrl 前端圖片 URL（例如 /images/user/xxxx.png）
     * @return 刪除成功 true；檔案不存在 false
     */
    public static boolean deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }
        try {
            String relativePath = imageUrl.replaceFirst("^/+", "");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(relativePath);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new BusinessException(UtilCode.IMAGE_ERROR);
        }
    }

}
