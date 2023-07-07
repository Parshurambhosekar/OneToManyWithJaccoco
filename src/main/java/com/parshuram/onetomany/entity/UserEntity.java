package com.parshuram.onetomany.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "User_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	private String lastName;
	private String city;
	private String mobileNumber;
	private String state;
	
	//one user has many policy....
	
	@OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonIgnore
	@Exclude
	private List<PolicyEntity> policyEntity;

	public UserEntity(Integer id, String firstName, String lastName, String city, String mobileNumber, String state) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.mobileNumber = mobileNumber;
		this.state = state;
	}
	
	
	
	
}
