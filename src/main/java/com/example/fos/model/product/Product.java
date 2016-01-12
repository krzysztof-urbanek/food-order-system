package com.example.fos.model.product;

import java.math.BigDecimal;

public class Product {
	protected BigDecimal price;
	protected String name;
	
	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}
	
	//Getters, setters
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
