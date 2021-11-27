package com.reactive.publishers;

import java.time.Duration;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxTests {

	// Flux used to Emit 0 to N elements

	// @Test
	public void fluxTest() {
		Flux<String> fluxStrings = Flux.just("AAA", "BBB", "CCC", "DDD", "EEE", "FFF").concatWithValues("GGG").log();
		fluxStrings.subscribe(res -> System.out.println(res));
	}

	// @Test
	public void fluxWithException() {
		Flux<String> fluxStrings = Flux.just("AAA", "BBB", "CCC", "DDD", "EEE", "FFF").concatWithValues("GGG")
				// .concatWith(Flux.error(new Exception("Exp daa..!")))
				.concatWithValues("ZZZ").log();
		// ZZZ wont add --? bcz for exp
		fluxStrings.subscribe((res -> System.out.println(res)),
				(error -> System.out.println("Error :" + error.getMessage())),
				(() -> System.out.println("Completed da ..!")));

	}

	// @Test
	public void fluxWithIterable() {
		Iterable<String> asIterable = Arrays.asList("A", "AA", "AAA", "AAAA");
		Flux<String> fluxStrings = Flux.fromIterable(asIterable).log();
		fluxStrings.subscribe((i) -> System.out.println(i));
	}

	// @Test
	public void fluxWithRange() {
		Flux<Integer> fluxStrings = Flux.range(5, 6); // --? Form 5 count 6 numbers --> 5 , 6 , 7 , 8 , 9 , 10
		fluxStrings.subscribe((i) -> System.out.println(i));
	}

	@Test
	public void fluxWithInterval() throws InterruptedException {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		interval.log().subscribe(i -> {
			System.out.println(Thread.currentThread().getName());
		}); // Main Thread --> is done its work and cancel --> so it is not printing anything
		Thread.sleep(10000); // --> Now this executes in parallel-1 thread so we can see the onNext call:
		// wont run incomplete element coz it is the sequence of infinite elements
	}

	// @Test
	public void fluxWithTake() throws InterruptedException {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		interval.log().take(3) // --> this will take only 3 items and then cancel the request
				.subscribe((i) -> System.out.println(i));
		Thread.sleep(10000);
	}

	@SuppressWarnings("deprecation")
	// @Test
	public void fluxWithRequest() throws InterruptedException {

		Flux.range(1, 10).log().subscribe(((i) -> System.out.println(i)), ((i) -> System.out.println(i)), (() -> {
		}), (sub -> sub.request(3))); // --> Wont Give On COmplete bcz still it is having items to emit
	}

	// @Test
	public void fluxWithErrorHandling() throws InterruptedException {
		Flux.just("A", "AA", "AAA").log().concatWith(Flux.error(new Exception("Exp Da..!"))).concatWithValues("BBB") // Wont
																														// execute
				.onErrorReturn("Handling Exp..!") // Error will be handled here
				.concatWithValues("CCC").subscribe(i -> System.out.println(i)); // Even after error --- we got on
																				// complete event coz we handled the
																				// exception
	}

}
