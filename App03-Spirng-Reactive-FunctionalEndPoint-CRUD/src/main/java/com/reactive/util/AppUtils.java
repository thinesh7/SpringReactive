package com.reactive.util;

import org.springframework.beans.BeanUtils;
import com.reactive.dto.ProductDTO;
import com.reactive.entities.Product;

public class AppUtils {

	public static Product changeDTOtoEntity(ProductDTO productDTO) {
		Product product = new Product();
		BeanUtils.copyProperties(productDTO, product); // Source --> Destination
		return product;
	}

	public static ProductDTO changeEntityToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		BeanUtils.copyProperties(product, productDTO);
		return productDTO;
	}
}
