package com.project.dom;

public class Friend 
{
	//private String name;
	//private String username ="DEFAULT";
	private String phone1;
	private String firstName;
	private String lastName;
	//private String address;
	//private boolean isUser;
	public Friend(){}
	public Friend(String firstName,String lastName,String phone1)
	{
		this.firstName=firstName;
		this.lastName=lastName;
		this.phone1=phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	

	
		
}
