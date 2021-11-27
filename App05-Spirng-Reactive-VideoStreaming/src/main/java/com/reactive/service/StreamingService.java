package com.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {
	
	//public static final String FORMAT = "G:/%s.mp4";

	public static final String FORMAT = "classpath:videos/%s.mp4";
	
	@Autowired
	private ResourceLoader resourceLoader;

	public Mono<Resource> getVideo(String titile) {
		System.out.println(String.format(FORMAT, titile));
		Mono<Resource> result = Mono.fromSupplier(() -> resourceLoader.getResource(String.format(FORMAT, titile)));
		return result;
	}
}
