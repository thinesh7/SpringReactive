package com.bms.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.bms.handler.BMSHandler;

@Configuration
public class BMSRouter {

	@Autowired
	private BMSHandler handler;

	@Bean
	public RouterFunction<ServerResponse> bmsRouterFunction() {
		return RouterFunctions.route()
				.POST("/saveCustomer", handler::createACustomer)
				.build();
	}
}
