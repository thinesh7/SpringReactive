package com.reactive.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reactive.beans.Employee;
import com.reactive.service.CustomerService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/customers")
public class ReactiveController {

	@Autowired
	private CustomerService service;

	// 1. Normal Traditional Way of Programming:
	@GetMapping("/getNEmps")
	public List<Employee> getAllEmployees() {
		return service.getAllEmployee();
	} // http://localhost:8080/customers/getNEmps

	
	
	
	// 2. Reactive Programming:
	@GetMapping(value = "/getREmps", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Employee> getAllEmpoyessUsingFlux() {
		return service.getAllEmpoyeesUsingFlux();
	} // http://localhost:8080/customers/getREmps

}
