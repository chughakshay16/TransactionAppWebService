package com.project.dom;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.project.utils.UserUtils;

@XmlType(propOrder={"username", "password","firstName","lastName","dateOfBirth","dateOfCreation","lastAccess","gender","userId","phone1"})
public class Account 
{
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Date dateOfCreation;
	private Date lastAccess;
	private String gender;
	private String userId;
	private String phone1;
	public Account(){
		
	}
	public Account(String username,String password,String firstName,String lastName,Date dob,String gender,String phone1)
	{
		this.username=username;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
		this.dateOfBirth=dob;
		this.dateOfCreation=this.lastAccess=new Date();
		this.gender=gender;
		this.phone1=phone1;
		this.userId=UserUtils.getUserId(username);
		
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
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
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	public Date getLastAccess() {
		return lastAccess;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender() {
		return gender;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone1() {
		return phone1;
	}
	
	
	
}
