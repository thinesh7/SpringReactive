package com.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reactive.service.StreamingService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ThineshStreamingController {

	@Autowired
	private StreamingService service;

	@GetMapping(value = "/video/{title}", produces = "video/mp4")
	public Mono<Resource> getVideos(@PathVariable("title") String title, @RequestHeader("Range") String range) {
		System.out.println("Range in Bytes --> "+range);
		return service.getVideo(title);
	}
}
