package com.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee {
	private Integer id;
	private String name;
	private Double salary;
	
	public Employee() {}
	public Employee(Integer id, String name, Double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "[" + this.getId() + ", " + this.getName() + ", " + this.getSalary() + "]";
	}
}

public class LambdaExpressionComparator {

	public static void main(String[] args) {
		
		try {
			List<Employee> employees = Arrays.asList(
					new Employee(1342, "ashish", 20000.0),
					new Employee(1232, "anshu", 40000.0));
			Comparator<Employee> sortById = (Employee e1, Employee e2) -> {
				return e1.getId().compareTo(e2.getId());
			};
			Comparator<Employee> sortBySalary = (Employee e1, Employee e2) -> {
				return e1.getSalary().compareTo(e2.getSalary());
			};
			//Collections.sort(employees, Comparator.comparing(Employee::getId).reversed());
			employees.sort(Comparator.comparing(Employee::getSalary).reversed());
			System.out.println(employees);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
