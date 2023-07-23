package com.employeeManagementApplication.demo;

import java.util.HashSet;
import java.util.Scanner;

public class EmployeeService {

    ExcelHandler excelHandler = new ExcelHandler();

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
        excelHandler.readExcel();
        System.out.println();
    }

    //View Employee based on ID
    public void viewEmployee() {
        System.out.println("Enter ID: ");
        id = scanner.nextInt();
        excelHandler.viewEmployee(id);
        System.out.println();
    }

    //Update Employee
    public void updateEmployee() {
        System.out.println("Enter ID: ");
        id = scanner.nextInt();
        boolean found = false;
        for (Employee employee : employeeSet) {
            if (employee.getId() == id) {
                System.out.println("Enter name: ");
                name = scanner.next();
                System.out.println("Enter new Salary: ");
                salary = scanner.nextDouble();
                employee.setName(name);
                employee.setSalary(salary);
                System.out.println("Updated Details of Employee: ");
                System.out.println(employee);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Employee with this ID is not present");
        }
        else {
            System.out.println("Employee details updated successfully");
        }
    }

    //Delete Employee
    public void deleteEmployee() {
        System.out.println("Enter Id: ");
        id = scanner.nextInt();
        boolean found = false;
        Employee empDelete = null;
        for (Employee employee : employeeSet) {
            if (employee.getId() == id) {
                empDelete = employee;
                found = true;
            }
        }
        if (!found) {
            System.out.println("Employee is not present");
        }
        else {
            employeeSet.remove(empDelete);
            System.out.println("Employee deleted successfully");
        }
    }

    //Add employee
    public Employee addEmployee() {
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
        employeeSet.add(newEmployee);
        System.out.println(newEmployee);
        System.out.println("Employee added successfully");
        return newEmployee;
    }
}
