package com.parshuram.onetomany.service;

import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.entity.PolicyEntity;
import java.util.List;

public interface PolicyService {
	
	public PolicyEntity createPolicy(PolicyDto policyDto,Integer id);
	
	public PolicyEntity updatePolicy(PolicyDto policyDto,Integer policyId,Integer id);
	
	public List<PolicyEntity> getAllPolicies();
	
	public void deletePolicy(Integer policyId);
	
	public PolicyEntity getPolicyById(Integer policyId);
	
	//get policy by name
	public List<PolicyEntity> getPolicyName(String policyName);
	
	//get policy by insurance
	public List<PolicyEntity> getPolicyByInsuranceAmount(Double maxInsurance);
	
	//get policy by monthly deducted amount
	public List<PolicyEntity> getPolicyByMonthlyDeductedAmout(Long monthlyAmount);
	
	
	
	

}
