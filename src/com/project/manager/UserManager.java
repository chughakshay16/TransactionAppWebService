package com.project.manager;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.exceptions.UserNotFoundException;

public interface UserManager 
{
	//private ArrayList<User> users=null;
	public User deleteUser(Account account);
	public boolean modifyUser(Account account);
	public User getUser(Account account);
	//this simply creates the User object with the account property set to the parameter value
	public User createNewUser(Account account);
	public User reInitializeUser(Account account);
	public void addNewUser(User user);
	public void setUser(User user);
	public ArrayList<User> getUsers(ArrayList<Account> accounts);
	public ArrayList<ArrayList<Group>> getGroupsForEachUser(ArrayList<User> users);
	public boolean hasUser(Account acccount);
	public ArrayList<User> reInitializeUsers(ArrayList<Account> accounts);
	
}
