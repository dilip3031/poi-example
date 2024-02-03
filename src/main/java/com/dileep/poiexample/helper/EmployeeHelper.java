package com.dileep.poiexample.helper;

import com.dileep.poiexample.entity.EmployeeEntity;
import com.dileep.poiexample.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EmployeeHelper {
    public ByteArrayOutputStream getEmployeeExcel(List<EmployeeEntity> allEmployess, Boolean isDataRequested) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String sheetName = "Employee", headers = "id,deleted,name,email,age,salary,experience,createdDate,updateDate,initVersion";
        XSSFWorkbook workbook = ExcelUtil.getExcelWorkbook(headers, sheetName);
        XSSFSheet employeeSheet = workbook.getSheet(sheetName);
        Integer rowCount = 1;
        if (isDataRequested) {
            for (EmployeeEntity employeeEntity : allEmployess) {
                Integer cellCount = 0;
                XSSFRow row = employeeSheet.createRow(rowCount);
                row.createCell(cellCount).setCellValue(employeeEntity.getId());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getDeleted());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getName());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getEmail());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getAge());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getSalary());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getExperience());
                cellCount++;
                XSSFCell createdDate = row.createCell(cellCount);
                createdDate.setCellValue(employeeEntity.getCreatedDate());
                cellCount++;
                XSSFCell updateDate = row.createCell(cellCount);
                updateDate.setCellValue(employeeEntity.getUpdateDate());
                cellCount++;
                row.createCell(cellCount).setCellValue(employeeEntity.getInitVersion());
                rowCount++;
            }
        }
        workbook.write(stream);
        workbook.close();
        return stream;
    }


    public List<EmployeeEntity> getDataFromExcel(MultipartFile file) throws IOException {
        List<EmployeeEntity> employeeData = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        Iterator<Sheet> sheetIterator = workbook.iterator();
        while (sheetIterator.hasNext()) {
            Iterator<Row> iterator = sheetIterator.next().iterator();
            iterator.forEachRemaining(cells -> {
                if(cells.getRowNum()>0){

                    EmployeeEntity employeeEntity = EmployeeEntity.builder()
                        .name(cells.getCell(2).getStringCellValue())
                        .email(cells.getCell(3).getStringCellValue())
                        .age(Integer.valueOf((cells.getCell(4).getNumericCellValue()+"").replaceAll("\\.0", "")))
                        .salary(Long.valueOf((cells.getCell(5).getNumericCellValue()+"").replaceAll("\\.0", "")))
                        .experience(cells.getCell(6).getNumericCellValue())
                        .build();
                if(cells.getCell(0)!=null&&!Double.valueOf(cells.getCell(0).getNumericCellValue()).isNaN()){
                    employeeEntity.setId(Long.valueOf((cells.getCell(0).getNumericCellValue()+"").replaceAll("\\.0", "")));
                    employeeEntity.setInitVersion(Integer.valueOf((cells.getCell(9).getNumericCellValue()+"").replaceAll("\\.0", "")));
                }
                employeeData.add(employeeEntity);}
            });
        }

        return employeeData;
    }
}
