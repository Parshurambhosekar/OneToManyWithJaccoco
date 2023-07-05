package com.parshuram.onetomany.service;

import java.util.List;

import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.entity.UserEntity;

public interface UserService {
	
	public UserEntity createUser(UserDto userDto);
	
	public UserEntity updateUser(UserDto userDto,Integer id);
	
	public List<UserEntity> getAllUserDetails();
	
	public void deleteUser(Integer id);
	
	public UserEntity getUserById(Integer id);
	
	//get user by firstName
	public UserEntity getUserByName(String firstName);
	
	//get user by lastName
	public List<UserEntity> getUserByLastName(String lastName);
	
	//get user by city
	public List<UserEntity> getUserByCity(String city);
	
	//get user by state
	public List<UserEntity> getUserByState(String state);
	
	
	
	
}
