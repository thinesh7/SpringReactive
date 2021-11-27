package com.reactive.reactor_thread_model;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class ZPractice {
	
	 @Test
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
	
}
