package com.reactive;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.dto.ProductDTO;
import com.reactive.handler.ProductsHandler;
import com.reactive.router.RouterConfiguration;

import reactor.core.publisher.Flux;

@WebFluxTest(RouterConfiguration.class)
class SpirngReactiveApplicationTests {

	// Inject Web client Call to do API calls:
	@Autowired
	private WebTestClient webTestClient;

	// Inject Handler class to mock the service layer:
	@MockBean
	private ProductsHandler service;

	@Test
	public void contextLoads() {
		Flux<ProductDTO> allProducts = Flux.just((new ProductDTO("P001", "P-1", 5, 60.0)),
				(new ProductDTO("P002", "P-2", 55, 90.0)));

		Mockito.when(service.getAllProducts(Mockito.any()))
						.thenReturn(ServerResponse.ok()
						.body(allProducts, ProductDTO.class));
		
		webTestClient.get()
					 .uri("/api/getProducts")
					 .exchange()
					 .expectStatus().isOk();
		
	}

}
