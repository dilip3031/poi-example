package com.dileep.poiexample.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class ExcelUtil {
    public static XSSFWorkbook getExcelWorkbook(String headers,String sheetName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        XSSFRow row = sheet.createRow(0);
        String[] names = headers.split(",");
        Integer cellCounter = 0;
        for (String name : names) {
            row.createCell(cellCounter).setCellValue(name);
            cellCounter++;
        }
        return workbook;
    }
}
