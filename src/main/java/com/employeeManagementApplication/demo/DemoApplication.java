package com.employeeManagementApplication.demo;

import com.employeeManagementApplication.demo.Service.EmployeeService;
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
                6. Get Employees by Department Name\s
                7. Add Employee Attendance\s
                8. Show Attendance\s
                9. Set Employee Leave\s
                10. Exist\s""");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();

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
                    System.out.println("Choose a Department");
                    employeeService.getEmployeesByDepartment();
                }
                case 7 -> {
                    System.out.println("All Employees");
                    employeeService.addAttendance();
                }
                case 8 -> {
                    System.out.println("Show Attendance By Date");
                    employeeService.showAttendanceByDate();
                }
                case 9 -> {
                    System.out.println("Set Employee Leave");
                    employeeService.setEmployeeLeave();
                }
                case 10 -> {
                    System.out.println("Thank you for using Application");
                    System.exit(0);
                }
                default -> System.out.println("Please Enter valid choice");
            }

        } while (ordering);

    }

}
