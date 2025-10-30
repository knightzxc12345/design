package com.design.utils;

import com.design.base.common.Common;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;

public class ExcelUtil {

    // 用於儲存 List cell 資訊
    private static class ListCellInfo {

        Cell cell;

        String listFieldName;

        String listItemField;

    }

    public static <T> byte[] convert(InputStream templateStream, T data) {
        try {
            if (templateStream == null) {
                return null;
            }
            Workbook workbook = new XSSFWorkbook(templateStream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            for (Sheet sheet : workbook) {
                fillSheet(sheet, data);
            }

            workbook.write(bos);
            return bos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static <T> void fillSheet(Sheet sheet, T data) {
        try {
            int rowIndex = 0;
            while (rowIndex <= sheet.getLastRowNum()) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    rowIndex++;
                    continue;
                }

                // 先檢查這列是否有 List cell
                List<ListCellInfo> listCells = new java.util.ArrayList<>();
                for (Cell cell : row) {
                    if (cell == null || cell.getCellType() != CellType.STRING) continue;

                    String value = cell.getStringCellValue();
                    Matcher matcher = Common.PLACEHOLDER_PATTERN.matcher(value);

                    while (matcher.find()) {
                        String key = matcher.group(1).trim();
                        if (key.contains(".")) {
                            String[] parts = key.split("\\.");
                            ListCellInfo info = new ListCellInfo();
                            info.cell = cell;
                            info.listFieldName = parts[0];
                            info.listItemField = parts[1];
                            listCells.add(info);
                        }
                    }
                }

                if (!listCells.isEmpty()) {
                    // 有 List 才展開
                    Object firstListObj = getValueByKey(data, listCells.get(0).listFieldName);
                    if (!(firstListObj instanceof List)) {
                        rowIndex++;
                        continue;
                    }

                    List<?> list = (List<?>) firstListObj;
                    int startRow = row.getRowNum();
                    int insertCount = list.size() - 1;

                    if (insertCount > 0) {
                        sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), insertCount);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        Row targetRow;
                        if (i == 0) {
                            targetRow = row;
                        } else {
                            targetRow = sheet.createRow(startRow + i);
                            copyRowStyle(sheet, row, targetRow);
                        }

                        for (ListCellInfo info : listCells) {
                            Cell targetCell = targetRow.getCell(info.cell.getColumnIndex());
                            if (targetCell == null) {
                                targetCell = targetRow.createCell(info.cell.getColumnIndex());
                            }
                            Object listObj = getValueByKey(data, info.listFieldName);
                            if (!(listObj instanceof List)) continue;

                            Object item = ((List<?>) listObj).get(i);
                            Object replacement = getValueByKey(item, info.listItemField);
                            targetCell.setCellValue(replacement != null ? replacement.toString() : "");
                        }
                    }

                    rowIndex = startRow + list.size();
                    continue;
                }

                // 沒有 List，單一欄位替換
                for (Cell cell : row) {
                    if (cell == null || cell.getCellType() != CellType.STRING) continue;

                    String value = cell.getStringCellValue();
                    Matcher matcher = Common.PLACEHOLDER_PATTERN.matcher(value);

                    while (matcher.find()) {
                        String key = matcher.group(1).trim();
                        if (!key.contains(".")) {
                            Object replacement = getValueByKey(data, key);
                            cell.setCellValue(replacement != null ? replacement.toString() : "");
                        }
                    }
                }

                rowIndex++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void copyRowStyle(Sheet sheet, Row sourceRow, Row targetRow) {
        targetRow.setHeight(sourceRow.getHeight());
        for (int i = sourceRow.getFirstCellNum(); i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            if (oldCell == null) continue;

            Cell newCell = targetRow.createCell(i);
            newCell.setCellStyle(oldCell.getCellStyle());
        }
    }

    private static Object getValueByKey(Object obj, String key) {
        try {
            String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method method = obj.getClass().getMethod(methodName);
            return method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

}
