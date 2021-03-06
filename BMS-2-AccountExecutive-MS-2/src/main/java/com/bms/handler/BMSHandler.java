package com.bms.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bms.dto.CustomerDTO;
import com.bms.helper.BMSHelper;
import com.bms.repository.CustomerRepository;

import reactor.core.publisher.Mono;

@Service
public class BMSHandler {

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private BMSHelper helper;

	public Mono<ServerResponse> createACustomer(ServerRequest request) {
		Mono<Object> res = request.bodyToMono(CustomerDTO.class).flatMap(i -> helper.createCustomer(i));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(res, Object.class);
	}
}
