package com.parshuram.onetomany.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.constants.AppConstants;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.exception.ResourceNotFoundException;
import com.parshuram.onetomany.repository.UserRepository;
import com.parshuram.onetomany.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity createUser(UserDto userDto) {

		var entity = new UserEntity();

		BeanUtils.copyProperties(userDto, entity);

		return userRepository.save(entity);
	}

	@Override
	public UserEntity updateUser(UserDto userDto, Integer id) {

		Optional<UserEntity> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {

			var userEntity = optionalUser.get();

			userEntity.setFirstName(userDto.getFirstName());
			userEntity.setLastName(userDto.getLastName());
			userEntity.setCity(userDto.getCity());
			userEntity.setMobileNumber(userDto.getMobileNumber());
			userEntity.setState(userDto.getState());

			return userRepository.save(userEntity);
		} else {

			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id);

		}

	}

	@Override
	public List<UserEntity> getAllUserDetails() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Integer id) {

		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id);
		}
	}

	@Override
	public UserEntity getUserById(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));
	}

	@Override
	public UserEntity getUserByName(String firstName) {

		var userEntity = userRepository.findByFirstName(firstName);

		if (userEntity == null) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.FIRSTNAME, firstName);
		}

		return userEntity;
	}

	@Override
	public List<UserEntity> getUserByLastName(String lastName) {

		List<UserEntity> userByLastName = userRepository.getUserByLastName(lastName);

		if (userByLastName.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.LASTNAME, lastName);
		}

		return userByLastName;
	}

	@Override
	public List<UserEntity> getUserByCity(String city) {

		List<UserEntity> userByCity = userRepository.getUserByCity(city);

		if (userByCity.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.CITY, city);
		}

		return userByCity;
	}

	@Override
	public List<UserEntity> getUserByState(String state) {

		List<UserEntity> userByState = userRepository.findByState(state);

		if (userByState.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.STATE, state);

		}

		return userByState;
	}

}
