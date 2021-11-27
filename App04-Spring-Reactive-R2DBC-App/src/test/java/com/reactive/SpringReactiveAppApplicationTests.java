package com.reactive;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.controller.ProductsController;
import com.reactive.entities.Product;

import reactor.core.publisher.Flux;

@WebFluxTest(ProductsController.class)
class SpringReactiveAppApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ReactiveCrudRepository<Product, Integer> repo;

	@Test
	public void getAllProducts() {

		Flux<Product> allProducts = Flux.just((new Product(1, "P-1", 5, 60.0)), (new Product(2, "P-2", 55, 90.0)));

		Mockito.when(repo.findAll()).thenReturn(allProducts);

		Flux<Product> resultProducts = webTestClient.get().uri("/products").exchange().expectStatus().isOk()
				.returnResult(Product.class).getResponseBody();

		resultProducts.subscribe(i -> System.out.println(i));
	}

}
