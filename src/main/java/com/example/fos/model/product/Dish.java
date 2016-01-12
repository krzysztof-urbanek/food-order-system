package com.example.fos.model.product;

import java.math.BigDecimal;

import com.example.fos.model.CourseType;
import com.example.fos.model.Cuisine;

public class Dish extends Product {
	private Cuisine cuisine;
	private CourseType courseType;
	
	public Dish(Cuisine cuisine, String name, CourseType courseType, BigDecimal price) {
		super(name, price);
		this.cuisine = cuisine;
		this.courseType = courseType;
	}
	
	//Getters, setters

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public Cuisine getCuisine() {
		return cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}
}
