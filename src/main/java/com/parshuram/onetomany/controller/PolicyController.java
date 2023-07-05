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

@RestController
@RequestMapping("/policy")
public class PolicyController {

	@Autowired
	private PolicyService policyService;

	@PostMapping("/{userId}")
	public ResponseEntity<PolicyEntity> createPolicy(@Valid @RequestBody PolicyDto policyDto,
			@PathVariable(name = "userId") Integer id) {
		//ok
		return new ResponseEntity<>(policyService.createPolicy(policyDto, id), HttpStatus.CREATED);
	}

	@PutMapping("/{userId}/{policyId}")
	public ResponseEntity<PolicyEntity> updatePolicy(@Valid @RequestBody PolicyDto policyDto,
			@PathVariable(name = "userId") Integer id, @PathVariable(name = "policyId") Integer policyId) {
		//ok
		return new ResponseEntity<>(policyService.updatePolicy(policyDto, id, policyId), HttpStatus.ACCEPTED);
	}

	@GetMapping("/")
	public ResponseEntity<List<PolicyEntity>> getAllPolicyDetails() {
		//ok
		return new ResponseEntity<>(policyService.getAllPolicies(), HttpStatus.OK);
	}

	@DeleteMapping("/{policyId}")
	public ResponseEntity<ApiResponse> deletePolicyById(@PathVariable Integer policyId) {
		//ok
		return new ResponseEntity<>(new ApiResponse(AppConstants.MESSAGE), HttpStatus.OK);
	}

	@GetMapping("/{policyId}")
	public ResponseEntity<PolicyEntity> getPolicyById(@PathVariable Integer policyId) {
		//ok
		return new ResponseEntity<>(policyService.getPolicyById(policyId), HttpStatus.OK);
	}

	@GetMapping("/policyname/{name}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByName(@PathVariable(name = "name") String policyName) {
				//ok
		return new ResponseEntity<>(policyService.getPolicyName(policyName), HttpStatus.OK);
	}

	@GetMapping("/insurance/{max}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByInsuranceAmount(
			@PathVariable(name = "max") Double maxInsurance) {
		//ok
		return new ResponseEntity<>(policyService.getPolicyByInsuranceAmount(maxInsurance), HttpStatus.FOUND);
	}

	@GetMapping("/dAmount/{amount}")
	public ResponseEntity<List<PolicyEntity>> getPolicyByMonthlyDeductedAmout(
			@PathVariable(name = "amount") Long monthlyAmount) {
		//ok
		return new ResponseEntity<>(policyService.getPolicyByMonthlyDeductedAmout(monthlyAmount), HttpStatus.FOUND);
	}

}
