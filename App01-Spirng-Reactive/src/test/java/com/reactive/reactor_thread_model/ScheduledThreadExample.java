package com.reactive.reactor_thread_model;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ScheduledThreadExample {

	// @Test
	public void schedulepublishOnThreadModel() {
		Mono.just("1")
		//	.publishOn(Schedulers.elastic()) --> Thread Name: elastic-2
		//	.publishOn(Schedulers.immediate()) --> Thread Name: elastic-2
			.publishOn(Schedulers.boundedElastic()) // --> Thread Name: boundedElastic-1
			.map(i -> {
					log("map", i);
					return Integer.parseInt(i);
				})
			.log().subscribe(System.out::println);
	}
	
	// @Test
	public void scheduleSubscribeOnThreadModel() throws InterruptedException {
		Mono.just("1").log()
			.map(i -> {
				log("map", i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return Integer.parseInt(i);
			})
			.subscribeOn(Schedulers.single())
			.map(i -> {
				log("map", i*2);
				return i*2;
			})
			.subscribe(System.out::println);
		
		Thread.sleep(2000);
	}

	@Test
	public void scheduleRunOnThreadModel() throws InterruptedException {
		
		long start = System.currentTimeMillis();
		
		System.out.println(" Number of CPU Cores: "+Runtime.getRuntime().availableProcessors());
		System.out.println(Thread.currentThread().getName());
		Flux.range(1,1000).parallel()
						.runOn(Schedulers.parallel())
						.map(i -> {
							//log("map", i);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return i;
						})
						.subscribe(i->System.out.println("Subscribed Data --> "+i+" Thread "+Thread.currentThread().getName()));
		Thread.sleep(4000);
		long end = System.currentTimeMillis();
		
		System.out.println("Total Time ->> "+(end-start)/1000);
	}
	
	private void log(String name, Object data) {
		System.out.println(
				"Inside Operator " + name + " Data is " + data.toString() + " Thread Name: " + Thread.currentThread().getName());
	}
}
