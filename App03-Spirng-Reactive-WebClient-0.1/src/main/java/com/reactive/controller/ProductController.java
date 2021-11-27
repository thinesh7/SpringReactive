package com.reactive.controller;

import javax.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.reactive.dto.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductController {

	private WebClient webClient;

	@PostConstruct
	public void init() {
		webClient = WebClient.builder().baseUrl("http://localhost:9090/api")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}

	@GetMapping(value = "/getProd", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDTO> getAllProducts() {
		Flux<ProductDTO> productDto = webClient.get().uri("/getProducts").retrieve().bodyToFlux(ProductDTO.class);
		return productDto;
	} // http://localhost:8085/api/getProd

	@GetMapping(value = "/getProd/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ProductDTO> getProductById(@PathVariable("id") String productId) {
		Mono<ProductDTO> ProductDto = webClient.get().uri("/getProducts/" + productId)
				.accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(ProductDTO.class).next();
		return ProductDto;
	}

	@PostMapping(value = "/saveProd", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ProductDTO> saveProduct(@RequestBody ProductDTO productDto) {
		return webClient.post().uri("/addProduct").body(BodyInserters.fromValue(productDto)).retrieve()
				.bodyToFlux(ProductDTO.class).next();
	}

	@PutMapping(value = "/updateProd/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ProductDTO> updateProduct(@RequestBody ProductDTO productDto, @PathVariable("id") String productId) {
		return webClient.put().uri("/updateProduct/" + productId).body(BodyInserters.fromValue(productDto)).retrieve()
				.bodyToFlux(ProductDTO.class).next();
	}

	@DeleteMapping(value = "/deleteProd/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<String> deleteProduct(@PathVariable("id") String productId) {
		return webClient.delete().uri("/deleteProduct/" + productId).retrieve().bodyToFlux(String.class).next();
	}

}