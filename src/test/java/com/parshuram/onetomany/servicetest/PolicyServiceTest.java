package com.parshuram.onetomany.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.binding.UserDto;
import com.parshuram.onetomany.entity.PolicyEntity;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.exception.ResourceNotFoundException;
import com.parshuram.onetomany.repository.PolicyRepository;
import com.parshuram.onetomany.repository.UserRepository;
import com.parshuram.onetomany.serviceimpl.PolicyServiceImpl;

@SpringBootTest
class PolicyServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PolicyRepository policyRepository;

	@InjectMocks
	private PolicyServiceImpl policyServiceImpl;

	private PolicyEntity policyEntity;

	private PolicyDto policyDto;

	@BeforeEach
	void init() {

		policyEntity = new PolicyEntity(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra"));

		policyDto = new PolicyDto(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserDto(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra"));
	}

	@Test
	void testGetAllPolicyDetails() {

		List<PolicyEntity> listOfPolicyEntity = new ArrayList<>();
		listOfPolicyEntity.add(new PolicyEntity(123456, "Life Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(456789, "General Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra")));

		Mockito.when(policyRepository.findAll()).thenReturn(listOfPolicyEntity);

		List<PolicyEntity> allPolicies = policyServiceImpl.getAllPolicies();

		assertNotNull(allPolicies);

		assertThat(allPolicies.size()).isEqualTo(3);

		assertEquals(3, allPolicies.size());
	}

	@Test
	void testGetPolicyByIdForException() {

		when(policyRepository.findById(policyEntity.getPolicyId())).thenReturn(Optional.ofNullable(policyEntity));

		assertThrows(ResourceNotFoundException.class, () -> {
			policyServiceImpl.getPolicyById(3);
		});
	}

	@Test
	void testGetPolicyById() {

		Mockito.when(policyRepository.findById(policyEntity.getPolicyId()))
				.thenReturn(Optional.ofNullable(policyEntity));

		PolicyEntity policyById = policyServiceImpl.getPolicyById(122789);

		assertNotNull(policyById);

		assertEquals(122789, policyById.getPolicyId());

		assertThat(policyById.getPolicyId()).isEqualTo(122789);
	}

	@Test
	void testGetPolicyByName() {

		List<PolicyEntity> listOfPolicyEntity = new ArrayList<>();
		listOfPolicyEntity.add(new PolicyEntity(123456, "Life Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(456789, "General Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra")));

		when(policyRepository.getPolicyByName("Life Insurance")).thenReturn(listOfPolicyEntity);

		List<PolicyEntity> policyName = policyServiceImpl.getPolicyName("Life Insurance");

		assertNotNull(policyName);

		List<PolicyEntity> list = policyName.stream()
				.filter(policy -> policy.getPolicyName().equalsIgnoreCase("life insurance"))
				.collect(Collectors.toList());

		assertEquals(1, list.size());

		assertThrows(ResourceNotFoundException.class, () -> policyServiceImpl.getPolicyName("Motar Insurance"));
	}

	@Test
	void testDeletePolicy() {

		when(policyRepository.existsById(policyEntity.getPolicyId())).thenReturn(true);

		when(policyRepository.existsById(4567)).thenReturn(false);

		doNothing().when(policyRepository).deleteById(122789);

		policyServiceImpl.deletePolicy(122789);

		verify(policyRepository, times(1)).deleteById(122789);

		assertThrows(ResourceNotFoundException.class, () -> policyServiceImpl.deletePolicy(457899));
	}

	@Test
	void testGetPolicyByInsuranceAmount() {

		List<PolicyEntity> listOfPolicyEntity = new ArrayList<>();
		listOfPolicyEntity.add(new PolicyEntity(123456, "Life Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(456789, "General Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra")));

		when(policyRepository.getByMaxInsurance(500000.0)).thenReturn(listOfPolicyEntity);

		List<PolicyEntity> policyByInsuranceAmount = policyServiceImpl.getPolicyByInsuranceAmount(500000.0);

		List<PolicyEntity> list = policyByInsuranceAmount.stream()
				.filter(policy -> policy.getMaxInsurance().equals(500000.0)).collect(Collectors.toList());

		assertNotNull(list);

		assertEquals(2, list.size());

		assertThat(list.size()).isEqualTo(2);

		assertThrows(ResourceNotFoundException.class, () -> policyServiceImpl.getPolicyByInsuranceAmount(700000.0));
	}

	@Test
	void testGetPolicyByMonthlyDeductedAmount() {

		List<PolicyEntity> listOfPolicyEntity = new ArrayList<>();
		listOfPolicyEntity.add(new PolicyEntity(123456, "Life Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(456789, "General Insurance", 500000.0, 6000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuaram", "Bhosekar", "Pune", "9623771726", "Maharashtra")));
		listOfPolicyEntity.add(new PolicyEntity(122789, "Travel Insurance", 600000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra")));

		when(policyRepository.getPoliciesByDeductedAmount(5000l)).thenReturn(listOfPolicyEntity);

		List<PolicyEntity> policyByMonthlyDeductedAmout = policyServiceImpl.getPolicyByMonthlyDeductedAmout(5000l);

		List<PolicyEntity> list = policyByMonthlyDeductedAmout.stream()
				.filter(policy -> policy.getMonthlyAmountDeduction().equals(5000l)).collect(Collectors.toList());

		assertNotNull(list);

		assertEquals(1, list.size());

		assertThat(list.size()).isEqualTo(1);

		assertThrows(ResourceNotFoundException.class, () -> policyServiceImpl.getPolicyByMonthlyDeductedAmout(7000l));
	}

	@Test
	void testCreatePolicy() {

		UserEntity userEntity = new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra");

		Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(userEntity));
		
		when(policyRepository.save(policyEntity)).thenReturn(policyEntity);
		
		assertThrows(ResourceNotFoundException.class, ()->policyServiceImpl.createPolicy(policyDto, 2));
		
		//saving assertion pending
	}

	@Test
	void testUpdatePolicy() {

		UserEntity userEntity = new UserEntity(1, "Suchitra", "Patil", "Sangli", "9623778937", "Maharashtra");

		when(userRepository.findById(1)).thenReturn(Optional.ofNullable(userEntity));

		when(policyRepository.findById(122789)).thenReturn(Optional.ofNullable(policyEntity));

		when(policyRepository.save(policyEntity)).thenReturn(policyEntity);
		
		policyDto.setPolicyName("Life Insurance");
		policyDto.setMonthlyAmountDeduction(7000l);
		
		PolicyEntity updatePolicy = policyServiceImpl.updatePolicy(policyDto, 122789, 1);
		
		assertNotNull(updatePolicy);
		
		assertEquals("Life Insurance", updatePolicy.getPolicyName());
		
		assertThat(updatePolicy.getMonthlyAmountDeduction()).isEqualTo(7000);
		
		assertThrowsExactly(ResourceNotFoundException.class,()->policyServiceImpl.updatePolicy(policyDto, 122478, 2));

		assertThrows(ResourceNotFoundException.class, ()->policyServiceImpl.updatePolicy(policyDto, 124789, 1).getPolicyId());
	}

}
