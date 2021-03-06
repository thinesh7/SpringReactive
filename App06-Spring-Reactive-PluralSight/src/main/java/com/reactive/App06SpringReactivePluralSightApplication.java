package com.reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.reactive.entities.Product;
import com.reactive.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class App06SpringReactivePluralSightApplication {

	public static void main(String[] args) {
		SpringApplication.run(App06SpringReactivePluralSightApplication.class, args);
	}

	// @Bean
	public CommandLineRunner init(ProductRepository repository, ReactiveMongoOperations operations) {
		return (i -> {

			System.out.println("-->" + i);

			Flux<Product> productFlux = Flux.just(new Product(null, "PROD-1", 20.56d),
					new Product(null, "PROD-2", 25.56d),
					new Product(null, "PROD-3", 30.56d))
					.flatMap(prod -> repository.save(prod));
			
			productFlux.thenMany(repository.findAll()).subscribe(System.out::println);
			
			operations.collectionExists(Product.class)
					  .flatMap(exists-> exists ? operations.dropCollection(Product.class): Mono.just(exists))
					  .thenMany(prod->operations.createCollection(Product.class))
					  .thenMany(productFlux)
					  .thenMany(repository.findAll())
					  .subscribe(System.out::println);
			});
	}
}
