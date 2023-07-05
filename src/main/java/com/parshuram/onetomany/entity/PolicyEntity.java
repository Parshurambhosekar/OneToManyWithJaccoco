package com.parshuram.onetomany.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "policy_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyEntity {

	@Id
	@Column(name = "policy_number")
	private Integer policyId;
	@Column(name = "policy_name")
	private String policyName;
	@Column(name = "getting_issurance_upto")
	private Double maxInsurance;
	@Column(name = "monthly_payable_amount")
	private Long monthlyAmountDeduction;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate endDate;
	
	
	//many policies registered in one user
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;


	public PolicyEntity(Integer policyId, String policyName, Double maxInsurance, Long monthlyAmountDeduction,
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
