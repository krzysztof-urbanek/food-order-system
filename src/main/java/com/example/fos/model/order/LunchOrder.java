package com.example.fos.model.order;

import java.math.BigDecimal;

import com.example.fos.model.product.Dish;

public class LunchOrder extends Order {
	Dish mainCourse;
	Dish dessert;
	
	public LunchOrder(Dish mainCourse, Dish dessert) {
		this.mainCourse = mainCourse;
		this.dessert = dessert;
	}
	
	@Override
	public BigDecimal getPrice() {
		return mainCourse.getPrice().add(dessert.getPrice());
	}

}
