package com.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ExecutorFuturePractice {

	public static void main(String[] args) {

		List<Integer> employeeList = Arrays.asList(1122, 1123, 1144, 1111);

		ExecutorService executor = Executors.newFixedThreadPool(3);
		Map<String, Map<Object, Long>> data = employeeList.stream().map(empId -> executor.submit(new ReportProcessor(empId)))
				.map(it -> {
					try {
						return it.get();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).flatMap(it -> it.stream())
				.collect(Collectors.groupingBy(EmployeeLeaveReport::getName, Collectors.groupingBy(it -> it.getLeaveDate().getMonth(), Collectors.counting())));
		System.out.println(data);
	}

}

class ReportProcessor implements Callable<List<EmployeeLeaveReport>> {
	private Integer employeeId;

	public ReportProcessor(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public List<EmployeeLeaveReport> call() throws Exception {
		return this.employeeData().stream().filter(it -> it.getId().equals(this.employeeId))
				.collect(Collectors.toList());
	}

	public List<EmployeeLeaveReport> employeeData() {
		return Arrays.asList(new EmployeeLeaveReport(1122, "Ashish", new Date(2020, 01, 01)),
				new EmployeeLeaveReport(1122, "Ashish", new Date(2020, 02, 05)),
				new EmployeeLeaveReport(1122, "Ashish", new Date(2020, 02, 02)),
				new EmployeeLeaveReport(1123, "Anshu", new Date(2020, 03, 11)),
				new EmployeeLeaveReport(1123, "Anshu", new Date(2020, 04, 10)),
				new EmployeeLeaveReport(1123, "Anshu", new Date(2020, 05, 11)),
				new EmployeeLeaveReport(1144, "Levi", new Date(2020, 06, 19)),
				new EmployeeLeaveReport(1144, "Levi", new Date(2020, 06, 20)),
				new EmployeeLeaveReport(1111, "Viko", new Date(2020, 07, 01)),
				new EmployeeLeaveReport(1111, "Viko", new Date(2020, 07, 10)));
	}
}

class EmployeeLeaveReport {
	private Integer id;
	private String name;
	private Date leaveDate;

	public EmployeeLeaveReport(Integer id, String name, Date leaveDate) {
		super();
		this.id = id;
		this.name = name;
		this.leaveDate = leaveDate;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	@Override
	public String toString() {
		return "EmployeeLeaveReport [id=" + id + ", name=" + name + ", leaveDate=" + leaveDate + "]";
	}

}
