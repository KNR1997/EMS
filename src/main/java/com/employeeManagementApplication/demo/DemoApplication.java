package com.employeeManagementApplication.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

    EmployeeService service = new EmployeeService();
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
        do {
            menu();
            System.out.println("Enter your Choice");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Add Employee");
                case 2 -> System.out.println("View Employee");
                case 3 -> System.out.println("Update Employee");
                case 4 -> System.out.println("Delete Employee");
                case 5 -> System.out.println("View All Employee");
                case 6 -> {
                    System.out.println("Thank you for using Application");
                    System.exit(0);
                }
                default -> System.out.println("Please Enter valid choice");
            }

        } while (ordering);

    }

}
