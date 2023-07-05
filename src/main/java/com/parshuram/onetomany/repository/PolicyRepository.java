package com.parshuram.onetomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parshuram.onetomany.entity.PolicyEntity;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Integer>{
	
	@Query("from PolicyEntity where policyName= :m")
	public List<PolicyEntity> getPolicyByName(@Param("m") String policyName);

	@Query(value = "select * from policy_details where getting_issurance_upto >=?",nativeQuery = true)
	public List<PolicyEntity> getByMaxInsurance(Double maxInsurance);
	
	@Query(value = "select * from policy_details where monthly_payable_amount<=?",nativeQuery = true)
	public List<PolicyEntity> getPoliciesByDeductedAmount(Long monthlyAmount);
	
	
}
