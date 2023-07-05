package com.parshuram.onetomany.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.controller.PolicyController;
import com.parshuram.onetomany.entity.PolicyEntity;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.service.PolicyService;
import com.parshuram.onetomany.utils.ApiResponse;

@SpringBootTest
class PolicyControllerMockTest {

	@Mock
	private PolicyService policyService;

	@InjectMocks
	private PolicyController policyController;

	@Test
	void testCreatePolicy() {

		PolicyDto policyDto = new PolicyDto(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1));

		PolicyEntity policyEntity = new PolicyEntity();

		BeanUtils.copyProperties(policyDto, policyEntity);

		when(policyService.createPolicy(policyDto, 251254)).thenReturn(policyEntity);

		ResponseEntity<PolicyEntity> responseEntity = policyController.createPolicy(policyDto, 251254);

		assertEquals(policyEntity, responseEntity.getBody());

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		assertEquals(201, responseEntity.getStatusCodeValue());
	}

	@Test
	void testUpdatePolicy() {

		PolicyDto policyDto = new PolicyDto(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserDto(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));

		PolicyEntity policyEntity = new PolicyEntity();

		BeanUtils.copyProperties(policyDto, policyEntity);

		when(policyService.updatePolicy(policyDto, 251254, 1)).thenReturn(policyEntity);

		ResponseEntity<PolicyEntity> responseEntity = policyController.updatePolicy(policyDto, 251254, 1);

		assertEquals(policyEntity, responseEntity.getBody());

		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

		assertEquals(202, responseEntity.getStatusCodeValue());
	}

	@Test
	void testGetAllPolicyDetails() {

		List<PolicyEntity> policyEntity = new ArrayList<>();
		policyEntity.add(new PolicyEntity(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		policyEntity.add(new PolicyEntity(278963, "General Insurance", 4000000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623778917", "Maharashtra")));
		policyEntity.add(new PolicyEntity(263975, "Travel Insurance", 5000000.0, 5500l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));

		when(policyService.getAllPolicies()).thenReturn(policyEntity);

		ResponseEntity<List<PolicyEntity>> responseEntity = policyController.getAllPolicyDetails();

		assertEquals(3, responseEntity.getBody().size());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(200, responseEntity.getStatusCodeValue());

		assertEquals(policyEntity, responseEntity.getBody());
	}

	@Test
	void testDeletePolicy() {

		policyService.deletePolicy(251254);

		ResponseEntity<ApiResponse> responseEntity = policyController.deletePolicyById(251254);

		assertEquals("Deleted Successfully.....", responseEntity.getBody().getMessage());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testGetPolicyById() {

		PolicyEntity policyEntity = new PolicyEntity(263975, "Travel Insurance", 5000000.0, 5500l, LocalDate.now(),
				LocalDate.now().plusYears(1));

		when(policyService.getPolicyById(263975)).thenReturn(policyEntity);

		ResponseEntity<PolicyEntity> responseEntity = policyController.getPolicyById(263975);

		assertEquals(policyEntity, responseEntity.getBody());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testGetPolicyByName() {

		List<PolicyEntity> policyEntity = new ArrayList<>();
		policyEntity.add(new PolicyEntity(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		policyEntity.add(new PolicyEntity(278963, "Travel Insurance", 4000000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623778917", "Maharashtra")));
		policyEntity.add(new PolicyEntity(263975, "Travel Insurance", 6000000.0, 5500l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));

		when(policyService.getPolicyName("Travel Insurance")).thenReturn(policyEntity);

		ResponseEntity<List<PolicyEntity>> responseEntity = policyController.getPolicyByName("Travel Insurance");

		List<PolicyEntity> policyList = responseEntity.getBody().stream()
				.filter(emp -> emp.getPolicyName().equalsIgnoreCase("Travel Insurance")).collect(Collectors.toList());

		assertEquals(2, policyList.size());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	void testGetPolicyByInsuranceAmount() {

		List<PolicyEntity> policyEntity = new ArrayList<>();
		policyEntity.add(new PolicyEntity(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		policyEntity.add(new PolicyEntity(278963, "Travel Insurance", 4000000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623778917", "Maharashtra")));
		policyEntity.add(new PolicyEntity(263975, "Travel Insurance", 6000000.0, 5500l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));

		when(policyService.getPolicyByInsuranceAmount(5000000.0)).thenReturn(policyEntity);

		ResponseEntity<List<PolicyEntity>> responseEntity = policyController.getPolicyByInsuranceAmount(5000000.0);

		List<PolicyEntity> policyByInsuranceAmount = responseEntity.getBody().stream()
				.filter(emp -> emp.getMaxInsurance().equals(5000000.0)).collect(Collectors.toList());

		assertEquals(1, policyByInsuranceAmount.size());

		assertEquals(302, responseEntity.getStatusCodeValue());

		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testGetPolicyByMonthlyDeductedAmount() {

		List<PolicyEntity> policyEntity = new ArrayList<>();
		policyEntity.add(new PolicyEntity(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		policyEntity.add(new PolicyEntity(278963, "Travel Insurance", 4000000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(2, "Sumit", "Bhosale", "Pune", "9623778917", "Maharashtra")));
		policyEntity.add(new PolicyEntity(263975, "Travel Insurance", 6000000.0, 5500l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));

		when(policyService.getPolicyByMonthlyDeductedAmout(5000l)).thenReturn(policyEntity);

		ResponseEntity<List<PolicyEntity>> responseEntity = policyController.getPolicyByMonthlyDeductedAmout(5000l);
		
		List<PolicyEntity> list = responseEntity.getBody().stream()
					.filter(policy->policy.getMonthlyAmountDeduction().equals(5000l))
					.collect(Collectors.toList());
		
		assertEquals(1, list.size());
		
		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
		
		assertEquals(302, responseEntity.getStatusCodeValue());
	}

}
