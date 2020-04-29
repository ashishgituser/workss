package com.lambda;

import java.util.function.Function;

class Student {
	private Integer id;
	
	public Student() { System.out.println("Default constructor."); }
	public Student(Integer id) {
		this.id = id;
		System.out.println("Parameterized constructor.");
	}
	
	public Integer getId() {
		return this.id;
	}
}

public class ConstructorReferenceExample {

	public static void main(String[] args) {
	
		Function<Integer, Student> studentObject = Student::new;
		Student s = studentObject.apply(100);
	}

}
