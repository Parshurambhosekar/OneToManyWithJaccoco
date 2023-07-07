package com.parshuram.onetomany.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserEntity createUser(UserDto userDto) {

		logger.info("User Dto is Passed from Client"+userDto);
		
		var entity = new UserEntity();

		BeanUtils.copyProperties(userDto, entity);

		logger.info("User Dto is Converted {} into  User Entity",entity);
		
		return userRepository.save(entity);
	}

	@Override
	public UserEntity updateUser(UserDto userDto, Integer id) {

		Optional<UserEntity> optionalUser = userRepository.findById(id);
		
		logger.info("User is found in"+id+"and user is.."+optionalUser);

		if (optionalUser.isPresent()) {

			var userEntity = optionalUser.get();
			
			logger.info("seting the Updated data to Entity");
			
			userEntity.setFirstName(userDto.getFirstName());
			userEntity.setLastName(userDto.getLastName());
			userEntity.setCity(userDto.getCity());
			userEntity.setMobileNumber(userDto.getMobileNumber());
			userEntity.setState(userDto.getState());

			logger.info("Updated User Entity {} ",userEntity);
			
			logger.info("Updated User Saved with the Updated Details...");
			
			return userRepository.save(userEntity);
			
		} else {

			logger.info("Exception Thrown...");
			
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id);

		}

	}

	@Override
	public List<UserEntity> getAllUserDetails() {
		
		List<UserEntity> userList = userRepository.findAll();
		
		logger.info("User List is Return {} from Service ",userList);
		
		return userList;
	}

	@Override
	public void deleteUser(Integer id) {

		if (userRepository.existsById(id)) {
			
			logger.info("User is Present with "+id);
			
			userRepository.deleteById(id);
			
		} else {
			
			logger.info("Exception Thrown....");
			
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id);
		}
	}

	@Override
	public UserEntity getUserById(Integer id) {
		
		UserEntity userEntity = userRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));
		
		logger.info("User Entity is return with" + userEntity.getId());
		
		logger.info("User based With id {}",userEntity);
		
		return userEntity;
	}

	@Override
	public UserEntity getUserByName(String firstName) {

		var userEntity = userRepository.findByFirstName(firstName);
		
		logger.info("Repo method user is found with first Name..."+firstName);

		if (userEntity == null) {
			
			logger.info("Exception Thrown...");

			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.FIRSTNAME, firstName);
		}

		logger.info("User return with FirstName {}",userEntity);
		
		return userEntity;
	}

	@Override
	public List<UserEntity> getUserByLastName(String lastName) {

		List<UserEntity> userByLastName = userRepository.getUserByLastName(lastName);

		logger.info("Repo method user is found with Last Name..."+lastName);
		
		if (userByLastName.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.LASTNAME, lastName);
		}
		
		logger.info("UserList return with LastName {}",userByLastName);

		return userByLastName;
	}

	@Override
	public List<UserEntity> getUserByCity(String city) {

		List<UserEntity> userByCity = userRepository.getUserByCity(city);
		
		logger.info("Repo method user is found with City..."+city);
	
		if (userByCity.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.CITY, city);
		}

		logger.info("UserList return with city {}",city);
		
		return userByCity;
	}

	@Override
	public List<UserEntity> getUserByState(String state) {

		List<UserEntity> userByState = userRepository.findByState(state);
		
		logger.info("Repo method user is found with State..."+state);
	
		if (userByState.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.USER, AppConstants.STATE, state);

		}
		
		logger.info("UserList return with State {}",state);

		return userByState;
	}

}
