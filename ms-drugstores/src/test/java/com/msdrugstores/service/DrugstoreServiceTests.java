package com.msdrugstores.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.msdrugstores.model.Drugstore;
import com.msschemas.dto.DrugstoreDTO;
import com.msschemas.exception.EntityAlreadyExistsException;
import com.msschemas.exception.EntityNotFoundException;

@DisplayName("DrugstoreServiceTests")
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class DrugstoreServiceTests {

	@Autowired
	private Flyway flyway;
	
	@Autowired
	private DrugstoreService drugstoreService;
	
	@BeforeEach
	public void beforeEach() {
		flyway.migrate();
	}
	
	@Test
	public void Should_return_two_drugstores() {
		List<Drugstore> drugstores = drugstoreService.getAll();
		
		assertEquals(2, drugstores.size());
	}
	
	@Test
	public void should_find_the_drugstore_with_id_eq_1() {
		Long idDrugtore = 1L;
		
		Drugstore drugstore = drugstoreService.findById(idDrugtore);
		
		assertEquals(idDrugtore, drugstore.getId());
		assertEquals("98224585000128", drugstore.getCnpj());
	}
	
	@Test
	public void should_not_find_the_drugstore() {
		Long idDrugstore =3100L;
		
		assertThatThrownBy(() -> drugstoreService.findById(idDrugstore)).isInstanceOf(EntityNotFoundException.class);
	}
	
	@Test
	public void should_create_an_drugstore() {
		List<String> phones = new ArrayList<>();
		phones.add("(31) 96545-3215");
		phones.add("(41) 96542-3215");
		
		Drugstore drugstore = Drugstore.builder()
				.name("Drogaria Popular")
				.cnpj("24710620000182")
				.email("drogariapopular@email.com")
				.phones(phones)
				.build();
		
		drugstore = drugstoreService.create(drugstore);
		
		assertNotNull(drugstore.getId());
		assertEquals(2, drugstore.getPhones().size());
	}
	
	@Test
	public void should_not_create_drugstore_due_cnpj_exists() {
		List<String> phones = new ArrayList<>();
		phones.add("(31) 96545-3215");
		phones.add("(41) 96542-3215");
		
		Drugstore drugstore = Drugstore.builder()
				.name("Drogaria Popular")
				.cnpj("98224585000128")
				.email("drogariapopular@email.com")
				.phones(phones)
				.build();
		
		assertThatThrownBy(() -> drugstoreService.create(drugstore)).isInstanceOf(EntityAlreadyExistsException.class);
	}
	
	@Test
	public void should_update_drugstore_information() {
		List<String> phones = new ArrayList<>();
		phones.add("(31) 96545-3215");
		phones.add("(41) 96542-3215");
		phones.add("(87) 96542-2154");
		
		DrugstoreDTO drugstoreDTO = new DrugstoreDTO();
		drugstoreDTO.setName("Drogaria Popular");
		drugstoreDTO.setCnpj("92040855000164");
		drugstoreDTO.setEmail("drogariapopular@email.com");
		drugstoreDTO.setPhones(phones);
		
		drugstoreService.update(2l, drugstoreDTO);
		
		Drugstore drugstore = drugstoreService.findById(2l);
		
		assertEquals(drugstoreDTO.getName(), drugstore.getName());
		assertEquals(drugstoreDTO.getCnpj(), drugstore.getCnpj());
		assertEquals(drugstoreDTO.getEmail(), drugstore.getEmail());
		assertEquals(3, drugstore.getPhones().size());
	}
	
	@Test
	public void should_remove_an_drugstore() {
		drugstoreService.remove(2l);
	}
	
	@Test
	public void should_throw_error_on_remove_an_drugstore() {
		assertThatThrownBy(() -> drugstoreService.remove(300l))
								.isInstanceOf(EntityNotFoundException.class);
	}
}
