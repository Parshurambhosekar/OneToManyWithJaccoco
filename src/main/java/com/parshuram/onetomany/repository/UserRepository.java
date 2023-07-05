package com.parshuram.onetomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parshuram.onetomany.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByFirstName(String firstName);
	
	@Query("from UserEntity where lastName= :n")
	public List<UserEntity> getUserByLastName(@Param("n") String lastName);
	
	@Query(value = "select * from user_details where city=?",nativeQuery = true)
	public List<UserEntity> getUserByCity(String city);
	
	public List<UserEntity> findByState(String state);
	
	
}
