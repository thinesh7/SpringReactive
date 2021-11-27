package com.reactive.dao;

import java.time.Duration;
import java.util.Random;
import org.springframework.stereotype.Repository;
import com.reactive.beans.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeDAO {

	public Flux<Employee> getAllEmployees() {
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("Processing Count --> " + i))
				.map(i -> new Employee(i, "Name- " + i, Double.valueOf(new Random().nextInt(100000))));
	}

	public Mono<Employee> getEmployeeByID(int empId) {
		Mono<Employee> monoRes = getAllEmployees().filter(emp -> emp.getEmployeeId() == empId).next();
		return monoRes;
	}

	public Mono<String> saveEmp(Mono<Employee> employee) {
		return employee.map(i -> i.getEmployeeId() + " " + i.getEmployeeName());
	}
}
