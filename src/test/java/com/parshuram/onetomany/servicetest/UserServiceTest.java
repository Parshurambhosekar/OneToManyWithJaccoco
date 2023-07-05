package com.parshuram.onetomany.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.exception.ResourceNotFoundException;
import com.parshuram.onetomany.repository.UserRepository;
import com.parshuram.onetomany.serviceimpl.UserServiceImpl;

@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	void testCreateUser() {

		UserEntity userEntity = new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		when(userRepository.save(userEntity)).thenReturn(userEntity);

		UserEntity createUser = userServiceImpl.createUser(userDto);

		assertEquals(userEntity, createUser);
	}

	@Test
	void testUpdateUser() {

		UserEntity userEntity = new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(userEntity));
		
		when(userRepository.existsById(2)).thenReturn(false);

		when(userRepository.save(userEntity)).thenReturn(userEntity);

		assertEquals(1, userServiceImpl.updateUser(userDto, 1).getId());
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.updateUser(userDto, 2).getId());
	}

	@Test
	void testGetAllUserDetails() {
		
		List<UserEntity> userEntity = new ArrayList<>();
		userEntity.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userEntity.add(new UserEntity(2, "Partha", "Patil", "Sangli", "9307304360", "Maharashtra"));
		userEntity.add(new UserEntity(3, "Sumit", "Bhosale", "Mumbai", "7276624426", "Telangana"));
		userEntity.add(new UserEntity(4, "Anmol", "Chavan", "Pune", "9881913245", "Telangana"));
	
		when(userRepository.findAll()).thenReturn(userEntity);
		
		assertEquals(userEntity, userServiceImpl.getAllUserDetails());
	}
	
	@Test
	void testDeleteUser() {
		
		when(userRepository.existsById(1)).thenReturn(true);
		
		when(userRepository.existsById(2)).thenReturn(false);
		
		userServiceImpl.deleteUser(1);
		
		verify(userRepository,times(1)).deleteById(1);
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.deleteUser(2));
		
	}
	
	@Test
	void testGetUserById() {
		
		UserEntity userEntity = new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(userEntity));
		
		when(userRepository.existsById(2)).thenReturn(false);
		
		assertEquals(1, userServiceImpl.getUserById(1).getId());
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.getUserById(2).getId());
		
	}
	
	@Test
	void testGetUserByName() {
		
		UserEntity userEntity = new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");
		
		when(userRepository.findByFirstName("Parshuram")).thenReturn(userEntity);
		
		assertEquals("Parshuram", userServiceImpl.getUserByName("Parshuram").getFirstName());
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.getUserByName("Sumit").getFirstName());
	}
	
	@Test
	void testGetUserByLastName() {
		
		List<UserEntity> userEntity = new ArrayList<>();
		userEntity.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userEntity.add(new UserEntity(2, "Partha", "Patil", "Sangli", "9307304360", "Maharashtra"));
		userEntity.add(new UserEntity(3, "Sumit", "Bhosekar", "Mumbai", "7276624426", "Maharashtra"));
		userEntity.add(new UserEntity(4, "Anmol", "Chavan", "Pune", "9881913245", "Telangana"));
		
		when(userRepository.getUserByLastName("Bhosekar")).thenReturn(userEntity);
		
		List<UserEntity> userByLastName = userServiceImpl.getUserByLastName("Bhosekar");
		
		List<UserEntity> list = userByLastName.stream().filter(emp->emp.getLastName().equalsIgnoreCase("Bhosekar"))
						.collect(Collectors.toList());
		
		assertNotNull(list);
		
		assertEquals(2, list.size());
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.getUserByLastName("Bhosale"));
	}
	
	@Test
	void testGetUserByState() {
		
		List<UserEntity> userEntity = new ArrayList<>();
		userEntity.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userEntity.add(new UserEntity(2, "Partha", "Patil", "Sangli", "9307304360", "Maharashtra"));
		userEntity.add(new UserEntity(3, "Sumit", "Bhosekar", "Mumbai", "7276624426", "Maharashtra"));
		userEntity.add(new UserEntity(4, "Anmol", "Chavan", "Pune", "9881913245", "Telangana"));
		
		when(userRepository.findByState("Maharashtra")).thenReturn(userEntity);
		
		List<UserEntity> userByState = userServiceImpl.getUserByState("Maharashtra");
		
		List<UserEntity> list = userByState.stream().filter(emp->emp.getState().equalsIgnoreCase("Maharashtra"))
							.collect(Collectors.toList());
		
		assertEquals(3, list.size());
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.getUserByState("AndraPradesh"));
	}
	
	@Test
	void testGetUserByCity() {
		
		List<UserEntity> userEntity = new ArrayList<>();
		userEntity.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userEntity.add(new UserEntity(2, "Partha", "Patil", "Sangli", "9307304360", "Maharashtra"));
		userEntity.add(new UserEntity(3, "Sumit", "Bhosekar", "Mumbai", "7276624426", "Maharashtra"));
		userEntity.add(new UserEntity(4, "Anmol", "Chavan", "Pune", "9881913245", "Telangana"));
		
		when(userRepository.getUserByCity("Pune")).thenReturn(userEntity);
		
		List<UserEntity> userByCity = userServiceImpl.getUserByCity("Pune");
		
		List<UserEntity> list = userByCity.stream()
				.filter(user->user.getCity().equalsIgnoreCase("Pune"))
				.collect(Collectors.toList());
		
		assertNotNull(list);
		
		assertThat(list.size()).isEqualTo(2);
		
		assertThrows(ResourceNotFoundException.class, ()->userServiceImpl.getUserByCity("Bangalore"));	
	}
	
	
}
