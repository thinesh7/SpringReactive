package com.reactive.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(value = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column("productId")
	private Integer productId;
	@Column("productName")
	private String productName;
	@Column("productQuantity")
	private Integer productQuantity;
	@Column("productPrice")
	private Double productPrice;

	public Product(String productName, Integer productQuantity, Double productPrice) {
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
	}

}
