package com.employeeManagementApplication.demo.Service;

import com.employeeManagementApplication.demo.Entity.Employee;

import java.util.HashSet;
import java.util.Scanner;

public class EmployeeService {

    ExcelHandlerService excelHandlerService = new ExcelHandlerService();

    HashSet<Employee> employeeSet = new HashSet<>();

    Employee employee1 = new Employee(101, "Kethaka", 25, "Developer", "IT", 50000);
    Employee employee2 = new Employee(102, "Singhe", 25, "QA", "IT", 60000);
    Employee employee3 = new Employee(103, "Chethiya", 25, "BA", "IT", 70000);
    Employee employee4 = new Employee(104, "Sameera", 24, "Developer", "IT", 50000);

    Scanner scanner = new Scanner(System.in);
    boolean found = false;

    int id;
    String name;
    int age;
    String department;

    String designation;

    double salary;

    public EmployeeService() {
        employeeSet.add(employee1);
        employeeSet.add(employee2);
        employeeSet.add(employee3);
        employeeSet.add(employee4);
    }

    //View All employees
    public void viewAllEmployees() {
        excelHandlerService.readExcel();
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
        System.out.println("Enter ID: ");
        id = scanner.nextInt();

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

        Employee updateEmployee = new Employee(id, name, age, designation, department, salary);
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
        System.out.println("Enter id: ");
        id = scanner.nextInt();
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

        Employee newEmployee = new Employee(id, name, age, designation, department, salary);
        System.out.println(newEmployee);

        excelHandlerService.writeExcel(newEmployee);
    }

    public void searchByDepartment() {
        excelHandlerService.showAllDepartments();
        System.out.println();
        System.out.println("Enter Department: ");
        department = scanner.next();
        excelHandlerService.showAllEmployeesByDepartment(department);
        System.out.println();
    }
}
