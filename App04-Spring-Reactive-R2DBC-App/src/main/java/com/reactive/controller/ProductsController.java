package com.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reactive.entities.Product;
import com.reactive.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductsController{

	@Autowired
	private ProductRepository repository;

	@GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Product> getAllProducts() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Product> getProductById(@PathVariable("id") Integer productId) {
		return repository.findById(productId);
	}

	@PostMapping(value = "/saveProd", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Product> saveProduct(@RequestBody Product product) {
		return repository.save(product);
	}

	@PutMapping(value = "/updateProd/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Product> updateProduct(@PathVariable("id") Integer ProductId, @RequestBody Product product) {
		Mono<Product> updatedProduct = repository.findById(ProductId).map(oldProduct -> {
			product.setProductId(oldProduct.getProductId());
			return product;
		}).flatMap(prod -> repository.save(prod));
		return updatedProduct;
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Void> deleteProduct(@PathVariable("id") Integer productId) {
		Mono<Void> map = repository.deleteById(productId).flatMap(product -> repository.deleteById(productId));
		return map;
	}

}
