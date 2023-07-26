package com.employeeManagementApplication.demo;

import com.employeeManagementApplication.demo.Service.EmployeeService;
import com.employeeManagementApplication.demo.Service.ExcelHandlerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

    static boolean ordering = true;

    public static void menu() {
        System.out.println("""
                *****Welcome To Employee Management System*****\s
                1. Add Employee\s
                2. View Employee\s
                3. Update Employee\s
                4. Delete Employee\s
                5. View All Employee\s
                6. Exist\s""");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();

        ExcelHandlerService excelWriter = new ExcelHandlerService();

        do {
            menu();
            System.out.println("Enter your Choice");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Add Employee");
                    employeeService.addEmployee();
                }
                case 2 -> {
                    System.out.println("View Employee");
                    employeeService.viewEmployee();
                }
                case 3 -> {
                    System.out.println("Update Employee");
                    employeeService.updateEmployee();
                }
                case 4 -> {
                    System.out.println("Delete Employee");
                    employeeService.deleteEmployee();
                }
                case 5 -> {
                    System.out.println("View All Employee");
                    employeeService.viewAllEmployees();
                }
                case 6 -> {
                    System.out.println("Thank you for using Application");
                    System.exit(0);
                }
                case 7 -> {
                    System.out.println("Get All Employees in Department");
                    employeeService.searchByDepartment();
                }
                default -> System.out.println("Please Enter valid choice");
            }

        } while (ordering);

    }

}
