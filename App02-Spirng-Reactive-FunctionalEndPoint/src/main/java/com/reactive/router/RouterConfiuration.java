package com.reactive.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.reactive.handler.CustomerHandler;

@Configuration
public class RouterConfiuration {

	@Autowired
	private CustomerHandler handler;

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
				.GET("/api/getCustomers", i -> handler.streamAllEmployees(i))
				.GET("/api/getCustomers/{id}", handler::getEmployeeById)
				.POST("/api/saveCustomer",handler::saveAnEmployee)
				.build();
	}
}
