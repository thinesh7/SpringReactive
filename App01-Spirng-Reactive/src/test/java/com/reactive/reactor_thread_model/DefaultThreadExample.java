package com.reactive.reactor_thread_model;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

public class DefaultThreadExample {

	// @Test
	public void defaultThreadModel() {
		
		Mono.just("1")
		.map(i -> {
			log("map", i);
			return Integer.parseInt(i);
		})
		.log().subscribe(System.out::println);
	} // By default uses main thread --> will print

	
	@Test
	public void timeOperator() throws InterruptedException {
		Mono.just("1") 
		.delayElement(Duration.ofSeconds(1)) // Delay of 1 second
		.map(i -> {
			log("MAP", i);
			return Integer.parseInt(i);
		})
		.log().subscribe(System.out::println);
		
		Thread.sleep(2000); // --> After giving sleep only we can see the print line
		// Inside Operator MAP Data is 1 Thread Name: parallel-1
	}

	private void log(String name, String data) {
		System.out.println(
				"Inside Operator " + name + " Data is " + data + " Thread Name: " + Thread.currentThread().getName());
	}

}
