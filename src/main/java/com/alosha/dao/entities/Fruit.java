package com.alosha.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fruit {

	@Id
	@GeneratedValue
	private long fruitId;
	
	private String fruitName;
	
	public long getFruitId() {
		return fruitId;
	}
	public void setFruitId(long fruitId) {
		this.fruitId = fruitId;
	}
	public String getFruitName() {
		return fruitName;
	}
	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}
	


	
}
