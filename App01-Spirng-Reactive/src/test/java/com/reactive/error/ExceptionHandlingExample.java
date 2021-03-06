package com.reactive.error;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

public class ExceptionHandlingExample {

	// @Test
	public void errorExample() {

		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.concatWith(Flux.error(new Exception("Raise Exception"))).concatWithValues("E").log();

		StepVerifier.create(concatWithValues).expectNext("A", "B", "C", "D").expectError().verify();
	}

	// @Test
	public void HandleExceptionExampledoOnError() {

		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.concatWith(Flux.error(new Exception("Raise Exception")))
				.doOnError(i -> System.out.println(i.getMessage()));
		StepVerifier.create(concatWithValues.log()).expectNext("A", "B", "C", "D").expectError().verify();
		// Will return onError Event
	}

	// @Test
	public void HandleExceptionExampleOnErrorReturn() {

		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.concatWith(Flux.error(new RuntimeException("Raise Exception"))).onErrorReturn("Some Deafult Value")
				.concatWithValues("E");

		StepVerifier.create(concatWithValues.log()).expectNext("A", "B", "C", "D").expectNext("Some Deafult Value")
				.expectNext("E").verifyComplete();
		// Will not return onError Event and return the fallBack Value
	}

	// @Test
	public void HandleExceptionExampleOnErrorResume() {
		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.concatWith(Flux.error(new RuntimeException("Raise Exception")))
				.onErrorResume((i) -> Flux.just("E")) // expecting a function --> do a logic and return a Publisher
				.concatWithValues("F");

		StepVerifier.create(concatWithValues.log()).expectNext("A", "B", "C", "D").expectNext("E","F")
				.verifyComplete();
	}
	
	// @Test
	public void HandleExceptionExampleOnErrorMap() {
		
		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.concatWith(Flux.error(new RuntimeException("Raise Exception")))
				.onErrorMap((i) -> new IOException(i.getMessage()+" Custom Da..!")); // Map Run to Exp 

		StepVerifier.create(concatWithValues.log()).expectNext("A", "B", "C", "D")
				.expectError(IOException.class)
				.verify();
		
		StepVerifier.create(concatWithValues.log()).expectNext("A", "B", "C", "D")
				.expectErrorMessage("Raise Exception Custom Da..!")
				.verify(); 
	}
	
	// @Test
	public void HandleExceptionExampleOnFinally() {
		
		Flux<String> concatWithValues = Flux.just("A", "B", "C", "D")
				.delayElements(Duration.ofSeconds(1))
				.doFinally(i->{
					if(SignalType.ON_COMPLETE == i) {
						System.out.println("Performing Complete Opeation");
					}else if(SignalType.ON_ERROR == i){
						System.out.println("Performing Error Opeation");
					}else if(SignalType.CANCEL == i){
						System.out.println("Performing Cancel Opeation");
					}
				})
				.take(2);

		StepVerifier.create(concatWithValues.log()).expectNext("A", "B")
				.thenCancel()
				.verify();
	}
	
	@Test
	public void HandleExceptionDoOnError() {
		Mono.error(new Exception("Exp Da..!"))
			.doOnError(i->System.out.println("Handle-->"+i)) // Handle-->java.lang.Exception: Exp Da..!
			.log()
			.subscribe(); // java.lang.Exception: Exp Da..!
	}
}
