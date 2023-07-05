package com.parshuram.onetomany.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 String resourceName;
	 String fieldName;
	 Integer fieldValue;
	 Double amount;
	 String name;
	 Long amountDedect;

	public ResourceNotFoundException(String resourceName,String fieldName,Integer fieldValue) {
		super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName=resourceName;
		this.fieldName=fieldName;
		this.fieldValue=fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName,String fieldName,String name) {
		super(String.format("%s is not found with %s : %s",resourceName,fieldName,name));
		this.resourceName=resourceName;
		this.fieldName=fieldName;
		this.name=name;
	}
	

	public ResourceNotFoundException(String resourceName,String fieldName,Double amount) {
		super(String.format("%s is not found with %s : %s",resourceName,fieldName,amount));
		this.resourceName=resourceName;
		this.fieldName=fieldName;
		this.amount=amount;
	}
	
	
	
	
}
