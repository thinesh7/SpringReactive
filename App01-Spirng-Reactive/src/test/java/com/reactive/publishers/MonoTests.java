package com.reactive.publishers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class MonoTests {

	// Mono used to Emit 0 to 1 elements

	// @Test
	void monoTest() {
		Mono<String> mono = Mono.just("Thinesh..!").log();
		mono.subscribe((data) -> System.out.println(data));
	}

	// @Test
	void monoWithException() {
		Mono<?> mono = Mono.just("Thinesh..!").then(Mono.error(new Exception("Dai Exp..!"))).log()
				.doOnError(error -> System.out.println("1. Exp --> " + error.getMessage()));
		mono.subscribe((data) -> System.out.println(data),
				(error -> System.out.println("2. Exp --> " + error.getMessage()))); // --> Throw Exception
	}

	@Test
	void switchExample() {
		Mono.just("").filter(i -> i != "")
					.switchIfEmpty(Mono.just("B"))
					.subscribe((data) -> System.out.println(data));
	}

}
