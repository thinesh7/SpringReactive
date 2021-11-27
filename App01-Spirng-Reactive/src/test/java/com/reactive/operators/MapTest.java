package com.reactive.operators;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

public class MapTest {

	@Test
	public void testMap() {

		Flux.just(1, 2, 3, 4, 5, 6).log().map(i -> {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i + " Aiyo " + i;
		}).subscribe(i -> System.out.println(i));

	}
}
