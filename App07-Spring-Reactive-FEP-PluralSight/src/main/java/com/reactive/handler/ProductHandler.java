package com.reactive.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.reactive.dto.ProductDTO;
import com.reactive.entities.Product;
import com.reactive.repository.ProductRepository;
import com.reactive.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductHandler {

	@Autowired
	private ProductRepository repository;

	public Mono<ServerResponse> getAllProducts(ServerRequest request) {
		Flux<ProductDTO> resultProductsDto = repository.findAll()
				.map(product -> (new AppUtils<Product, ProductDTO>()).entityToDto(product, new ProductDTO()));
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(resultProductsDto, ProductDTO.class);
	}

	public Mono<ServerResponse> getProductById(ServerRequest request) {

		String productId = request.pathVariable("id");

		Mono<ProductDTO> resultProductDto = repository.findById(productId)
				.map(product -> (new AppUtils<Product, ProductDTO>()).entityToDto(product, new ProductDTO()));

		Mono<ServerResponse> notFound = ServerResponse.notFound().build();

		return resultProductDto.flatMap(productDto -> {
			return ServerResponse.ok().contentType(APPLICATION_JSON).body(productDto, ProductDTO.class);
		}).switchIfEmpty(notFound);
	}

}
