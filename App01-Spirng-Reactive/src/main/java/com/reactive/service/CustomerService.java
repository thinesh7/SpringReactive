package com.reactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.beans.Employee;
import com.reactive.dao.CustomerRepository;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	public List<Employee> getAllEmployee() {
		List<Employee> allEmployee = null;
		long start = 0;
		long end = 0;

		start = System.currentTimeMillis();
		allEmployee = repo.getAllEmployee();
		end = System.currentTimeMillis();

		System.out.println("Total Execution Time -> " + (end - start) + " Milli Seconds" + " --> "
				+ ((end - start) / 1000) + " Seconds");
		return allEmployee;
	}

	public Flux<Employee> getAllEmpoyeesUsingFlux() {
		Flux<Employee> allEmployee = null;
		long start = 0;
		long end = 0;

		start = System.currentTimeMillis();
		allEmployee = repo.getAllEmployeeUsingFlux();
		end = System.currentTimeMillis();

		System.out.println("Total Execution Time -> " + (end - start) + " Milli Seconds" + " --> "
				+ ((end - start) / 1000) + " Seconds");
		return allEmployee;
	}
}
