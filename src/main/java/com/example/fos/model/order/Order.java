package com.example.fos.model.order;

import java.math.BigDecimal;

public abstract class Order {
	private boolean active = true;
	
	public abstract BigDecimal getPrice();
	
	public void setInactive() {
		active = false;
	}
	
	public void setActive() {
		active = true;
	}
	
	public boolean isActive() {
		return active;
	}
}
