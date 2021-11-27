package com.reactive.dao;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Repository;

import com.reactive.beans.Employee;

import reactor.core.publisher.Flux;

@Repository
public class CustomerRepository {

	private static void makeCurrentThreadToSleep(int i) {
		try {
			Thread.sleep(1000);
			System.out.println("I Came inside..! --> " + i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 1. Using Traditional Programming:
	public List<Employee> getAllEmployee() {

		return IntStream.rangeClosed(1, 10).peek(CustomerRepository::makeCurrentThreadToSleep)
				.peek(i -> System.out.println("Normal Processing Count " + i)).mapToObj(i -> {
					System.out.println("Creating Employee Obj ->" + i + "\n");
					return new Employee(i, "Name-" + i, Double.valueOf(new Random().nextInt(100000)));
				}).collect(Collectors.toList());
	}

	// 2. Using Reactive Programming:
	public Flux<Employee> getAllEmployeeUsingFlux() {

		return Flux.range(1, 10)
				// .delayElements(Duration.ofSeconds(1))
				.doOnNext(CustomerRepository::makeCurrentThreadToSleep)
				.doOnNext(i -> System.out.println("Flux Processing Count in Stream flow" + i)).map(i -> {
					System.out.println("Creating Employee Obj ->" + i + "\n");
					return new Employee(i, "Name-" + i, Double.valueOf(new Random().nextInt(100000)));
				});
	}
}
