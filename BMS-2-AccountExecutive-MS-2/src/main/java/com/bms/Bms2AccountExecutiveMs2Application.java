package com.bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Bms2AccountExecutiveMs2Application {

	public static void main(String[] args) {
		SpringApplication.run(Bms2AccountExecutiveMs2Application.class, args);
	}

}
