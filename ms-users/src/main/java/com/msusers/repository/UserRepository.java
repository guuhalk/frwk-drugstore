package com.msusers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msusers.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByCpf(String cpf);
	
	boolean existsByEmailOrCpf(String email, String cpf);
	
	@Query("SELECT count(*) FROM User u WHERE (u.email = :email OR u.cpf = :cpf) AND u.id != :idUser")
	int countByEmailOrCpfAndIdDiff(String email, String cpf, Long idUser);
	
}
