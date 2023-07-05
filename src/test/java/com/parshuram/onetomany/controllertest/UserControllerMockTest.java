package com.parshuram.onetomany.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.controller.UserController;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.service.UserService;
import com.parshuram.onetomany.utils.ApiResponse;

@SpringBootTest
class UserControllerMockTest {
	
	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;
	
	@Test
	void testCreateUser() {
		
		UserDto userDto = new UserDto(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(userDto, userEntity);
		
		when(userService.createUser(userDto)).thenReturn(userEntity);
		
		ResponseEntity<UserEntity> responseEntity = userController.createUser(userDto);
		
		assertEquals(userEntity, responseEntity.getBody());
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testUpdateUser() {
		
		UserDto userDto = new UserDto(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(userDto, userEntity);
		
		when(userService.updateUser(userDto, 1)).thenReturn(userEntity);
		
		ResponseEntity<UserEntity> responseEntity = userController.updateUser(userDto, 1);
		
		assertEquals(1, responseEntity.getBody().getId());
		
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
		
		assertEquals(202, responseEntity.getStatusCodeValue());
		
		assertEquals("Parshuram", responseEntity.getBody().getFirstName());
		
		assertEquals("Bhosekar", responseEntity.getBody().getLastName());
		
		assertEquals("Pune", responseEntity.getBody().getCity());
		
		assertEquals("Maharashtra", responseEntity.getBody().getState());
	}
	
	@Test
	void testGetListOfAllUsers() {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patidar", "Sangli", "7276624426", "Maharashtra"));		
			
		when(userService.getAllUserDetails()).thenReturn(userList);
		
		ResponseEntity<List<UserEntity>> responseEntity = userController.getAllListOfUsers();
		
		assertEquals(3, responseEntity.getBody().size());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testDeleteUser() {
		
		userService.deleteUser(1);
		
		ResponseEntity<ApiResponse> responseEntity = userController.deleteUser(1);
		
		assertEquals("Deleted Successfully.....", responseEntity.getBody().getMessage());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testGetUserById() {
		
		UserEntity userEntity=new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");
		
		when(userService.getUserById(1)).thenReturn(userEntity);
		
		ResponseEntity<UserEntity> responseEntity = userController.getUserById(1);
		
		assertEquals(userEntity, responseEntity.getBody());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testGetUserByName() {
		
		UserEntity userEntity=new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");
		
		when(userService.getUserByName("Parshuram")).thenReturn(userEntity);
		
		ResponseEntity<UserEntity> responseEntity = userController.getUserByName("Parshuram");
		
		assertEquals("Parshuram", responseEntity.getBody().getFirstName());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testGetUserByCity() {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patidar", "Sangli", "7276624426", "Maharashtra"));		
		
		when(userService.getUserByCity("Pune")).thenReturn(userList);
		
		ResponseEntity<List<UserEntity>> responseEntity = userController.getUserByCity("Pune");
		
		List<UserEntity> list = responseEntity.getBody().stream()
					.filter(user->user.getCity().equalsIgnoreCase("pune"))
					.collect(Collectors.toList());
		
		assertEquals(2, list.size());
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
		
		assertEquals(302, responseEntity.getStatusCodeValue());
		
	}
	
	@Test
	void testGetUserByLastName() {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Bhosekar", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patidar", "Sangli", "7276624426", "Maharashtra"));		
		
		when(userService.getUserByLastName("Bhosekar")).thenReturn(userList);
		
		ResponseEntity<List<UserEntity>> responseEntity = userController.getUserByLastName("Bhosekar");
		
		List<UserEntity> list = responseEntity.getBody().stream()
				.filter(user->user.getLastName().equalsIgnoreCase("bhosekar"))
				.collect(Collectors.toList());
		
		assertEquals(2, list.size());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	void testGetUserByState() {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Bhosekar", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patidar", "Sangli", "7276624426", "Maharashtra"));		
		
		when(userService.getUserByState("Maharashtra")).thenReturn(userList);

		ResponseEntity<List<UserEntity>> responseEntity = userController.getUserByState("Maharashtra");
		
		List<UserEntity> list = responseEntity.getBody().stream()
					.filter(user->user.getState().equalsIgnoreCase("maharashtra"))
					.collect(Collectors.toList());
		
		assertEquals(3, list.size());
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
		
		assertEquals(302, responseEntity.getStatusCodeValue());
		
	}
	
	
	
	
	
	
	
}
