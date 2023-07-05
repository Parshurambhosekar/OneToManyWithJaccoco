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

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto userDto) {
		//ok
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
		//ok
		return new ResponseEntity<>(userService.updateUser(userDto, id), HttpStatus.ACCEPTED);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserEntity>> getAllListOfUsers() {
		//ok
		return new ResponseEntity<>(userService.getAllUserDetails(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
		//ok
		userService.deleteUser(id);

		return new ResponseEntity<>(new ApiResponse(AppConstants.MESSAGE), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
		//ok
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<UserEntity> getUserByName(@PathVariable String name) {
		//ok
		return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
	}

	@GetMapping("/lastname/{name}")
	public ResponseEntity<List<UserEntity>> getUserByLastName(@PathVariable(name = "name") String lastName) {
		//ok
		return new ResponseEntity<>(userService.getUserByLastName(lastName), HttpStatus.OK);
	}

	@GetMapping("/city/{city}")
	public ResponseEntity<List<UserEntity>> getUserByCity(@PathVariable String city) {
		//ok
		return new ResponseEntity<>(userService.getUserByCity(city), HttpStatus.FOUND);
	}

	@GetMapping("/state/{state}")
	public ResponseEntity<List<UserEntity>> getUserByState(@PathVariable String state) {
		//ok
		return new ResponseEntity<>(userService.getUserByState(state), HttpStatus.FOUND);
	}

}
