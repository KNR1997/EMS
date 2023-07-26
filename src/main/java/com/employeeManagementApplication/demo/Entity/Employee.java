package com.employeeManagementApplication.demo.Entity;

import lombok.Data;

@Data
public class Employee {

    private int id;
    private String name;
    private int age;
    private String designation;
    private String department;
    private double salary;
    @Override
    public String toString() {
        return "Employee{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
    public Employee(String name, int age, String designation, String department, double salary) {
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.department = department;
        this.salary = salary;
    }
}
