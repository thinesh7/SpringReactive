package com.reactive.operators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FlatMapTest {

	// @Test
	public void flatMapTest() {
		List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6);
		Flux<String> flatMap = Flux.fromIterable(asList).flatMap(i -> getObj(i)).log(); // Asynchronous Operation
		
		StepVerifier.create(flatMap).expectNextCount(6).verifyComplete();
	}

	// @Test // Make the same with less time but no sequence
	public void flatMapUsingParallelScheduler() {
		List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6);
		Flux<String> flatMap = Flux.fromIterable(asList)
				.window(2)
				.flatMap(i -> i.flatMap(j -> getObj(j)).subscribeOn(Schedulers.parallel()))
				.log();
				
		StepVerifier.create(flatMap).expectNextCount(6).verifyComplete();
	}
	
    @Test // Make the same less time with sequence -- > Best
	public void flatMapSequence() {
		List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6);
		Flux<String> flatMap = Flux.fromIterable(asList)
				.window(2)
				.flatMapSequential(i -> i.flatMap(j -> getObj(j)).subscribeOn(Schedulers.parallel()))
				.log();
				
		StepVerifier.create(flatMap).expectNextCount(6).verifyComplete();
	}

	public Mono<String> getObj(int input) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		System.out.println(Thread.currentThread().getName());
		
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.put(4, "four");
		map.put(5, "five");
		map.put(6, "six");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Flux.fromIterable(map.entrySet()).filter(i -> i.getKey() == input).single().map(i -> i.getValue());
		// single , next , last
	}

}
