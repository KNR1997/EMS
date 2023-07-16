package com.employeeManagementApplication.demo;

import java.util.HashSet;
import java.util.Scanner;

public class EmployeeService {

    HashSet<Employee> employeeSet = new HashSet<Employee>();

    Employee employee1 = new Employee(101, "Kethaka", 25,"Developer", "IT", 50000);
    Employee employee2 = new Employee(102, "Singhe", 25,"QA", "IT", 60000);
    Employee employee3 = new Employee(103, "Chethiya", 25,"BA", "IT", 70000);
    Employee employee4 = new Employee(104, "Sameera", 24,"Developer", "IT", 50000);

    Scanner scanner = new Scanner(System.in);

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
}
