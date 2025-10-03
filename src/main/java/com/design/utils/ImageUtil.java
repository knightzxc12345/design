package com.design.utils;

import com.design.base.api.UtilCode;
import com.design.base.common.Common;
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
     * @param file 前端使用的圖片檔案
     */
    public static String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dirPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return Common.IMAGE_PATH + filename;
        } catch (IOException e) {
            throw new BusinessException(UtilCode.IMAGE_ERROR);
        }
    }

    /**
     * 刪除圖片
     * @param imageUrl 前端使用的圖片 URL，例如 /images/xxxx.png
     * @return 刪除成功返回 true，檔案不存在返回 false
     */
    public static boolean deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }
        try {
            String filename = imageUrl.replace(Common.IMAGE_PATH, "");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new BusinessException(UtilCode.IMAGE_ERROR);
        }
    }

}
