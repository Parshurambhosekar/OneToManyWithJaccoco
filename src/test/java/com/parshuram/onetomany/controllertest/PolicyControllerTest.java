package com.parshuram.onetomany.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parshuram.onetomany.binding.PolicyDto;
import com.parshuram.onetomany.controller.PolicyController;
import com.parshuram.onetomany.entity.PolicyEntity;
import com.parshuram.onetomany.entity.UserEntity;
import com.parshuram.onetomany.service.PolicyService;

@WebMvcTest(value = PolicyController.class)
class PolicyControllerTest {

	@MockBean
	private PolicyService policyService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testCreatePolicy() throws Exception {

		PolicyDto policyDto = new PolicyDto(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1));

		PolicyEntity policyEntity = new PolicyEntity();

		BeanUtils.copyProperties(policyDto, policyEntity);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		// use that because java 1.8 LocalDate not supported
		// and 1 dependency added.//jackson-datatype-jsr310
		String valueAsString = mapper.writeValueAsString(policyEntity);

		when(policyService.createPolicy(policyDto, 251254)).thenReturn(policyEntity);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/policy/{userId}", "251254")
				.contentType(MediaType.APPLICATION_JSON).content(valueAsString);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(201, status);

		assertEquals(valueAsString, response.getContentAsString());

		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testUpdatePolicy() throws Exception {

		PolicyDto policyDto = new PolicyDto(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1));

		PolicyEntity policyEntity = new PolicyEntity();

		BeanUtils.copyProperties(policyDto, policyEntity);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String valueAsString = mapper.writeValueAsString(policyEntity);

		when(policyService.updatePolicy(policyDto, 251254, 1)).thenReturn(policyEntity);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/policy/251254/1")
				.contentType(MediaType.APPLICATION_JSON).content(valueAsString);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(202, status);

		assertEquals("application/json", response.getContentType());

		assertEquals(valueAsString, response.getContentAsString());
	}

	@Test
	void testGetAllPolicyDetails() throws Exception {

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

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policy/");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testDeletePolicyById() throws Exception {

		policyService.deletePolicy(256412);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/policy/{policyId}", "256412");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testGetPolicyByName() throws Exception {

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

		when(policyService.getPolicyName("Life Insurance")).thenReturn(policyEntity);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policy/policyname/{name}",
				"Life Insurance");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testGetPolicyByInsuranceAmount() throws Exception {

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

		when(policyService.getPolicyByInsuranceAmount(5000000.0)).thenReturn(policyEntity);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policy//insurance/{max}",
				"5000000.0");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(302, status);
	}

	@Test
	void testGetPolicyByMonthlyDeduction() throws Exception {

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

		when(policyService.getPolicyByMonthlyDeductedAmout(5000l)).thenReturn(null);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policy/dAmount/{amount}", "5000");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		int status = response.getStatus();

		assertEquals(302, status);
	}
	
	@Test
	void testGetPolicyById() throws Exception {
		
	  PolicyEntity policyEntity=new PolicyEntity(251254, "Life Insurance", 5000000.0, 5000l, LocalDate.now(),
				LocalDate.now().plusYears(1),
				new UserEntity(1, "Parshuram", "Bhosekar", "Pune", "9623771726", "Maharashtra"));
		
	   when(policyService.getPolicyById(251254)).thenReturn(policyEntity);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policy/{policyId}",251254);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		assertEquals(200, status);
		
		assertEquals("application/json", response.getContentType());
	}

	
	
	
	
	
	
}
