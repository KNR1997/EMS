package com.employeeManagementApplication.demo;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@NoArgsConstructor
public class ExcelHandler {
    private final String filePath = "D:\\EnterpriseApplication.xlsx";

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

    public void writeExcel(Employee newEmployee) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to add to the first sheet (index 0)
            int lastRowNum = sheet.getLastRowNum(); // Get the index of the last row in the sheet

            // Create a new row at the end of the sheet
            Row newRow = sheet.createRow(lastRowNum + 1);

            // Populate the cells in the row with data for the new record
            Cell cell0 = newRow.createCell(0);
            cell0.setCellValue(newEmployee.getId());

            Cell cell1 = newRow.createCell(1);
            cell1.setCellValue(newEmployee.getName());

            Cell cell2 = newRow.createCell(2);
            cell2.setCellValue(newEmployee.getAge());

            Cell cell3 = newRow.createCell(3);
            cell3.setCellValue(newEmployee.getDesignation());

            Cell cell4 = newRow.createCell(4);
            cell4.setCellValue(newEmployee.getDepartment());

            Cell cell5 = newRow.createCell(5);
            cell5.setCellValue(newEmployee.getSalary());

            // Write the changes back to the Excel file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("New record added successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
