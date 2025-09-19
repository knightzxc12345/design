package com.design.utils;

import com.amazonaws.util.IOUtils;
import com.design.handler.BusinessException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ItextUtil {

    private static Resource font;

    public static void init(Resource fontResource){
        font = fontResource;
    }

    public static FileInfo addTextsAndImage(
            String title,
            InputStream fileInputStream,
            List<PDFText> pdfTexts,
            List<PDFImage> pdfImages,
            String contentType) {
        InputStream fontInputStream = null;
        try {
            fontInputStream = font.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(fileInputStream), new PdfWriter(outputStream));
            PdfDocumentInfo documentInfo = pdfDocument.getDocumentInfo();
            documentInfo.setTitle(title);
            Document document = new Document(pdfDocument);
            PdfFont font = PdfFontFactory.createFont(IOUtils.toByteArray(fontInputStream), PdfEncodings.IDENTITY_H);
            document = setTexts(document, font, pdfTexts);
            document = setImages(document, pdfImages);
            document.close();
            pdfDocument.close();
            byte[] bytes = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return new FileInfo(
                    inputStream,
                    bytes.length,
                    contentType
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BusinessException(ITextEnum.I00001);
        } finally {
            try{
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fontInputStream != null) {
                    fontInputStream.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static FileInfo getPage(
            InputStream fileInputStream,
            String contentType,
            int page) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(fileInputStream));
            PdfDocument pagePdfDocument = new PdfDocument(new PdfWriter(outputStream));
            pdfDocument.copyPagesTo(page, page, pagePdfDocument);
            pagePdfDocument.close();
            pdfDocument.close();
            byte[] bytes = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return new FileInfo(
                    inputStream,
                    bytes.length,
                    contentType
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BusinessException(ITextEnum.I00001);
        } finally {
            try{
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    // 加入文字清單
    private static Document setTexts(Document document, PdfFont font, List<PDFText> pdfTexts){
        if(null == pdfTexts || pdfTexts.isEmpty()){
            return document;
        }
        Paragraph paragraph;
        DeviceRgb rgb;
        Canvas canvas;
        Rectangle rectangle;
        for(PDFText pdfText : pdfTexts){
            rgb = setRgb(pdfText.getRgb());
            paragraph = new Paragraph(pdfText.getText())
                    .setFont(font)
                    .setFontSize(pdfText.getSize())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(Color.convertRgbToCmyk(rgb));
            rectangle = new Rectangle(pdfText.getXRay(), pdfText.getYRay(), pdfText.getWidth(), pdfText.getHeight());
            canvas = new Canvas(new PdfCanvas(document.getPdfDocument().getPage(pdfText.getPage())), rectangle);
            canvas.add(paragraph);
            canvas.close();
        }
        return document;
    }

    // 設定顏色
    private static DeviceRgb setRgb(float [] rgb){
        // 預設藍色
        if(null == rgb){
            return new DeviceRgb(0f, 0f, 254f);
        }
        DeviceRgb deviceRgb = new DeviceRgb();
        deviceRgb.setColorValue(rgb);
        return deviceRgb;
    }

    // 加入圖片
    private static Document setImages(Document document, List<PDFImage> pdfImages){
        if(null == pdfImages || pdfImages.isEmpty()){
            return document;
        }
        PDFImage.ImageDetail imageDetail;
        byte [] imageBytes;
        Image image;
        for(PDFImage pdfImage : pdfImages){
            imageDetail = pdfImage.getImageDetail();
            imageBytes = inputStreamToByteArray(pdfImage.getInputStream());
            if(null == imageDetail){
                continue;
            }
            image = setImage(imageDetail, imageBytes);
            document.add(image);
        }
        return document;
    }

    private static Image setImage(PDFImage.ImageDetail imageDetail, byte [] bytes){
        return initImage(
                imageDetail.getXRay(),
                imageDetail.getYRay(),
                imageDetail.getWidth(),
                imageDetail.getPage(),
                bytes
        );
    }

    private static Image initImage(
            float xRay,
            float yRay,
            float width,
            int page,
            byte [] bytes){
        Image image = new Image(ImageDataFactory.create(bytes));
        image.setFixedPosition(xRay, yRay);
        float aspectRatio = image.getImageWidth() / image.getImageHeight();
        float height = width / aspectRatio;
        image.scaleToFit(width, height);
        image.setPageNumber(page);
        return image;
    }

    private static byte[] inputStreamToByteArray(InputStream inputStream){
        ByteArrayOutputStream outputStream;
        try {
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
        }catch (IOException ex){
            ex.printStackTrace();
            throw new BusinessException(ITextEnum.I00002);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException(ITextEnum.I00002);
                }
            }
        }
        return outputStream.toByteArray();
    }

}