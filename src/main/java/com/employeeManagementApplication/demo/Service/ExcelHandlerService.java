package com.employeeManagementApplication.demo.Service;

import com.employeeManagementApplication.demo.Entity.Employee;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@NoArgsConstructor
public class ExcelHandlerService {

    private final String filePath = "D:\\EnterpriseApplication.xlsx";

    PrintTableService printTableService = new PrintTableService();

    CommonService commonService = new CommonService(filePath);

    public void getAllEmployees() {
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
            printTableService.printTable(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee newEmployee) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to add to the first sheet (index 0)
            int lastRowNum = sheet.getLastRowNum(); // Get the index of the last row in the sheet
            double lastEmployeeID = sheet.getRow(lastRowNum).getCell(0).getNumericCellValue(); // Get Last Employee ID in the Excel

            // Create a new row at the end of the sheet
            Row newRow = sheet.createRow(lastRowNum + 1);

            // Populate the cells in the row with data for the new record
            Cell cell0 = newRow.createCell(0);
            cell0.setCellValue(lastEmployeeID + 1);

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

                        //Print the Table
                        printTableService.printTable(data);

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

    public void updateEmployee(int employeeID, Employee updateEmployee) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to update the first sheet (index 0)

            // Find the row index that contains the target ID
            int rowIndexToUpdate = -1;
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cellID = row.getCell(0);
                    if (cellID != null && cellID.getCellType() == CellType.NUMERIC && cellID.getNumericCellValue() == employeeID) {
                        rowIndexToUpdate = rowIndex;
                        break; // Assuming the ID is unique, break the loop when the target ID is found
                    }
                }
            }

            if (rowIndexToUpdate != -1) {
                Row rowToUpdate = sheet.getRow(rowIndexToUpdate);
                if (rowToUpdate != null) {
                    // Get the Excel cell values
                    Cell name = rowToUpdate.getCell(1);
                    Cell age = rowToUpdate.getCell(2);
                    Cell designation = rowToUpdate.getCell(3);
                    Cell department = rowToUpdate.getCell(4);
                    Cell salary = rowToUpdate.getCell(5);

                    if (name != null && name.getCellType() == CellType.STRING) {
                        name.setCellValue(updateEmployee.getName()); // Replace "Updated Name" with the new value
                    }
                    if (age != null && age.getCellType() == CellType.NUMERIC) {
                        age.setCellValue(updateEmployee.getAge()); // Replace "Updated Age" with the new value
                    }
                    if (designation != null && designation.getCellType() == CellType.STRING) {
                        designation.setCellValue(updateEmployee.getDesignation()); // Replace "Updated Designation" with the new value
                    }
                    if (department != null && department.getCellType() == CellType.STRING) {
                        department.setCellValue(updateEmployee.getDepartment()); // Replace "Updated Department" with the new value
                    }
                    if (salary != null && salary.getCellType() == CellType.NUMERIC) {
                        salary.setCellValue(updateEmployee.getSalary()); // Replace "Updated Salary" with the new value
                    }

                    // Write the changes back to the Excel file
                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                    }

                    System.out.println("Row with ID " + employeeID + " has been updated successfully.");
                }
            } else {
                System.out.println("ID " + employeeID + " not found in the Excel file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllDepartments() {
        int columnIndex = 4; // Replace with the index of the column you want to extract (0-based index)

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read from the first sheet (index 0)
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"Departments"});

            HashSet<String> distinctValues = new HashSet<>();

            // Iterate over all rows and get the data from the specific column
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Cell cell = row.getCell(columnIndex);
                if (cell != null) {
                    String cellValue = getCellValueAsString(cell);
                    distinctValues.add(cellValue);
                }
            }

            // Print the distinct values
            for (String value : distinctValues) {
                String[] stringArray = new String[1];
                stringArray[0] = value;
                data.add(stringArray);
            }
            printTableService.printTable(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllEmployeesByDepartment(String departmentName) {
        int columnIndex = 4; // Replace with the index of the column you want to search (0-based index)

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"ID", "Name", "Age", "Designation", "Department", "Salary"});

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read from the first sheet (index 0)

            // Iterate over all rows and get the data from the specific column
            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);
                String[] stringArray = new String[6];

                if (cell != null) {
                    String cellValue = getCellValueAsString(cell);
                    if (cellValue.equalsIgnoreCase(departmentName)) {
                        for (int i = 0; i < row.getLastCellNum(); i++) {
                            stringArray[i] = getCellValueAsString(row.getCell(i));
                        }
                        data.add(stringArray);
                    }
                }
            }
            printTableService.printTable(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case BLANK -> "EMPTY";
            default -> "UNKNOWN";
        };
    }

    public void addAttendance(String[] values, String state) {
        // Create an integer array of the same length as the string array
        int[] employeeIDs = new int[values.length];
        LocalDate today = LocalDate.now();

        // Define a date format pattern
        String dateFormatPattern = "yyyy-MM-dd";

        // Create a DateTimeFormatter with the given pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);

        // Create a new HashMap to hold the string map
        HashMap<Integer, String> stringMap = new HashMap<>();

        // Convert each element of the string array to an integer and store in the integer array
        for (int i = 0; i < values.length; i++) {
            employeeIDs[i] = Integer.parseInt(values[i]);
        }

        // Get only the employee IDs that not entered
        ArrayList<Integer> employeeIDsThatNotAddedArrayList = commonService.removeAlreadyAddedEmployeesForAttendance(employeeIDs, today.format(formatter));

        // Convert the ArrayList to an int array
        int[] employeeIDsThatNotAddedArray = new int[employeeIDsThatNotAddedArrayList.size()];
        for (int i = 0; i < employeeIDsThatNotAddedArrayList.size(); i++) {
            employeeIDsThatNotAddedArray[i] = employeeIDsThatNotAddedArrayList.get(i);
        }

        for (int employeeID : employeeIDsThatNotAddedArray) {
            String employeeName = commonService.getEmployeeNameForID(employeeID);
            stringMap.put(employeeID, employeeName);
        }

        for (int employeeID : employeeIDsThatNotAddedArray) {

            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = WorkbookFactory.create(file)) {

                Sheet sheet = workbook.getSheetAt(1); // Assuming you want to add to the first sheet (index 0)
                int lastRowNum = sheet.getLastRowNum(); // Get the index of the last row in the sheet

                // Create a new row at the end of the sheet
                Row newRow = sheet.createRow(lastRowNum + 1);

                // Populate the cells in the row with data for the new record
                Cell cell0 = newRow.createCell(0);
                cell0.setCellValue(employeeID);

                Cell cell1 = newRow.createCell(1);
                cell1.setCellValue(stringMap.get(employeeID));

                Cell cell2 = newRow.createCell(2);
                cell2.setCellValue(state);

                Cell cell3 = newRow.createCell(3);
                cell3.setCellValue(today.format(formatter));

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

    public void showAttendanceByDate(String date) {
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(1); // Assuming you want to read from the first sheet (index 0)
            List<String[]> data = new ArrayList<>();
            data.add(new String[]{"ID", "Name", "Date", "Status"});

            // Iterate over all rows, starting from the second row (index 1)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                String[] stringArray = new String[4];

                if (row != null) {
                    // Get the cell with ID (assuming it's in the first column, index 0)
                    Cell cellID = row.getCell(3);

                    // Check if the cell is not empty and its value matches the targetID
                    if (cellID != null && cellID.getCellType() == CellType.STRING && Objects.equals(cellID.getStringCellValue(), date)) {
                        // Get the values from the other cells in the same row (e.g., assuming you have a column for Name in index 1)
                        Cell cell0 = row.getCell(0);
                        Cell cell1 = row.getCell(1);
                        Cell cell2 = row.getCell(2);
                        Cell cell3 = row.getCell(3);


                        stringArray[0] = Integer.toString((int) cell0.getNumericCellValue());
                        stringArray[1] = cell1.getStringCellValue();
                        stringArray[2] = cell2.getStringCellValue();
                        stringArray[3] = cell3.getStringCellValue();

                        data.add(stringArray);
                    }
                }
            }
            //Print the Table
            printTableService.printTable(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEmployeeLeave(String userID, String leaveDate) {
        //Get month of the leaveDate
        String month = commonService.getMonthFromDate(leaveDate);

        //Get Employee Name for ID
        String employeeName = commonService.getEmployeeNameForID(Integer.parseInt(userID));

        //Check user record created for this month
        Boolean userRecordEnteredForThisMonth = commonService.checkUserRecordInLeaveSheetForThisMonth(userID, month);

        if (!userRecordEnteredForThisMonth) {
            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = WorkbookFactory.create(file)) {

                Sheet sheet = workbook.getSheetAt(2); // Assuming you want to add to the first sheet (index 0)
                int lastRowNum = sheet.getLastRowNum(); // Get the index of the last row in the sheet

                // Create a new row at the end of the sheet
                Row newRow = sheet.createRow(lastRowNum + 1);

                // Populate the cells in the row with data for the new record
                Cell cell0 = newRow.createCell(0);
                cell0.setCellValue(userID);

                Cell cell1 = newRow.createCell(1);
                cell1.setCellValue(employeeName);

                Cell cell2 = newRow.createCell(2);
                cell2.setCellValue(month);

                Cell cell3 = newRow.createCell(3);
                cell3.setCellValue(leaveDate);

                newRow.createCell(4);

                newRow.createCell(5);

                Cell cell7 = newRow.createCell(6);
                cell7.setCellValue(2);

                // Write the changes back to the Excel file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }

                System.out.println("New record added successfully!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = WorkbookFactory.create(file)) {

                Sheet sheet = workbook.getSheetAt(2); // Assuming you want to add to the first sheet (index 0)
                int rowNumber = commonService.getLeaveSheetRecordForEmployeeIdAndMonth(userID, month); // Get the already existed record for employee/Month

                // Get the row from the sheet
                Row row = sheet.getRow(rowNumber);

                // Populate the cells in the row with data for the new record
                Cell cell2 = row.getCell(4);
                Cell cell3 = row.getCell(5);
                Cell cell7 = row.getCell(6);

                if (cell7.getNumericCellValue() == 2) {
                    cell2.setCellValue(leaveDate);
                    cell7.setCellValue(1);
                } else if (cell7.getNumericCellValue() == 1) {
                    cell3.setCellValue(leaveDate);
                    cell7.setCellValue(0);
                } else if ((cell7.getNumericCellValue() == 0)) {
                    System.out.println("Cannot get any Leaves this month !!!");
                    return;
                }

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
}
