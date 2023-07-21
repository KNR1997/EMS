package com.employeeManagementApplication.demo;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@NoArgsConstructor
public class ExcelHandler {
    private String filePath = "D:\\EnterpriseApplication.xlsx";

    public void readExcel() {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.getSheet("Sheet1");
            Row row = sheet.createRow(2);
            Cell cell = row.createCell(0);
            cell.setCellValue(100);

            workbook.write(fileOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
