package com.employeeManagementApplication.demo;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ExcelHandler {
    private final String filePath = "D:\\EnterpriseApplication.xlsx";

    public void readExcel() {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"ID", "Name", "Age", "Designation", "Department", "Salary"});

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    String[] stringArray = new String[6];

                    // Iterate over all cells in the row
                    for (Cell cell : row) {
                        // Check if the cell is null or blank (empty)
                        if (cell == null || cell.getCellType() == CellType.BLANK || cell.toString() == "") {
                            stringArray[cell.getColumnIndex()] = "_";
                        } else {
                            // Check the cell type and retrieve the cell value accordingly
                            switch (cell.getCellType()) {
                                case STRING -> stringArray[cell.getColumnIndex()] = cell.getStringCellValue();
                                case NUMERIC ->
                                        stringArray[cell.getColumnIndex()] = Integer.toString((int) cell.getNumericCellValue());
                                case BOOLEAN -> {
                                    System.out.print(cell.getBooleanCellValue() + "\t");
                                    stringArray[cell.getColumnIndex()] = String.valueOf(cell.getBooleanCellValue());
                                }
                                default -> stringArray[cell.getColumnIndex()] = "@#$%";
                            }
                        }
                    }
                    data.add(stringArray);
                }
            }

            //Print the Table
            printTable(data);

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

    private static void printTable(List<String[]> data) {
        int[] maxLengths = new int[data.get(0).length];

        // Calculate the maximum length for each column
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                maxLengths[i] = Math.max(maxLengths[i], row[i].length());
            }
        }

        // Print the table headers
        for (int i = 0; i < data.get(0).length; i++) {
            System.out.print(padRight(data.get(0)[i], maxLengths[i]) + " | ");
        }
        System.out.println();

        // Print the table separator
        for (int i = 0; i < data.get(0).length; i++) {
            System.out.print(padRight("", maxLengths[i], '-') + " | ");
        }
        System.out.println();

        // Print the table rows
        for (int rowIndex = 1; rowIndex < data.size(); rowIndex++) {
            String[] row = data.get(rowIndex);
            for (int i = 0; i < row.length; i++) {
                System.out.print(padRight(row[i], maxLengths[i]) + " | ");
            }
            System.out.println();
        }
    }

    private static String padRight(String str, int length) {
        return padRight(str, length, ' ');
    }

    private static String padRight(String str, int length, char paddingChar) {
        StringBuilder paddedStr = new StringBuilder(str);
        while (paddedStr.length() < length) {
            paddedStr.append(paddingChar);
        }
        return paddedStr.toString();
    }

    public void viewEmployee(int employeeID) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read from the first sheet (index 0)

            // Iterate over all rows, starting from the second row (index 1)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    // Get the cell with ID (assuming it's in the first column, index 0)
                    Cell cellID = row.getCell(0);

                    // Check if the cell is not empty and its value matches the targetID
                    if (cellID != null && cellID.getCellType() == CellType.NUMERIC && cellID.getNumericCellValue() == employeeID) {
                        // Get the values from the other cells in the same row (e.g., assuming you have a column for Name in index 1)
                        Cell cell1 = row.getCell(1);
                        Cell cell2 = row.getCell(2);
                        Cell cell3 = row.getCell(3);
                        Cell cell4 = row.getCell(4);
                        Cell cell5 = row.getCell(5);

                        List<String[]> data = new ArrayList<>();
                        String[] stringArray = new String[6];

                        stringArray[0] = Integer.toString((int) cellID.getNumericCellValue());
                        stringArray[1] = cell1.getStringCellValue();
                        stringArray[2] = Integer.toString((int) cell2.getNumericCellValue());
                        stringArray[3] = cell3.getStringCellValue();
                        stringArray[4] = cell4.getStringCellValue();
                        stringArray[5] = Integer.toString((int) cell5.getNumericCellValue());

                        // Add Table Header details
                        data.add(new String[]{"ID", "Name", "Age", "Designation", "Department", "Salary"});
                        data.add(stringArray);
                        printTable(data);

                        // Break the loop once the targetID is found (assuming the ID is unique)
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int employeeID) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to delete from the first sheet (index 0)

            // Find the row index that contains the target ID
            int rowIndexToDelete = -1;
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellID = row.getCell(0);
                    if (cellID != null && cellID.getCellType() == CellType.NUMERIC && cellID.getNumericCellValue() == employeeID) {
                        rowIndexToDelete = rowIndex;
                        break; // Assuming the ID is unique, break the loop when the target ID is found
                    }
                }
            }

            if (rowIndexToDelete != -1) {
                // If the target ID is found, delete the row by shifting rows above it downwards
                sheet.shiftRows(rowIndexToDelete + 1, sheet.getLastRowNum(), -1);

                // Update the last row index
                int lastRowIndex = sheet.getLastRowNum();
                if (lastRowIndex >= 0) {
                    Row lastRow = sheet.getRow(lastRowIndex);
                    if (lastRow != null) {
                        // Clear the contents of the last row to avoid duplicates when writing back to the Excel file
                        sheet.removeRow(lastRow);
                    }
                }

                // Write the changes back to the Excel file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }

                System.out.println("Row with ID " + employeeID + " has been deleted successfully.");
            } else {
                System.out.println("ID " + employeeID + " not found in the Excel file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
