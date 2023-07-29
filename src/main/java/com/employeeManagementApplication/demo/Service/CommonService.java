package com.employeeManagementApplication.demo.Service;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class CommonService {

    private final String filePath;

    public CommonService(String filePath) {
        this.filePath = filePath;
    }

    public String getEmployeeNameForID(Integer employeeID) {
        String employeeName = null;
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
                        Cell employeeNameCell = row.getCell(1);
                        employeeName = employeeNameCell.getStringCellValue();

                        // Break the loop once the targetID is found (assuming the ID is unique)
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeName;
    }

    public ArrayList<Integer> removeAlreadyAddedEmployeesForAttendance(int[] employeeIDs, String date) {
        ArrayList<Integer> employeesNotAdded = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(1);
            ArrayList<Integer> alreadyAddedEmployeeIDs = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {

                    if (Objects.equals(row.getCell(3).getStringCellValue(), date)) {
                        Integer employeeID = (int) row.getCell(0).getNumericCellValue();
                        alreadyAddedEmployeeIDs.add(employeeID);
                    }
                }
            }

            // Convert ArrayList to HashSet for efficient look-up
            HashSet<Integer> hashSet = new HashSet<>(alreadyAddedEmployeeIDs);

            // Find employee Ids not added
            for (int element : employeeIDs) {
                if (!hashSet.contains(element)) {
                    employeesNotAdded.add(element);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeesNotAdded;
    }

    public int getLeaveSheetRecordForEmployeeIdAndMonth(String userID, String month) {
        int rowNumber = 0;
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(2);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {

                    if (Objects.equals(row.getCell(0).getStringCellValue(), userID)
                            && Objects.equals(row.getCell(2).getStringCellValue(), month)) {
                        rowNumber = row.getRowNum();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowNumber;
    }

    public Boolean checkUserRecordInLeaveSheetForThisMonth(String userID, String month) {
        boolean userRecordAlreadyCreatedForThisMonth = false;

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(2);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {

                    if (Objects.equals(row.getCell(0).getStringCellValue(), userID)
                            && Objects.equals(row.getCell(2).getStringCellValue(), month)) {
                        userRecordAlreadyCreatedForThisMonth = true;

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userRecordAlreadyCreatedForThisMonth;
    }

    public String getMonthFromDate(String date) {
        String dateFormatPattern = "yyyy-MM-dd"; // Replace with the date format pattern of your date string

        // Create a DateTimeFormatter with the given pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);

        // Parse the date string into a LocalDate object
        LocalDate Date = LocalDate.parse(date, formatter);

        // Get the month from the LocalDate object
        return Date.getMonth().name(); // Returns the month as a string (e.g., "JANUARY", "FEBRUARY", etc.)

    }




}
