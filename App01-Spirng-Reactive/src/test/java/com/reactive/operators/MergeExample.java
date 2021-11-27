package com.reactive.operators;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class MergeExample {

	@Test
	public void mergeTest() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(1));

		Flux<String> mergedFlux = Flux.merge(flux1, flux2);

		StepVerifier.create(mergedFlux.log()).expectNextCount(6).verifyComplete();
		// Order will be different

	}

}
