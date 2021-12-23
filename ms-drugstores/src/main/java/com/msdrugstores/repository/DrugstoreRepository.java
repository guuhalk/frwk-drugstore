package com.msdrugstores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msdrugstores.model.Drugstore;

@Repository
public interface DrugstoreRepository extends JpaRepository<Drugstore, Long> {

	boolean existsByCnpj(String cnpj);
	
	@Query("SELECT count(*) FROM Drugstore d WHERE d.cnpj = :cnpj AND d.id != :id")
	int countByCnpjAndIdDiff(String cnpj, Long id);
	
	@Query("SELECT distinct(d) FROM Drugstore d LEFT JOIN FETCH d.phones")
	List<Drugstore> findAll();

	@Query("FROM Drugstore d LEFT JOIN FETCH d.phones where d.id = :id")
	Optional<Drugstore> findById(Long id);
}
