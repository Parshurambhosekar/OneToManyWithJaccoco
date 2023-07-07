package com.parshuram.onetomany.serviceimpl;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger=LoggerFactory.getLogger(PolicyServiceImpl.class);
	
	@Override
	public PolicyEntity createPolicy(PolicyDto policyDto, Integer id) {
		
		var userEntity = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));
		
		logger.info("UserEntity {} ",userEntity+"userId"+id);
		
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

		logger.info("Policy Entity Saved...{} ",policyEntity);
		
		return policyRepository.save(policyEntity);
	}

	@Override
	public PolicyEntity updatePolicy(PolicyDto policyDto, Integer policyId, Integer id) {

		var userEntity = userRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.USER, AppConstants.USERID, id));

		logger.info("UserEntity {} ",userEntity+"userId"+id);
		
		var policyEntity = policyRepository.findById(policyId)
				.orElseThrow(()->new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId));
		
		logger.info("PolicyEntity {} ",policyEntity+"policyId"+policyId);
		
		policyEntity.setPolicyName(policyDto.getPolicyName());
		policyEntity.setMaxInsurance(policyDto.getMaxInsurance());
		policyEntity.setMonthlyAmountDeduction(policyDto.getMonthlyAmountDeduction());
		
		LocalDate startDate = policyDto.getStartDate();
		
		policyEntity.setStartDate(startDate);
		policyEntity.setEndDate(startDate.plusYears(1));
		policyEntity.setUserEntity(userEntity);
		
		logger.info("Updated Policy Entity {} ",policyEntity);
		
		return policyRepository.save(policyEntity);
	}

	@Override
	public List<PolicyEntity> getAllPolicies() {
		
		List<PolicyEntity> policyList = policyRepository.findAll();
		
		logger.info("List of All Polices With User Details ",policyList);
		
		return policyList;
	}

	@Override
	public void deletePolicy(Integer policyId) {

		if (policyRepository.existsById(policyId)) {
			
			logger.info("Policy Details is found with id "+policyId);
			
			policyRepository.deleteById(policyId);
		}
		else {
			
			logger.info("Exception Thrown...");
			
			throw new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId);
		}
		
	}
	
	@Override
	public PolicyEntity getPolicyById(Integer policyId) {
		
		PolicyEntity policyEntity = policyRepository.findById(policyId)
					.orElseThrow(()->new ResourceNotFoundException(AppConstants.POLICY, AppConstants.POLICYID, policyId));
		
		logger.info("Policy details with id"+policyId+" {} ",policyEntity);
		
		return policyEntity;
	}

	@Override
	public List<PolicyEntity> getPolicyName(String policyName) {

		List<PolicyEntity> policyByName = policyRepository.getPolicyByName(policyName);
		
		logger.info("policy Found with name"+policyName);
		
		if (policyByName.isEmpty()) {
			
			logger.info("Exception Thrown....");
			
			throw new ResourceNotFoundException(AppConstants.POLICY,AppConstants.POLICYNAME, policyName);
		}
		
		logger.info("Policy Details {} with Name ",policyByName);
		
		return policyByName;
	}

	@Override
	public List<PolicyEntity> getPolicyByInsuranceAmount(Double maxInsurance) {

		List<PolicyEntity> insurance = policyRepository.getByMaxInsurance(maxInsurance);
		
		logger.info("policy Found with Insurance"+maxInsurance);
		
		if (insurance.isEmpty()) {
			throw new ResourceNotFoundException(AppConstants.POLICY,AppConstants.INSURANCE, maxInsurance); 
		}
	
		logger.info("Policy Details {} with required Insurance Amount ",insurance);
		
		return insurance;
	}

	@Override
	public List<PolicyEntity> getPolicyByMonthlyDeductedAmout(Long monthlyAmount) {

		List<PolicyEntity> amount = policyRepository.getPoliciesByDeductedAmount(monthlyAmount);
		
		logger.info("policy Found with Mothly deduction Amount"+monthlyAmount);
		
		if(amount.isEmpty()) {
			
			logger.info("Exception Thrown...");
			
			throw new ResourceNotFoundException(AppConstants.POLICY, AppConstants.AMOUNT, (double)monthlyAmount);
		}
			
		logger.info("Policy Details {} with Mothly Deduction Amount",monthlyAmount);
		
		return amount;
	}

}
