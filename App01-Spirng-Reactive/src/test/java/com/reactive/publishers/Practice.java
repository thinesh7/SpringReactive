package com.reactive.publishers;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Practice {

	// @Test
	public void test1() throws InterruptedException {
		Flux.just("A", "B", "C", "D", "E", "F", "X", "Y", "Z")
		.doOnNext(i -> System.out.println("Data main --> " + i + " " + Thread.currentThread().getName()))
		.delayElements(Duration.ofSeconds(1))
		.doOnNext(i->System.out.println("Data 1 --> " + i + " " + Thread.currentThread().getName()))
		.subscribe();

		Thread.sleep(5000);
		System.out.println("Done");
	}
	
	// @Test
	public void test2() throws InterruptedException {
		Flux.just("A", "B", "C", "D")
		.doOnNext(i -> System.out.println("Data main --> " + i + " " + Thread.currentThread().getName())) // --> Main
		.delayElements(Duration.ofSeconds(1))
		.doOnNext(i->System.out.println("Data 1 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
		.delayElements(Duration.ofSeconds(1))
		.doOnNext(i->System.out.println("Data 2 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
		.subscribe();

		Thread.sleep(10000);
		System.out.println("Done");
	}
	
	// @Test
	public void test3() throws InterruptedException {
		
		Mono<Integer> reduce = Flux.just(1,2,3,4).reduce(0, (a,b)-> a+b);
		
		reduce
			.doOnNext(i -> System.out.println("Data main --> " + i + " " + Thread.currentThread().getName())) // --> Main
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 1 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 2 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.subscribe();

		Thread.sleep(4000);
		System.out.println("Done");
	}
	
	// @Test
	public void test4() throws InterruptedException {
		
		// Mono<Integer> reduce = Flux.range(5, 2).reduce(0, (a,b)-> a+b);  5 and 6 --> 11
		
		Mono<Integer> reduce = Flux.range(1, 5).reduce(0, (a,b)-> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			return a+b;
		});
		
		reduce
			.doOnNext(i -> System.out.println("Data main --> " + i + " " + Thread.currentThread().getName())) // --> Main
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 1 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 2 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.subscribe();

		Thread.sleep(4000);
		System.out.println("Done");
	}
	
	@Test
	public void test5() throws InterruptedException {
		
		Mono<Integer> reduce = Flux.range(1, 5).reduce(0, (a,b)-> {
			return a+b;
		});
		
		reduce
			.doOnNext(i -> System.out.println("Data main --> " + i + " " + Thread.currentThread().getName())) // --> Main
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 1 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.delayElement(Duration.ofSeconds(1))
			.doOnNext(i->System.out.println("Data 2 --> " + i + " " + Thread.currentThread().getName())) // Concurrent
			.subscribe();

		Thread.sleep(4000);
		System.out.println("Done");
	}
}
