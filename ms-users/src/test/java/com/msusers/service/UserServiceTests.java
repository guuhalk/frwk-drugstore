package com.msusers.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.msschemas.dto.UserDTO;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.enumeration.UserType;
import com.msusers.model.User;

@DisplayName("UserServiceTests")
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private Flyway flyway;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	public void beforeEach() {
		flyway.migrate();
	}
	
	@Test
	public void Should_return_two_users() {
		List<User> users = userService.getAll();
		
		assertEquals(2, users.size());
	}
	
	@Test
	public void should_find_the_user_with_id_eq_1() {
		Long idUser = 1L;
		
		User user = userService.findById(idUser);
		
		assertEquals(idUser, user.getId());
		assertEquals("11779016689", user.getCpf());
	}
	
	@Test
	public void should_not_find_the_user() {
		Long idUser = 100L;
		
		assertThatThrownBy(() -> userService.findById(idUser)).isInstanceOf(EntityNotFoundException.class);
	}
	
	@Test
	public void should_find_an_user_by_cpf() {
		String cpfUser = "11779016689";
		
		User user = userService.findByCpf(cpfUser);
		
		assertEquals(cpfUser, user.getCpf());
	}
	
	@Test
	public void should_not_find_the_user_by_cpf() {
		String cpfUser = "65432189723";
		
		assertThatThrownBy(() -> userService.findByCpf(cpfUser)).isInstanceOf(EntityNotFoundException.class);
	}
	
	@Test
	public void should_create_an_user() {
		String password = "123456";
		
		User user = User.builder()
				.name("Gerald")
				.cpf("03181642053")
				.email("gerald@email.com")
				.password(password)
				.userType(UserType.CLIENT)
				.build();
		
		user = userService.create(user);
		
		assertNotNull(user.getId());
		assertTrue(passwordEncoder.matches(password, user.getPassword()));
	}
	
	@Test
	public void should_not_create_user_due_cpf_exists() {
		User user = User.builder()
				.name("Gerald")
				.cpf("11779016689")
				.email("gerald@email.com")
				.password("123456")
				.userType(UserType.CLIENT)
				.build();
		
		assertThatThrownBy(() -> userService.create(user)).isInstanceOf(GenericException.class);
	}
	
	@Test
	public void should_not_create_user_due_email_exists() {
		User user = User.builder()
				.name("Gerald")
				.cpf("03181642053")
				.email("airton@email.com")
				.password("123456")
				.userType(UserType.CLIENT)
				.build();
		
		assertThatThrownBy(() -> userService.create(user)).isInstanceOf(GenericException.class);
	}
	
	@Test
	public void should_update_user_information() {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Airton Silva");
		userDTO.setCpf("03181642053");
		userDTO.setEmail("airton@email.com");
		userDTO.setUserType(UserType.CLIENT);
		
		User user = userService.update(2l, userDTO);
		
		assertEquals(userDTO.getName(), user.getName());
		assertEquals(userDTO.getCpf(), user.getCpf());
		assertEquals(userDTO.getEmail(), user.getEmail());
		assertEquals(userDTO.getUserType(), user.getUserType());
	}
	
	@Test
	public void should_change_password() {
		String currentPassword = "admin";
		String newPassword = "123456";
		
		userService.changePassword(2l, currentPassword, newPassword);
		
		User user = userService.findById(2l);
		assertTrue(passwordEncoder.matches(newPassword, user.getPassword()));
	}
	
	@Test
	public void incorrect_password_on_change_password() {
		String currentPassword = "123456789";
		String newPassword = "admin";
		
		assertThatThrownBy(() -> userService.changePassword(2l, currentPassword, newPassword))
							.isInstanceOf(GenericException.class);
	}
	
	@Test
	public void should_remove_an_user() {
		userService.remove(2l);
	}
	
	@Test
	public void should_throw_error_on_remove_an_user() {
		assertThatThrownBy(() -> userService.remove(3l))
								.isInstanceOf(EntityNotFoundException.class);
	}
}
