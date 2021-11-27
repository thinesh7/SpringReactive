package com.reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.reactive.entities.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
