package com.reactive.operators;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ConcatTest {

	@Test
	public void mergeTest() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

		Flux<String> mergedFlux = Flux.concat(flux1, flux2).log();

		mergedFlux.subscribe(i -> System.out.println(i));

		StepVerifier.create(mergedFlux.log()).expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
		// Order will be Same -- > but take more time

	}

}
