package com.employeeManagementApplication.demo.Service;

import com.employeeManagementApplication.demo.Entity.Employee;

import java.util.HashSet;
import java.util.Scanner;

public class EmployeeService {

    ExcelHandlerService excelHandlerService = new ExcelHandlerService();

    HashSet<Employee> employeeSet = new HashSet<>();

    Scanner scanner = new Scanner(System.in);
    boolean found = false;

    int id;
    String name;
    int age;
    String department;

    String designation;

    double salary;

    //View All employees
    public void viewAllEmployees() {
        excelHandlerService.getAllEmployees();
        System.out.println();
    }

    //View Employee based on ID
    public void viewEmployee() {
        System.out.println("Enter ID: ");
        id = scanner.nextInt();
        excelHandlerService.viewEmployee(id);
        System.out.println();
    }

    //Update Employee
    public void updateEmployee() {
        System.out.println("Enter name: ");
        name = scanner.next();
        System.out.println("Enter age: ");
        age = scanner.nextInt();
        System.out.println("Enter Designation: ");
        designation = scanner.next();
        System.out.println("Enter Department");
        department = scanner.next();
        System.out.println("Enter salary");
        salary = scanner.nextDouble();

        Employee updateEmployee = new Employee(name, age, designation, department, salary);
        excelHandlerService.updateEmployee(id, updateEmployee);

    }

    //Delete Employee
    public void deleteEmployee() {
        System.out.println("Enter Id: ");
        id = scanner.nextInt();
        excelHandlerService.deleteEmployee(id);
    }

    //Add employee
    public void addEmployee() {
        System.out.println("Enter name: ");
        name = scanner.next();
        System.out.println("Enter age: ");
        age = scanner.nextInt();
        System.out.println("Enter Designation: ");
        designation = scanner.next();
        System.out.println("Enter Department");
        department = scanner.next();
        System.out.println("Enter salary");
        salary = scanner.nextDouble();

        Employee newEmployee = new Employee(name, age, designation, department, salary);
        System.out.println(newEmployee);

        excelHandlerService.addEmployee(newEmployee);
    }

    public void getEmployeesByDepartment() {
        //Show All the distinct Department Names
        excelHandlerService.showAllDepartments();
        System.out.println();
        //Get user Input
        System.out.println("Enter Department: ");
        department = scanner.next();
        //Show All the Employees based on the user entered Department name
        excelHandlerService.showAllEmployeesByDepartment(department);
        System.out.println();
    }

    public void addAttendance() {
        //Show All the employees first
        excelHandlerService.getAllEmployees();

        String[] status = {"Present", "Absent"};

        for (String state: status) {
            System.out.println("Enter the IDs of " + state + " employees: (Like: 100,101,102)");
            String input = scanner.nextLine();

            // Split the input string using the comma (,) as the delimiter
            String[] values = input.split(",");
            excelHandlerService.addAttendance(values, state);
        }
    }
}
