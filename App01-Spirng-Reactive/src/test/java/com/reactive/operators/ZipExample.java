package com.reactive.operators;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

public class ZipExample {

	//@Test
	public void zipTest() {

		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

		Flux<Tuple2<String, String>> mergedZip = Flux.zip(flux1, flux2);

		mergedZip.subscribe(i -> System.out.println(i));

		mergedZip.map(i -> i.getT1() + i.getT2()).subscribe(i -> System.out.println("Tuple -> " + i));

		StepVerifier.create(mergedZip.log()).expectNextCount(3).verifyComplete();
	}

	@Test
	public void zipWithTest() {

		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

		Flux<Tuple2<String, String>> mergedZip = flux1.zipWith(flux2);

		mergedZip.subscribe(i -> System.out.println(i));

		mergedZip.map(i -> i.getT1() + i.getT2()).subscribe(i -> System.out.println("Tuple -> " + i));

		StepVerifier.create(mergedZip.log()).expectNextCount(3).verifyComplete();
	}
}
