package com.project.business;

import java.util.ArrayList;
import java.util.Date;

import com.project.dom.Friend;

public interface Register 
{
	public String register(String username,String password,String firstName,String lastName,Date dob,String gender,String phoneNo);
	public boolean authenticate(String userName,String password);
	public boolean registerNext(String userId,ArrayList<Friend> friends);
}
