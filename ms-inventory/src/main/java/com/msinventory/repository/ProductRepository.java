package com.msinventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.msinventory.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	boolean existsBySku(String sku);
	
	@Query(value =  "{ $and: [ { 'id': { $ne: ?1 } }, { 'sku': ?0 } ] }", count = true)
	long countBySkuAndIdDiff(String sku, String id);
}
