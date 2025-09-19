package com.design.utils;

import com.amazonaws.services.s3.model.S3Object;
import com.design.handler.BusinessException;
import com.design.base.common.Common;
import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractUtil {

    private static final String[] CHINESE_NUMBERS = {"零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖"};

    public static List<PDFText> getTexts(
            ContractEntity contractEntity,
            ContractDetailEntity contractDetailEntity,
            List<ContractEquipmentEntity> contractEquipmentEntities,
            ContractReviewProcessEntity businessContractReviewProcessEntity,
            ContractReviewProcessEntity managerContractReviewProcessEntity,
            ContractReviewProcessEntity topManagerContractReviewProcessEntity,
            Address city,
            Address town,
            MemberEntity memberEntity){
        List<PDFText> pdfTexts = new ArrayList<>();
        // 設定合約編號
        pdfTexts = setContractNo(pdfTexts, contractEntity.getNo());
        // 設定訂購日期
        pdfTexts = setCreateTime(pdfTexts, contractEntity.getCreateTime());
        // 設定買方
        pdfTexts = setBuyer(pdfTexts, contractEntity.getMemberName());
        // 設定車型名稱
        pdfTexts = setModelCode(pdfTexts, contractDetailEntity.getModelCode());
        // 設定驅動形式
        pdfTexts = setDriverDesc(pdfTexts, contractEntity.getDriverDesc());
        // 設定馬力
        pdfTexts = setHpCode(pdfTexts, contractDetailEntity.getHpCode());
        // 設定後懸吊
        pdfTexts = setSuspensionDesc(pdfTexts, contractDetailEntity.getSuspensionDesc());
        // 設定齒輪代碼
        pdfTexts = setRatCode(pdfTexts, contractDetailEntity.getRatCode());
        // 設定變速箱
        pdfTexts = setGearDesc(pdfTexts, contractDetailEntity.getGearBox());
        // 設定車身顏色
        pdfTexts = setVehicleColorId(pdfTexts, contractDetailEntity.getVehicleColorId());
        // 設定軸距
        pdfTexts = setWheelBase(pdfTexts, contractDetailEntity.getWheelBase());
        // 設定輪胎數
        pdfTexts = setWheelCount(pdfTexts, contractDetailEntity.getWheelCount());
        // 設定原廠配備
        pdfTexts = setOriginContractEquipments(pdfTexts, contractEquipmentEntities);
        // 設定本地配備
        pdfTexts = setLocalContractEquipments(pdfTexts, contractEquipmentEntities);
        // 設定車輛保養
        pdfTexts = setMaintain(pdfTexts, contractDetailEntity.getMaintain(), contractDetailEntity.getSubMaintain());
        // 設定其他約定事項
        pdfTexts = setRemain(pdfTexts, businessContractReviewProcessEntity, managerContractReviewProcessEntity, topManagerContractReviewProcessEntity);
        // 設定訂購數量
        pdfTexts = setQuantity(pdfTexts, contractDetailEntity.getQuantity());
        // 設定車款單價
        pdfTexts = setUnitPrice(pdfTexts, contractDetailEntity.getSpecialUnitPrice());
        // 設定車款總計
        pdfTexts = setChineseUnitPrice(pdfTexts, contractDetailEntity.getQuantity(), contractDetailEntity.getSpecialUnitPrice());
        // 設定已付訂金
        pdfTexts = setDeposit(pdfTexts, contractDetailEntity.getDeposit());
        // 設定繳款方式
        pdfTexts = setPaymentType(pdfTexts, contractDetailEntity.getPaymentType());
        // 設定繳交日期
        pdfTexts = setPaymentTime(pdfTexts, contractDetailEntity.getPaymentTime());
        // 設定尾款
        pdfTexts = setAmount(pdfTexts, contractDetailEntity.getAmount(), contractDetailEntity.getDeposit());
        // 設定加值營業稅
        pdfTexts = setTax(pdfTexts, contractDetailEntity.getQuantity(), contractDetailEntity.getSpecialUnitPrice());
        // 設定預定交車年月
        pdfTexts = setDeliveryTime(pdfTexts, contractDetailEntity.getDeliveryTime());
        // 設定買方
        pdfTexts = setBuyerName(pdfTexts, contractEntity.getMemberName());
        // 設定法定代理人
        pdfTexts = setLegalGuardianName(pdfTexts, memberEntity);
        // 設定訂購代理人
        pdfTexts = setPurchasingAgentName(pdfTexts, contractEntity.getContactName());
        // 設定地址
        pdfTexts = setAddress(pdfTexts, city, town, memberEntity);
        return pdfTexts;
    }

    // 取得簽名
    public static List<PDFImage> getSignatureImages(
            S3Object businessS3Object,
            S3Object managerS3Object){
        List<PDFImage> pdfImages = new ArrayList<>();
        // 設定業務簽名
        PDFImage pdfImage = new PDFImage();
        PDFImage.ImageDetail imageDetail = setContractBusinessSignature();
        pdfImage.setInputStream(businessS3Object.getObjectContent());
        pdfImage.setImageDetail(imageDetail);
        pdfImages.add(pdfImage);
        // 設定業務主管簽名
        pdfImage = new PDFImage();
        imageDetail = setContractManagerSignature();
        pdfImage.setInputStream(managerS3Object.getObjectContent());
        pdfImage.setImageDetail(imageDetail);
        pdfImages.add(pdfImage);
        return pdfImages;
    }

    public static void write(InputStream inputStream, String path){
        try{
            int index;
            byte [] bytes = new byte[1024];
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            while ((index = inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, index);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // 設定合約編號
    private static List<PDFText> setContractNo(
            List<PDFText> pdfTexts,
            String contractNo){
        if(StringUtils.isBlank(contractNo)){
            throw new BusinessException(ContractEnum.C00001);
        }
        pdfTexts.add(new PDFText(
                contractNo,
                440f,
                773f,
                150,
                16,
                12f,
                new float[]{255, 0, 0}
        ));
        return pdfTexts;
    }

    // 設定合約編號
    private static List<PDFText> setCreateTime(
            List<PDFText> pdfTexts,
            Instant createTime){
        if(null == createTime){
            throw new BusinessException(ContractEnum.C00001);
        }
        LocalDate date = createTime.atZone(Common.zoneId).toLocalDate();
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue());
        String day = String.format("%02d", date.getDayOfMonth());
        pdfTexts.add(new PDFText(
                year,
                440f,
                749f,
                100,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                month,
                482f,
                749f,
                100,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                day,
                513f,
                749f,
                100,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定買方
    private static List<PDFText> setBuyer(
            List<PDFText> pdfTexts,
            String memberName){
        if(StringUtils.isBlank(memberName)){
            throw new BusinessException(ContractEnum.C00003);
        }
        pdfTexts.add(new PDFText(
                memberName,
                78f,
                732f,
                200,
                16,
                12f
        ));
        return pdfTexts;
    }

    // 設定車型名稱
    private static List<PDFText> setModelCode(
            List<PDFText> pdfTexts,
            String modelCode){
        if(StringUtils.isBlank(modelCode)){
            throw new BusinessException(ContractEnum.C00004);
        }
        pdfTexts.add(new PDFText(
                modelCode,
                96f,
                669f,
                200,
                16,
                9f
        ));
        return pdfTexts;
    }

    // 設定驅動形式
    private static List<PDFText> setDriverDesc(
            List<PDFText> pdfTexts,
            String driverDesc){
        if(StringUtils.isBlank(driverDesc)){
            throw new BusinessException(ContractEnum.C00005);
        }
        pdfTexts.add(new PDFText(
                driverDesc,
                267f,
                669f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定馬力
    private static List<PDFText> setHpCode(
            List<PDFText> pdfTexts,
            String hpCode){
        if(StringUtils.isBlank(hpCode)){
            throw new BusinessException(ContractEnum.C00006);
        }
        pdfTexts.add(new PDFText(
                hpCode,
                415f,
                669f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定後懸吊
    private static List<PDFText> setSuspensionDesc(
            List<PDFText> pdfTexts,
            String suspensionDesc){
        if(StringUtils.isBlank(suspensionDesc)){
            throw new BusinessException(ContractEnum.C00007);
        }
        pdfTexts.add(new PDFText(
                suspensionDesc,
                85f,
                646f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定差速比
    private static List<PDFText> setRatCode(
            List<PDFText> pdfTexts,
            String ratCode){
        if(StringUtils.isBlank(ratCode)){
            throw new BusinessException(ContractEnum.C00008);
        }
        pdfTexts.add(new PDFText(
                ratCode,
                255f,
                646f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定變速箱
    private static List<PDFText> setGearDesc(
            List<PDFText> pdfTexts,
            String gearBoxDesc){
        if(StringUtils.isBlank(gearBoxDesc)){
            throw new BusinessException(ContractEnum.C00029);
        }
        pdfTexts.add(new PDFText(
                gearBoxDesc,
                425f,
                646f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定車身顏色
    private static List<PDFText> setVehicleColorId(
            List<PDFText> pdfTexts,
            String vehicleColorId){
        if(StringUtils.isBlank(vehicleColorId)){
            throw new BusinessException(ContractEnum.C00010);
        }
        pdfTexts.add(new PDFText(
                vehicleColorId,
                95f,
                622f,
                200,
                16,
                8f
        ));
        return pdfTexts;
    }

    // 設定軸距
    private static List<PDFText> setWheelBase(
            List<PDFText> pdfTexts,
            String wheelBase){
        if(StringUtils.isBlank(wheelBase)){
            throw new BusinessException(ContractEnum.C00009);
        }
        pdfTexts.add(new PDFText(
                wheelBase,
                243f,
                623f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定輪胎數
    private static List<PDFText> setWheelCount(
            List<PDFText> pdfTexts,
            Integer wheelCount){
        if(null == wheelCount){
            throw new BusinessException(ContractEnum.C00030);
        }
        pdfTexts.add(new PDFText(
                wheelCount.toString(),
                425f,
                623f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定原廠配備清單
    private static List<PDFText> setOriginContractEquipments(
            List<PDFText> pdfTexts,
            List<ContractEquipmentEntity> contractEquipmentEntities) {
        if(null == contractEquipmentEntities || contractEquipmentEntities.isEmpty()){
            return pdfTexts;
        }
        List<ContractEquipmentEntity> originContractEquipmentEntities = new ArrayList<>();
        for(ContractEquipmentEntity contractEquipmentEntity : contractEquipmentEntities){
            if(SourceType.ORIGIN.equals(contractEquipmentEntity.getSourceType())){
                originContractEquipmentEntities.add(contractEquipmentEntity);
            }
        }
        if(originContractEquipmentEntities.isEmpty()){
            return pdfTexts;
        }
        ContractEquipmentEntity contractEquipmentEntity;
        for(int i = 0; i < originContractEquipmentEntities.size(); i ++){
            contractEquipmentEntity = originContractEquipmentEntities.get(i);
            pdfTexts = setOriginContractEquipment(pdfTexts, i, contractEquipmentEntity.getName(), contractEquipmentEntity.getQuantity());
        }
        return pdfTexts;
    }

    // 設定原廠配備
    private static List<PDFText> setOriginContractEquipment(
            List<PDFText> pdfTexts,
            Integer index,
            String contractEquipmentName,
            Integer quantity){
        contractEquipmentName = String.format("%s.%s%s%s", index + 1, contractEquipmentName, " x ", quantity);
        if(0 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    586f,
                    200,
                    16,
                    10f
            ));
        }
        if(1 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    571f,
                    200,
                    16,
                    10f
            ));
        }
        if(2 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    556f,
                    200,
                    16,
                    10f
            ));
        }
        if(3 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    541f,
                    200,
                    16,
                    10f
            ));
        }
        if(4 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    526f,
                    200,
                    16,
                    10f
            ));
        }
        if(5 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    586f,
                    200,
                    16,
                    10f
            ));
        }
        if(6 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    571f,
                    200,
                    16,
                    10f
            ));
        }
        if(7 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    556f,
                    200,
                    16,
                    10f
            ));
        }
        if(8 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    541f,
                    200,
                    16,
                    10f
            ));
        }
        if(9 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    526f,
                    200,
                    16,
                    10f
            ));
        }
        if(10 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    586f,
                    200,
                    16,
                    10f
            ));
        }
        if(11 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    571f,
                    200,
                    16,
                    10f
            ));
        }
        if(12 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    556f,
                    200,
                    16,
                    10f
            ));
        }
        if(13 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    541f,
                    200,
                    16,
                    10f
            ));
        }
        if(14 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    526f,
                    200,
                    16,
                    10f
            ));
        }
        return pdfTexts;
    }

    // 設定本地配備清單
    private static List<PDFText> setLocalContractEquipments(
            List<PDFText> pdfTexts,
            List<ContractEquipmentEntity> contractEquipmentEntities) {
        if(null == contractEquipmentEntities || contractEquipmentEntities.isEmpty()){
            return pdfTexts;
        }
        List<ContractEquipmentEntity> localContractEquipmentEntities = new ArrayList<>();
        for(ContractEquipmentEntity contractEquipmentEntity : contractEquipmentEntities){
            if(SourceType.LOCAL.equals(contractEquipmentEntity.getSourceType())){
                localContractEquipmentEntities.add(contractEquipmentEntity);
            }
        }
        if(localContractEquipmentEntities.isEmpty()){
            return pdfTexts;
        }
        ContractEquipmentEntity contractEquipmentEntity;
        for(int i = 0; i < localContractEquipmentEntities.size(); i ++){
            contractEquipmentEntity = localContractEquipmentEntities.get(i);
            pdfTexts = setLocalContractEquipment(pdfTexts, i, contractEquipmentEntity.getName(), contractEquipmentEntity.getQuantity());
        }
        return pdfTexts;
    }

    // 設定本地配備
    private static List<PDFText> setLocalContractEquipment(
            List<PDFText> pdfTexts,
            Integer index,
            String contractEquipmentName,
            Integer quantity){
        contractEquipmentName = String.format("%s.%s%s%s", index + 1, contractEquipmentName, " x ", quantity);
        if(0 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    496f,
                    200,
                    16,
                    10f
            ));
        }
        if(1 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    482f,
                    200,
                    16,
                    10f
            ));
        }
        if(2 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    468f,
                    200,
                    16,
                    10f
            ));
        }
        if(3 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    454f,
                    200,
                    16,
                    10f
            ));
        }
        if(4 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    40f,
                    440f,
                    200,
                    16,
                    10f
            ));
        }
        if(5 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    496f,
                    200,
                    16,
                    10f
            ));
        }
        if(6 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    482f,
                    200,
                    16,
                    10f
            ));
        }
        if(7 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    468f,
                    200,
                    16,
                    10f
            ));
        }
        if(8 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    454f,
                    200,
                    16,
                    10f
            ));
        }
        if(9 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    210f,
                    440f,
                    200,
                    16,
                    10f
            ));
        }
        if(10 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    496f,
                    200,
                    16,
                    10f
            ));
        }
        if(11 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    482f,
                    200,
                    16,
                    10f
            ));
        }
        if(12 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    468f,
                    200,
                    16,
                    10f
            ));
        }
        if(13 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    454f,
                    200,
                    16,
                    10f
            ));
        }
        if(14 == index){
            pdfTexts.add(new PDFText(
                    contractEquipmentName,
                    382f,
                    440f,
                    200,
                    16,
                    10f
            ));
        }
        return pdfTexts;
    }

    // 設定車輛保養
    private static List<PDFText> setMaintain(
            List<PDFText> pdfTexts,
            Integer maintain,
            Integer subMaintain) {
        if(null == maintain){
            throw new BusinessException(ContractEnum.C00011);
        }
        if(null == subMaintain){
            throw new BusinessException(ContractEnum.C00012);
        }
        pdfTexts.add(new PDFText(
                String.valueOf(maintain),
                176f,
                421f,
                150,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                String.valueOf(subMaintain),
                222f,
                421f,
                150,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                String.valueOf(maintain + subMaintain),
                290f,
                421f,
                150,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定其他約定事項
    private static List<PDFText> setRemain(
            List<PDFText> pdfTexts,
            ContractReviewProcessEntity businessContractReviewProcessEntity,
            ContractReviewProcessEntity managerContractReviewProcessEntity,
            ContractReviewProcessEntity topManagerContractReviewProcessEntity) {
        if(null != businessContractReviewProcessEntity){
            pdfTexts.add(new PDFText(
                    String.format("%s%s", "其他約定事項：", businessContractReviewProcessEntity.getRemark()),
                    40f,
                    371,
                    500,
                    50,
                    8f
            ));
        }
        if(null != managerContractReviewProcessEntity){
            pdfTexts.add(new PDFText(
                    String.format("%s%s", "業務主管：", managerContractReviewProcessEntity.getRemark()),
                    40f,
                    366,
                    500,
                    16,
                    8f
            ));
        }
//        if(null != topManagerContractReviewProcessEntity){
//            pdfTexts.add(new PDFText(
//                    String.format("%s%s", "最高主管:", topManagerContractReviewProcessEntity.getRemark()),
//                    120f,
//                    370,
//                    500,
//                    16,
//                    10f
//            ));
//        }
        return pdfTexts;
    }

    // 設定訂購數量
    private static List<PDFText> setQuantity(
            List<PDFText> pdfTexts,
            Integer quantity) {
        if(null == quantity){
            throw new BusinessException(ContractEnum.C00013);
        }
        pdfTexts.add(new PDFText(
                String.valueOf(quantity),
                118f,
                351f,
                150,
                16,
                12f
        ));
        return pdfTexts;
    }

    // 設定車款單價
    private static List<PDFText> setUnitPrice(
            List<PDFText> pdfTexts,
            BigDecimal specialUnitPrice) {
        if(null == specialUnitPrice){
            throw new BusinessException(ContractEnum.C00014);
        }
        pdfTexts.add(new PDFText(
                String.format("%,d", specialUnitPrice.intValue()),
                327f,
                350f,
                150,
                16,
                12f
        ));
        return pdfTexts;
    }

    // 設定車款總計
    private static List<PDFText> setChineseUnitPrice(
            List<PDFText> pdfTexts,
            Integer quantity,
            BigDecimal specialUnitPrice) {
        BigDecimal number = specialUnitPrice.multiply(new BigDecimal(quantity));
        // 元
        int value = number.intValue() % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                475f,
                326f,
                100,
                16,
                10f
        ));
        // 拾
        value = (number.intValue() / 10) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                423f,
                326f,
                100,
                16,
                10f
        ));
        // 百
        value = (number.intValue() / 100) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                391f,
                326f,
                100,
                16,
                10f
        ));
        // 千
        value = (number.intValue() / 1000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                359f,
                326f,
                100,
                16,
                10f
        ));
        // 萬
        value = (number.intValue() / 10000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                324f,
                326f,
                100,
                16,
                10f
        ));
        // 拾萬
        value = (number.intValue() / 100000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                281f,
                326f,
                100,
                16,
                10f
        ));
        // 佰萬
        value = (number.intValue() / 1000000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                237f,
                326f,
                100,
                16,
                10f
        ));
        // 仟萬
        value = (number.intValue() / 10000000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                193f,
                326f,
                100,
                16,
                10f
        ));
        // 億
        value = (number.intValue() / 100000000) % 10;
        pdfTexts.add(new PDFText(
                CHINESE_NUMBERS[value],
                158f,
                326f,
                100,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定已付訂金
    private static List<PDFText> setDeposit(
            List<PDFText> pdfTexts,
            BigDecimal deposit) {
        if(null == deposit){
            throw new BusinessException(ContractEnum.C00015);
        }
        pdfTexts.add(new PDFText(
                String.format("%,d", deposit.intValue()),
                158f,
                284f,
                150,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定繳款方式
    private static List<PDFText> setPaymentType(
            List<PDFText> pdfTexts,
            PaymentType paymentType) {
        if(null == paymentType){
            throw new BusinessException(ContractEnum.C00016);
        }
        pdfTexts.add(new PDFText(
                paymentType.getName(),
                323f,
                283f,
                150,
                16,
                8f
        ));
        return pdfTexts;
    }

    // 設定繳交日期
    private static List<PDFText> setPaymentTime(
            List<PDFText> pdfTexts,
            Instant paymentTime) {
//        if(null == paymentTime){
//            throw new BusinessException(ContractEnum.C00017);
//        }
        if(null == paymentTime){
            return pdfTexts;
        }
        LocalDate date = paymentTime.atZone(Common.zoneId).toLocalDate();
        String year = String.valueOf(date.getYear());
        String month = String.format("%02d", date.getMonthValue());
        String day = String.format("%02d", date.getDayOfMonth());
        pdfTexts.add(new PDFText(
                year,
                458f,
                284f,
                100,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                month,
                493f,
                284f,
                100,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                day,
                520f,
                284f,
                100,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定尾款
    private static List<PDFText> setAmount(
            List<PDFText> pdfTexts,
            BigDecimal amount,
            BigDecimal deposit) {
        if(null == amount){
            throw new BusinessException(ContractEnum.C00018);
        }
        amount = amount.subtract(deposit);
        pdfTexts.add(new PDFText(
                String.format("%,d", amount.intValue()),
                167f,
                224f,
                300,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定加值營業稅
    private static List<PDFText> setTax(
            List<PDFText> pdfTexts,
            Integer quantity,
            BigDecimal specialUnitPrice) {
        BigDecimal taxTotal = specialUnitPrice;
        taxTotal = taxTotal.multiply(new BigDecimal(quantity));
        taxTotal = taxTotal.multiply(new BigDecimal("0.05"));
        taxTotal = taxTotal.setScale(0, RoundingMode.HALF_UP);
        pdfTexts.add(new PDFText(
                String.format("%,d", taxTotal.intValue()),
                167f,
                204f,
                300,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定預定交車年月
    private static List<PDFText> setDeliveryTime(
            List<PDFText> pdfTexts,
            String deliveryTime) {
        if(StringUtils.isBlank(deliveryTime)){
            return pdfTexts;
        }
        String [] time = deliveryTime.split("-");
        String year = time[0];
        String month = time[1];
        pdfTexts.add(new PDFText(
                year,
                140f,
                185f,
                150,
                16,
                10f
        ));
        pdfTexts.add(new PDFText(
                month,
                230f,
                185f,
                150,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定買方
    private static List<PDFText> setBuyerName(
            List<PDFText> pdfTexts,
            String memberName) {
        if(StringUtils.isBlank(memberName)){
            throw new BusinessException(ContractEnum.C00020);
        }
        pdfTexts.add(new PDFText(
                memberName,
                70f,
                154f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定法定代理人
    private static List<PDFText> setLegalGuardianName(
            List<PDFText> pdfTexts,
            MemberEntity memberEntity) {
        String name = null;
        if(!MemberType.PERSON.equals(memberEntity.getType())){
            name = memberEntity.getPrincipal();
        }
        if(MemberType.PERSON.equals(memberEntity.getType())){
            name = memberEntity.getName();
        }
        if(StringUtils.isBlank(name)){
            throw new BusinessException(ContractEnum.C00021);
        }
        pdfTexts.add(new PDFText(
                name,
                110f,
                133f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定訂購代理人
    private static List<PDFText> setPurchasingAgentName(
            List<PDFText> pdfTexts,
            String contractName) {
        if(StringUtils.isBlank(contractName)){
            throw new BusinessException(ContractEnum.C00022);
        }
        pdfTexts.add(new PDFText(
                contractName,
                110f,
                112f,
                200,
                16,
                10f
        ));
        return pdfTexts;
    }

    // 設定地址
    private static List<PDFText> setAddress(
            List<PDFText> pdfTexts,
            Address city,
            Address town,
            MemberEntity memberEntity) {
        if(StringUtils.isBlank(memberEntity.getAddress())){
            throw new BusinessException(ContractEnum.C00023);
        }
        String address = String.format("%s%s%s", city.getName(), town.getName(), memberEntity.getAddress());
        pdfTexts.add(new PDFText(
                address,
                110f,
                92f,
                200,
                16,
                8f
        ));
        return pdfTexts;
    }

    // 設定合約業務簽名
    private static PDFImage.ImageDetail setContractBusinessSignature(){
        return new PDFImage.ImageDetail(
                380f,
                92f,
                150
        );
    }

    // 設定合約業務簽名
    private static PDFImage.ImageDetail setContractManagerSignature(){
        return new PDFImage.ImageDetail(
                380f,
                45f,
                150
        );
    }

}
