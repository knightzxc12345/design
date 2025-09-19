package com.design.utils;

import com.amazonaws.services.s3.model.S3Object;
import com.design.handler.BusinessException;
import com.design.base.common.Common;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> imageContentTypes;

    public static List<String> videoContentType;

    public static List<String> jsonContentType;

    public static List<String> pdfContentType;

    static{
        imageContentTypes = new ArrayList<>();
        imageContentTypes.add("image/jpeg");
        imageContentTypes.add("application/x-jpg");
        imageContentTypes.add("image/png");
        imageContentTypes.add("image/tiff");
        imageContentTypes.add("application/x-tif");
        imageContentTypes.add("text/xml");

        videoContentType = new ArrayList<>();
        videoContentType.add("video/mp4");

        jsonContentType = new ArrayList<>();
        jsonContentType.add("application/json");

        pdfContentType = new ArrayList<>();
        pdfContentType.add("application/pdf");
    }

    public static boolean checkImage(MultipartFile multipartFile) {
        if(null == multipartFile || multipartFile.isEmpty()){
            throw new BusinessException(FileEnum.F00006);
        }
        checkImageType(multipartFile);
        checkImageSize(multipartFile);
        return true;
    }

    public static boolean checkImage(S3Object s3Object) {
        if(null == s3Object){
            throw new BusinessException(FileEnum.F00006);
        }
        checkImageType(s3Object.getObjectMetadata().getContentType());
        checkImageSize(s3Object.getObjectMetadata().getContentLength());
        return true;
    }

    public static boolean checkImage(List<MultipartFile> multipartFiles) {
        if(null == multipartFiles || multipartFiles.isEmpty()){
            throw new BusinessException(FileEnum.F00006);
        }
        for(MultipartFile multipartFile : multipartFiles){
            checkImage(multipartFile);
        }
        return true;
    }

    public static boolean checkVideo(MultipartFile multipartFile) {
        if(null == multipartFile || multipartFile.isEmpty()){
            return false;
        }
        checkVideoType(multipartFile);
        checkVideoSize(multipartFile);
        return true;
    }

    public static boolean checkJson(MultipartFile multipartFile) {
        if(null == multipartFile || multipartFile.isEmpty()){
            return false;
        }
        checkJsonType(multipartFile);
        return true;
    }

    public static boolean checkPdf(MultipartFile multipartFile) {
        if(null == multipartFile || multipartFile.isEmpty()){
            throw new BusinessException(FileEnum.F00006);
        }
        checkPdfType(multipartFile);
        checkPdfSize(multipartFile);
        return true;
    }

    private static void checkImageType(String contentType) {
        if(imageContentTypes.contains(contentType)){
            return;
        }
        throw new BusinessException(FileEnum.F00001);
    }

    private static void checkImageType(MultipartFile multipartFile) {
        if(imageContentTypes.contains(multipartFile.getContentType())){
            return;
        }
        throw new BusinessException(FileEnum.F00001);
    }

    private static void checkVideoType(MultipartFile multipartFile) {
        if(videoContentType.contains(multipartFile.getContentType())){
            return;
        }
        throw new BusinessException(FileEnum.F00003);
    }

    private static void checkPdfType(MultipartFile multipartFile) {
        if(pdfContentType.contains(multipartFile.getContentType())){
            return;
        }
        throw new BusinessException(FileEnum.F00007);
    }

    private static void checkJsonType(MultipartFile multipartFile) {
        if(jsonContentType.contains(multipartFile.getContentType())){
            return;
        }
        throw new BusinessException(FileEnum.F00005);
    }

    private static void checkImageSize(MultipartFile multipartFile) {
        if(multipartFile.getSize() <= Common.IMAGE_SIZE){
            return;
        }
        throw new BusinessException(FileEnum.F00002);
    }

    private static void checkImageSize(long size) {
        if(size <= Common.IMAGE_SIZE){
            return;
        }
        throw new BusinessException(FileEnum.F00002);
    }

    private static void checkVideoSize(MultipartFile multipartFile) {
        if(multipartFile.getSize() <= Common.VIDEO_SIZE){
            return;
        }
        throw new BusinessException(FileEnum.F00004);
    }

    private static void checkPdfSize(MultipartFile multipartFile) {
        if(multipartFile.getSize() <= Common.PDF_SIZE){
            return;
        }
        throw new BusinessException(FileEnum.F00008);
    }

}