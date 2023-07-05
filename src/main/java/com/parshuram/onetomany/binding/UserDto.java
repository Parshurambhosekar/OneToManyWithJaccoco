package com.parshuram.onetomany.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Integer id;
	@NotEmpty(message = "firstName is Mandatory....")
	private String firstName;
	@NotEmpty(message = "lastName is Mandatory.....")
	private String lastName;
	@NotEmpty(message = "city is required..")
	private String city;
	@NotEmpty
	@Size(min = 10,max = 10,message = "MobileNumber must be in 10 Digits")
	private String mobileNumber;
	@NotEmpty(message = "state is required..")
	private String state;
	
	private PolicyDto policyDto;

	public UserDto(Integer id, @NotEmpty(message = "firstName is Mandatory....") String firstName,
			@NotEmpty(message = "lastName is Mandatory.....") String lastName,
			@NotEmpty(message = "city is required..") String city,
			@NotEmpty @Size(min = 10, max = 10, message = "MobileNumber must be in 10 Digits") String mobileNumber,
			@NotEmpty(message = "state is required..") String state) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.mobileNumber = mobileNumber;
		this.state = state;
	}
	
	

}
