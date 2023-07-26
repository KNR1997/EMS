package com.employeeManagementApplication.demo.Service;


import java.util.List;

public class PrintTableService {

    public void printTable(List<String[]> data) {
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
}
