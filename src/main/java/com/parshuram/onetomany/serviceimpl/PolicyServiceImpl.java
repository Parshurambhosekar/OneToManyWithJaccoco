package com.parshuram.onetomany.serviceimpl;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.constants.AppConstants;
import com.parshuram.onetomany.entity.PolicyEntity;
import com.parshuram.onetomany.exception.ResourceNotFoundException;
import com.parshuram.onetomany.repository.PolicyRepository;
import com.parshuram.onetomany.repository.UserRepository;
import com.parshuram.onetomany.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PolicyRepository policyRepository;
	
	@Override
	public PolicyEntity createPolicy(PolicyDto policyDto, Integer id) {

		var userEntity = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));
	
		var random=new SecureRandom();
		
		var policyId = random.nextInt(1000, 80000);
		
		var policyEntity=new PolicyEntity();
		
		policyEntity.setUserEntity(userEntity);
		policyEntity.setPolicyId(policyId);
		policyEntity.setEndDate(policyDto.getStartDate().plusYears(1));
		policyEntity.setPolicyName(policyDto.getPolicyName());
		policyEntity.setStartDate(policyDto.getStartDate());
		policyEntity.setMaxInsurance(policyDto.getMaxInsurance());
		policyEntity.setMonthlyAmountDeduction(policyDto.getMonthlyAmountDeduction());
		
		return policyRepository.save(policyEntity);
	}

	@Override
	public PolicyEntity updatePolicy(PolicyDto policyDto, Integer policyId, Integer id) {

		var userEntity = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));
	
		var policyEntity = policyRepository.findById(policyId)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId));
		
		policyEntity.setPolicyName(policyDto.getPolicyName());
		policyEntity.setMaxInsurance(policyDto.getMaxInsurance());
		policyEntity.setMonthlyAmountDeduction(policyDto.getMonthlyAmountDeduction());
		
		LocalDate startDate = policyDto.getStartDate();
		
		policyEntity.setStartDate(startDate);
		policyEntity.setEndDate(startDate.plusYears(1));
		policyEntity.setUserEntity(userEntity);
		
		return policyRepository.save(policyEntity);
	}

	@Override
	public List<PolicyEntity> getAllPolicies() {
		
		return policyRepository.findAll();
	}

	@Override
	public void deletePolicy(Integer policyId) {

		if (policyRepository.existsById(policyId)) {
			policyRepository.deleteById(policyId);
		}
		else {
			throw new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId);
		}
		
	}
	
	@Override
	public PolicyEntity getPolicyById(Integer policyId) {
		return policyRepository.findById(policyId)
						.orElseThrow(()->new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId));
	}

	@Override
	public List<PolicyEntity> getPolicyName(String policyName) {

		List<PolicyEntity> policyByName = policyRepository.getPolicyByName(policyName);
		
		if (policyByName.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.POLICY,AppConstants.POLICYNAME, policyName);
		}
		
		return policyByName;
	}

	@Override
	public List<PolicyEntity> getPolicyByInsuranceAmount(Double maxInsurance) {

		List<PolicyEntity> insurance = policyRepository.getByMaxInsurance(maxInsurance);
		
		if (insurance.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.POLICY,AppConstants.INSURANCE, maxInsurance); 
		}
		
		return insurance;
	}

	@Override
	public List<PolicyEntity> getPolicyByMonthlyDeductedAmout(Long monthlyAmount) {

		List<PolicyEntity> amount = policyRepository.getPoliciesByDeductedAmount(monthlyAmount);
		
		if(amount.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.POLICY, AppConstants.AMOUNT, (double)monthlyAmount);
		}
			
		return amount;
	}

}
