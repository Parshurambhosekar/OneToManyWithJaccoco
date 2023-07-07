package com.parshuram.onetomany.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.constants.AppConstants;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.service.UserService;
import com.parshuram.onetomany.utils.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto userDto) {
		//ok
		UserEntity createUser = userService.createUser(userDto);
		
		log.info("user Service Called user is Created..." +createUser);
		
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
		//ok
		log.info("UserDto {} is .."+userDto);
		
		log.info("User Dto with Id.."+userDto.getId());
		
		UserEntity updateUser = userService.updateUser(userDto, id);
		
		log.info("User is updated...."+updateUser);
		
		return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserEntity>> getAllListOfUsers() {
		//ok
		List<UserEntity> allUserDetails = userService.getAllUserDetails();
		
		log.info("All User details Called...");
		log.info("ALl Users {} is recieved",allUserDetails);
		
		return new ResponseEntity<>(allUserDetails, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
		//ok
		log.info("Delete User Service called {} ",id);
		
		userService.deleteUser(id);
		
		log.info("user is Deleted....");

		return new ResponseEntity<>(new ApiResponse(AppConstants.MESSAGE), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
		//ok
		UserEntity userById = userService.getUserById(id);
		
		log.info("User is Received by Id " +userById.getId());
		log.info("User By id {} ",userById);
		
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<UserEntity> getUserByName(@PathVariable String name) {
		//ok
		
		UserEntity userByName = userService.getUserByName(name);
		
		log.info("User is Received By Name " +name);
		log.info("User By Name {} ",userByName);
		
		return new ResponseEntity<>(userByName, HttpStatus.OK);
	}

	@GetMapping("/lastname/{name}")
	public ResponseEntity<List<UserEntity>> getUserByLastName(@PathVariable(name = "name") String lastName) {
		//ok
		
		List<UserEntity> userByLastName = userService.getUserByLastName(lastName);
		
		log.info("User is Received By LastName " +lastName);

		log.info("User is Received By LastName {} ",userByLastName);
		
		return new ResponseEntity<>(userByLastName, HttpStatus.OK);
	}

	@GetMapping("/city/{city}")
	public ResponseEntity<List<UserEntity>> getUserByCity(@PathVariable String city) {
		//ok
		
		List<UserEntity> userByCity = userService.getUserByCity(city);
		
		log.info("User is Received By City " +city);
		
		log.info("User is Received By City {} ",userByCity);
		
		return new ResponseEntity<>(userByCity, HttpStatus.FOUND);
	}

	@GetMapping("/state/{state}")
	public ResponseEntity<List<UserEntity>> getUserByState(@PathVariable String state) {
		//ok
		List<UserEntity> userByState = userService.getUserByState(state);
		
		log.info("User is Received By State " +state);
		
		log.info("User is Received By State {} ",userByState);
		
		return new ResponseEntity<>(userByState, HttpStatus.FOUND);
	}

}
