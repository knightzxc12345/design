package com.design.utils;

import com.design.handler.BusinessException;
import com.design.base.common.Common;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ExcelUtil {

    public static <T> FileInfo create(InputStream inputStream, List<T> datas){
        try {
            if(null == inputStream){
                throw new BusinessException(ExcelEnum.E00002);
            }
            // 取得欄位
            Map<String, List<Object>> columnMaps = getColumnMaps(datas);
            // 初始化
            Workbook workbook = new XSSFWorkbook(inputStream);
            // 設定內容
            workbook = setWorkbook(workbook, columnMaps);
            // 產出內容
            InputStream targetInputStream = output(workbook);
            return new FileInfo(targetInputStream, targetInputStream.available(), Common.EXCEL_CONTENT_TYPE);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(ExcelEnum.E00001);
        }
    }

    // 取得所有欄位名稱
    private static <T> Map<String, List<Object>> getColumnMaps(List<T> datas){
        try{
            Map<String, List<Object>> columns = new HashMap<>();
            Field [] fields;
            String key;
            Object value;
            List<Object> values;
            for(T t : datas){
                fields = t.getClass().getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);
                    key = field.getName();
                    value = field.get(t);
                    values = columns.getOrDefault(key, new ArrayList<>());
                    values.add(value);
                    columns.put(key, values);
                }
            }
            return columns;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(ExcelEnum.E00003);
        }
    }

    // 設定內容
    private static Workbook setWorkbook(Workbook workbook, Map<String, List<Object>> columnMaps){
        try{
            Integer columnIndex;
            List<Object> values;
            Row dataRow;
            Cell dataCell;
            CellStyle dataCellStyle;
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(1);
            if (null == columnMaps || columnMaps.isEmpty()) {
                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    dataCell = headerRow.getCell(i);
                    if (null == dataCell) {
                        dataCell = headerRow.createCell(i);
                    }
                    dataCell.setCellStyle(null);
                    dataCell.setCellValue("");
                }
                return workbook;
            }
            for (String key : columnMaps.keySet()) {
                columnIndex = findColumnIndex(headerRow, key);
                if(-1 == columnIndex){
                    continue;
                }
                dataCellStyle = headerRow.getCell(columnIndex).getCellStyle();
                values = columnMaps.get(key);
                for (int i = 0; i < values.size(); i++) {
                    dataRow = sheet.getRow(i + 1);
                    if (null == dataRow) {
                        dataRow = sheet.createRow(i + 1);
                    }
                    dataCell = dataRow.createCell(columnIndex);
                    dataCell.setCellStyle(dataCellStyle);
                    setCellValueWithType(dataCell, values.get(i));
                }
            }
            return workbook;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(ExcelEnum.E00005);
        }
    }

    // 取得索引
    private static int findColumnIndex(Row headerRow, String key) {
        Cell cell;
        String cellValue;
        key = String.format("{{%s}}", key);
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            cell = headerRow.getCell(i);
            if (cell == null) {
                continue;
            }
            cellValue = getCellValue(cell);
            if (key.equals(cellValue)) {
                return i;
            }
        }
        return -1;
    }

    private static String getCellValue(Cell cell){
        try{
            switch (cell.getCellType()) {
                case STRING:
                case FORMULA:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    }
                    double num = cell.getNumericCellValue();
                    if (num == (long) num) {
                        return String.valueOf((long) num);
                    }
                    return String.valueOf(num);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case BLANK:
                case ERROR:
                default:
                    return  "";
            }
        }catch (IllegalStateException ex){
            return String.valueOf(cell.getNumericCellValue());
        }catch (Exception ex){
            return "";
        }
    }

    private static void setCellValueWithType(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).longValue());
        } else {
            cell.setCellValue(value.toString());
        }
    }

    // 轉換output
    private static InputStream output(Workbook workbook){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] bytes = bos.toByteArray();
            return new ByteArrayInputStream(bytes);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(ExcelEnum.E00006);
        }
    }

}
