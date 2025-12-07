package br.com.emanuelcosta.swingdatabase.model.entity;

import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private Date birthDate;
    private double salary;
    private Department department;

    public Employee() {
    }

    public Employee(int id, String name, Date birthDate, double salary, Department department) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.salary = salary;
        this.department = department;
    }

    public Employee(String name, Date birthDate, double salary, Department department) {
        this.name = name;
        this.birthDate = birthDate;
        this.salary = salary;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", department=" + (department != null ? department.getName() : "null") +
                " }";
    }
}
