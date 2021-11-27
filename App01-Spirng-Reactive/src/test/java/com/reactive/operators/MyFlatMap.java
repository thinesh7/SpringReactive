package com.reactive.operators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MyFlatMap {

	@Test
	public void flatMapTest() {
		
		List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6);
		Flux<String> myFlatmap = Flux.fromIterable(asList).map(i -> getObj(i).block());
		myFlatmap.subscribe(i -> System.out.println(i));

		// StepVerifier.create(myFlatmap).expectNextCount(6).verifyComplete();
	}

	public Mono<String> getObj(int input) {
		Map<Integer, String> map = new HashMap<Integer, String>();
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
