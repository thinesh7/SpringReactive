package com.reactive.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.reactive.handler.ProductsHandler;

@Configuration
public class RouterConfiguration {

	@Autowired
	private ProductsHandler handler;

	// URL: http://localhost:9090/api/getProducts

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
				.GET("/api/getProducts", handler::getAllProducts)
				.GET("/api/getProducts/{id}", handler::getAProductById)
				.POST("/api/addProduct", handler::saveProduct)
				.PUT("/api/updateProduct/{id}", handler::updateProduct)
				.DELETE("/api/deleteProduct/{id}", handler::deleteProduct)
				.GET("/api/getProductsInRange",handler::getProductsByPrice)
				.build();
	}

}
