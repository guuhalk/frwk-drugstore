package com.msinventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.msinventory.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

	boolean existsByName(String name);
	
	@Query(value =  "{ $and: [ { 'id': { $ne: ?1 } }, { 'name': ?0 } ] }", count = true)
	long countByNameAndIdDiff(String name, String id);
}
