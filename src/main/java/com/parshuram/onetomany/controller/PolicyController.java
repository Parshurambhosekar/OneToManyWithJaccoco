package com.parshuram.onetomany.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.constants.AppConstants;
import com.parshuram.onetomany.entity.PolicyEntity;
import com.parshuram.onetomany.service.PolicyService;
import com.parshuram.onetomany.utils.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/policy")
@Slf4j
public class PolicyController {

	@Autowired
	private PolicyService policyService;

	@PostMapping("/{userId}")
	public ResponseEntity<PolicyEntity> createPolicy(@Valid @RequestBody PolicyDto policyDto,
			@PathVariable(name = "userId") Integer id) {
		// ok

		log.info("Policy Data {} ", policyDto + "With User id" + id);

		PolicyEntity createPolicy = policyService.createPolicy(policyDto, id);

		log.info("User is {} with Policy created", createPolicy);

		return new ResponseEntity<>(createPolicy, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}/{policyId}")
	public ResponseEntity<PolicyEntity> updatePolicy(@Valid @RequestBody PolicyDto policyDto,
			@PathVariable(name = "userId") Integer id, @PathVariable(name = "policyId") Integer policyId) {
		// ok

		log.info("Policy Data {} ", policyDto + " with userId" + id + "and {} ", policyDto.getPolicyId());

		PolicyEntity updatePolicy = policyService.updatePolicy(policyDto, id, policyId);

		log.info("Updated Policy {} with details", updatePolicy);

		return new ResponseEntity<>(updatePolicy, HttpStatus.ACCEPTED);
	}

	@GetMapping("/")
	public ResponseEntity<List<PolicyEntity>> getAllPolicyDetails() {
		// ok
		List<PolicyEntity> allPolicies = policyService.getAllPolicies();

		log.info("All Policies {} with User details", allPolicies);

		return new ResponseEntity<>(allPolicies, HttpStatus.OK);
	}

	@DeleteMapping("/{policyId}")
	public ResponseEntity<ApiResponse> deletePolicyById(@PathVariable Integer policyId) {
		// ok

		log.info("Policy With id {} ", policyId);

		policyService.deletePolicy(policyId);

		log.info("Policy Deleted Along with UserId " +policyId);

		return new ResponseEntity<>(new ApiResponse(AppConstants.MESSAGE), HttpStatus.OK);
	}

	@GetMapping("/{policyId}")
	public ResponseEntity<PolicyEntity> getPolicyById(@PathVariable Integer policyId) {
		// ok
		PolicyEntity policyById = policyService.getPolicyById(policyId);

		log.info("Policy With id " +policyById.getPolicyId());

		log.info("Policy Details {} ", policyById);

		return new ResponseEntity<>(policyById, HttpStatus.OK);
	}

	@GetMapping("/policyname/{name}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByName(@PathVariable(name = "name") String policyName) {
		// ok
		List<PolicyEntity> name = policyService.getPolicyName(policyName);
		
		log.info("Policy With policy Name " +policyName);

		log.info("Policy Details {} ", name);

		return new ResponseEntity<>(name, HttpStatus.OK);
	}

	@GetMapping("/insurance/{max}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByInsuranceAmount(
			@PathVariable(name = "max") Double maxInsurance) {
		// ok
		List<PolicyEntity> policyByInsuranceAmount = policyService.getPolicyByInsuranceAmount(maxInsurance);
		
		log.info("Policy With policy Maximum Insurance Limit " +maxInsurance);

		log.info("Policy Details {} ", policyByInsuranceAmount);
		
		return new ResponseEntity<>(policyByInsuranceAmount, HttpStatus.FOUND);
	}

	@GetMapping("/dAmount/{amount}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByMonthlyDeductedAmout(
			@PathVariable(name = "amount") Long monthlyAmount) {
		// ok
		
		List<PolicyEntity> policyByMonthlyDeductedAmout = policyService.getPolicyByMonthlyDeductedAmout(monthlyAmount);
		
		log.info("Policy With policy Maximum Insurance Limit " +monthlyAmount);

		log.info("Policy Details {} ", policyByMonthlyDeductedAmout);
		
		return new ResponseEntity<>(policyByMonthlyDeductedAmout, HttpStatus.FOUND);
	}

}
