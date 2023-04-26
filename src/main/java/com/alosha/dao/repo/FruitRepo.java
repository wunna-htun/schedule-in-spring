package com.alosha.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alosha.dao.entities.Fruit;

public interface FruitRepo extends CrudRepository<Fruit, Long> {

	public long countByFruitName(String fruitName);

	@Query(value = "select distinct fruit_name from Fruit",nativeQuery = true)
	public List<String> findUniqueFruitName();
	
	
	
}
