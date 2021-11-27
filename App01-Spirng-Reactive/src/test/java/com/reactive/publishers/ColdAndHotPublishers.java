package com.reactive.publishers;

import java.time.Duration;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdAndHotPublishers {

	@Test
	public void coldPublisher() throws InterruptedException {

		long start = System.currentTimeMillis();

		Flux<String> flux = Flux.just("A", "B", "C", "D", "E").delayElements(Duration.ofSeconds(1));

		flux.subscribe(i -> System.out
				.println("Subscriber - 1 Emitting --> 1->" + i + " " + Thread.currentThread().getName()));
		Thread.sleep(3000); // Subscriber - 1 - A, B, C, D, E --> Will start Emit data from beginning

		flux.subscribe(i -> System.out
				.println("Subscriber - 2 Emitting --> 2->" + i + " " + Thread.currentThread().getName()));
		Thread.sleep(6000); // Subscriber - 1 - A, B, C, D, E --> Will start Emit data from beginning

		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000.0);
	}

	// @Test
	public void hotPublisher() throws InterruptedException {

		long start = System.currentTimeMillis();

		Flux<String> flux = Flux.just("A", "B", "C", "D", "E").delayElements(Duration.ofSeconds(1));

		ConnectableFlux<String> connectableFlux = flux.publish();
		connectableFlux.connect(); // Connect to source

		connectableFlux.subscribe(i -> System.out
				.println("Subscriber - 1 Emitting --> 1->" + i + " " + Thread.currentThread().getName()));
		Thread.sleep(3000); // A, B, C, D, E

		connectableFlux.subscribe(i -> System.out
				.println("Subscriber - 2 Emitting --> 2->" + i + " " + Thread.currentThread().getName()));
		Thread.sleep(6000); // D, E

		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0); // --? 9.376
	}

}
