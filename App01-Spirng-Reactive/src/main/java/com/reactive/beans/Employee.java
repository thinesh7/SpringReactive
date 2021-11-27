package com.reactive.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

	private Integer employeeId;
	private String employeeName;
	private Double employeeSalary;
}
