package com.reactive.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.reactive.dto.ProductDTO;
import com.reactive.entities.Product;
import com.reactive.repository.ProductRepository;
import com.reactive.util.AppUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductsHandler {

	@Autowired
	private ProductRepository repository;

	// 1. Get --> All Products:
	public Mono<ServerResponse> getAllProducts(ServerRequest request) {
		Flux<ProductDTO> allProducts = repository.findAll().map(AppUtils::changeEntityToDTO).log();
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(allProducts, ProductDTO.class);
	}

	// 2. GET --> Get a Product by Product Id:
	public Mono<ServerResponse> getAProductById(ServerRequest request) {
		String productId = request.pathVariable("id");
		Mono<ProductDTO> productDto = repository.findById(productId).map((i -> {
			return AppUtils.changeEntityToDTO(i);
		})).log();
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(productDto, ProductDTO.class);
	}

	// 3. POST --> POST a Product into the DB:
	public Mono<ServerResponse> saveProduct(ServerRequest request) {
		Mono<ProductDTO> productDTOmono = request.bodyToMono(ProductDTO.class);
		Mono<ProductDTO> resultProductDTO = productDTOmono.map(i -> AppUtils.changeDTOtoEntity(i)).flatMap(i -> {
			Mono<Product> insertedData = repository.insert(i);
			return insertedData;
		}).map(AppUtils::changeEntityToDTO);
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(resultProductDTO, ProductDTO.class);
	}

	// 4. POST --> UPDATE a Product:
	public Mono<ServerResponse> updateProduct(ServerRequest request) {

		String productId = request.pathVariable("id");
		Mono<Product> product = request.bodyToMono(ProductDTO.class).map(AppUtils::changeDTOtoEntity);

		Mono<ProductDTO> productDTO = repository.findById(productId).flatMap(i -> product)
				.doOnNext(prod -> prod.setProductId(productId)).flatMap(i -> repository.save(i))
				.map(AppUtils::changeEntityToDTO);

		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(productDTO, ProductDTO.class);
	}

	// 5. DELETE --> DELETE a Product:
	public Mono<ServerResponse> deleteProduct(ServerRequest request) {
		String productId = request.pathVariable("id");
		Mono<Product> res = repository.findById(productId).doOnNext(i -> {
			repository.delete(i).log().subscribe();
		}).log();
		res.subscribe();
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Mono.just("Done"), String.class);
	}

	// 6. Get - getProductsByPrice Between a Range:
	public Mono<ServerResponse> getProductsByPrice(ServerRequest request) {
		double min = Double.parseDouble(request.queryParam("min").get());
		double max = Double.parseDouble(request.queryParam("max").get());

		System.out.println(min + " " + max);

		Flux<ProductDTO> productsDto = repository.findByProductPriceBetween(Range.closed(min, max))
				.map(AppUtils::changeEntityToDTO);
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(productsDto, ProductDTO.class);
	}

	// URL: http://localhost:9090/api/getProductsInRange?min=8&max=50

	// https://www.bezkoder.com/reactor-flux-list-map/
	// https://www.baeldung.com/java-string-from-mono Mono<String> just =
	// Mono.just("Hello world!"); just.block()
}
