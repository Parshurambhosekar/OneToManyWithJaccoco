package com.parshuram.onetomany.binding;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDto {
	
	private Integer policyId;
	@NotEmpty(message = "Please Enter the Policy Name.....")
	private String policyName;
	@NotNull(message = "Enter the Maximum Ensurance Amout get by the User")
	@Min(value = 200000,message = "Insurance must be atleast 200000lakh")
	private Double maxInsurance;
	@NotNull(message = "Please enter the Monthly Amount Deduction")
	@Min(value = 4000 ,message = "Minimum 4000 Amount must be Deducted")
	private Long monthlyAmountDeduction;
	@JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	private UserDto userDto;

	public PolicyDto(Integer policyId, @NotEmpty(message = "Please Enter the Policy Name.....") String policyName,
			@NotNull(message = "Enter the Maximum Ensurance Amout get by the User") @Min(value = 200000, message = "Insurance must be atleast 200000lakh") Double maxInsurance,
			@NotNull(message = "Please enter the Monthly Amount Deduction") @Min(value = 4000, message = "Minimum 4000 Amount must be Deducted") Long monthlyAmountDeduction,
			LocalDate startDate, LocalDate endDate) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.maxInsurance = maxInsurance;
		this.monthlyAmountDeduction = monthlyAmountDeduction;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	
	
	
}
