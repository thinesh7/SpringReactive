package com.reactive.controller;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.entities.Product;
import com.reactive.entities.ProductEvent;
import com.reactive.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

	// http://localhost:8080/products

	@Autowired
	private ProductRepository repository;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> getAllProducts() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Product>> getProductsById(@PathVariable("id") String prodId) {
		return repository.findById(prodId).map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> saveProduct(@RequestBody Product product) {
		return repository.save(product);
	}

	@PutMapping(value = "/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String prodId,
			@RequestBody Product product) {

		Mono<ResponseEntity<Product>> response = repository.findById(prodId).flatMap(existingProduct -> {
			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductPrice(product.getProductPrice());
			return repository.save(existingProduct);
		}).map(prod -> ResponseEntity.ok(prod)).defaultIfEmpty(ResponseEntity.notFound().build());

		return response;
	}

	@DeleteMapping(value = "/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String prodId) {

		Mono<ResponseEntity<Void>> result = repository.findById(prodId).flatMap(existingProduct -> {
			return repository.delete(existingProduct)
					.then(Mono.just(ResponseEntity.ok().<Void>build())); // Return Void (Imp)
		}).defaultIfEmpty(ResponseEntity.notFound().build());

		return result;
	}

	@DeleteMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<String>> deleteAllProducts() {
		return repository.deleteAll().then(Mono.just(ResponseEntity.ok("All Products Deleted")));
	}
	
	@DeleteMapping(value = "/del",produces = MediaType.TEXT_EVENT_STREAM_VALUE )
	public Mono<ResponseEntity<String>> deleteAllProductsCheck() {
		Mono<ResponseEntity<String>> result = repository.findAll().next()
							.flatMap(product -> repository.delete(product))
							.map(i-> ResponseEntity.ok("All Deleted"))
							.defaultIfEmpty(ResponseEntity.ok("Sorry No Products are there to be deleted"));
		return result;
	}
	
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductEvent> streamEvent(){
		
		return Flux.interval(Duration.ofSeconds(1))
				   .map(i-> new ProductEvent(i,"Product Event"))
				   .take(5)
				   .log();
	}
}
