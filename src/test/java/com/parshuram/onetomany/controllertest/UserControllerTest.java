package com.parshuram.onetomany.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.controller.UserController;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.service.UserService;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	void testCreateUser() throws Exception {

		UserDto userDto = new UserDto(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(userDto, userEntity);

		ObjectMapper mapper = new ObjectMapper();
		String valueAsString = mapper.writeValueAsString(userEntity);

		when(userService.createUser(userDto)).thenReturn(userEntity);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
				.contentType(MediaType.APPLICATION_JSON).content(valueAsString);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(201, status);

		assertEquals(valueAsString, response.getContentAsString());

		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testUpdateUser() throws Exception {

		UserDto userDto = new UserDto(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");

		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(userDto, userEntity);

		ObjectMapper mapper = new ObjectMapper();
		String valueAsString = mapper.writeValueAsString(userEntity);

		when(userService.updateUser(userDto, 1)).thenReturn(userEntity);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}","1")
							.contentType(MediaType.APPLICATION_JSON)
							.content(valueAsString);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(202, status);
		
		assertEquals(valueAsString, response.getContentAsString());
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void testGetListOfAllUsers() throws Exception {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patidar", "Sangli", "7276624426", "Maharashtra"));		
			
		when(userService.getAllUserDetails()).thenReturn(userList);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/")
															.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test 
	void testDeleteUser() throws Exception {
		
		userService.deleteUser(1);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/{id}","1");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);	
	}
	
	@Test
	void testGetUserById() throws Exception {
		
		UserEntity userEntity=new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");
		
		when(userService.getUserById(1)).thenReturn(userEntity);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}","1");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);
	}
	
	@Test
	void testGetUserByName() throws Exception {
		
		UserEntity userEntity=new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra");
	
		when(userService.getUserByName("Parshuram")).thenReturn(userEntity);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/name/{name}","Parshuram");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);
	}
	
	@Test
	void testGetUserByLastName() throws Exception {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Patil", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patil", "Sangli", "7276624426", "Maharashtra"));		
			
		when(userService.getUserByLastName("Patil")).thenReturn(userList);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/lastname/{name}","Patil");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void testGetUserByCity() throws Exception {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Patil", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patil", "Sangli", "7276624426", "Maharashtra"));		
			
		when(userService.getUserByCity("Pune")).thenReturn(userList);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/city/{city}","Pune");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(302, status);	
	}
	
	@Test
	void testGetUserByState() throws Exception {
		
		List<UserEntity> userList=new ArrayList<>();
		userList.add(new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		userList.add(new UserEntity(2, "Sumit", "Patil", "Pune", "9623777896", "Maharashtra"));
		userList.add(new UserEntity(3, "Amit", "Patil", "Sangli", "7276624426", "Maharashtra"));		
			
		when(userService.getUserByState("Maharashtra")).thenReturn(userList);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/state/{state}","Maharashtra");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(302,status);
		
		assertEquals("application/json", response.getContentType());
		
	}
}
