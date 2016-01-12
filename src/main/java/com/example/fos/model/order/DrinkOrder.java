package com.example.fos.model.order;

import java.math.BigDecimal;

import com.example.fos.model.product.Drink;

public class DrinkOrder extends Order {
	Drink drink;
	boolean iceCubes;
	boolean lemon;
	
	public DrinkOrder(Drink drink, boolean iceCubes, boolean lemon) {
		this.drink = drink;
		this.iceCubes = iceCubes;
		this.lemon = lemon;
	}

	@Override	
	public BigDecimal getPrice() {
		return drink.getPrice();
	}
}
