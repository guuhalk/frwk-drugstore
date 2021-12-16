package com.msuser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msuser.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
	
	@Query("SELECT count(*) FROM User u WHERE u.email = :email AND u.id != :idUser")
	int countByEmailAndIdDiff(String email, Long idUser);
	
	boolean existsByCpf(String cpf);
	
	@Query("SELECT count(*) FROM User u WHERE u.cpf = :cpf AND u.id != :idUser")
	int countByCpfAndIdDiff(String cpf, Long idUser);
		
	Optional<User> findFirstByCpf(String cpf);
	
	List<User> findByCpf(String cpf);
	
	
}
