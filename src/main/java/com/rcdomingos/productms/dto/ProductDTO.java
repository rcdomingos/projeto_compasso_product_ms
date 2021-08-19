package com.rcdomingos.productms.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rcdomingos.productms.entities.Product;

public class ProductDTO {
	
	@NotNull
	@NotBlank
	private String name;
	
	@NotNull
	@NotBlank
	private String description;

	@Min(0)
	@Max(Long.MAX_VALUE)
	@NotNull
	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Product toProduct() {
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);

		return product;
	}

}