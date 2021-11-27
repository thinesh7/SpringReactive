package com.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.reactive.entities.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
	
}
