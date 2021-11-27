package com.reactive.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.reactive.beans.Employee;
import com.reactive.dao.EmployeeDAO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

	@Autowired
	private EmployeeDAO dao;

	// 1. Get All Employees:
	public Mono<ServerResponse> streamAllEmployees(ServerRequest request) {
		Flux<Employee> allEmployees = dao.getAllEmployees();
		Mono<ServerResponse> response = ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(allEmployees,
				Employee.class);
		return response;
	}

	// 2. Get an Employee by Id:
	public Mono<ServerResponse> getEmployeeById(ServerRequest request) {
		int empId = Integer.parseInt(request.pathVariable("id"));
		Mono<Employee> employee = dao.getEmployeeByID(empId);
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(employee, Employee.class);
	}

	// 3. POST an Employee:
	public Mono<ServerResponse> saveAnEmployee(ServerRequest request) {
		Mono<Employee> employeeDTO = request.bodyToMono(Employee.class);
		Mono<String> savedEmp = dao.saveEmp(employeeDTO);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(savedEmp, Employee.class);
	}
}
