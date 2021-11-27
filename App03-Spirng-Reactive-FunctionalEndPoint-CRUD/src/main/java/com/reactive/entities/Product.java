package com.reactive.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "products")
public class Product {

	@Id
	private String productId;
	private String productName;
	private Integer productQuantity;
	private Double productPrice;

}
