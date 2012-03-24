package com.project.manager;

import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.login.AccountNotFoundException;

import com.project.dom.Account;
import com.project.exceptions.AccountAlreadyPresentException;
//import com.project.exceptions.AccountNotPresentException;
import com.project.exceptions.HashtablesMismatchException;

public interface AccountManager 
{
	public void resetAccounts(ArrayList<Account> accounts);
	public Account getAccountByUserName(String username);//while retreiving change the lastAccess value
	public Account getAccountByUserId(String userId);//while retreiving change the lastAccess value 
	//this simply creates the Account object with the other values not mentioned in the parameters
	public Account createAccount(String username,String password,String firstname,String lastname,Date dob,String gender,String phone1);//create the values for doc,lastAccess,userId
	public Account deleteAccountWithUsername(String username);
	public Account deleteAccountWithUserId(String userId);
	public boolean validateAccount(Account account);
	public ArrayList<Account> getAccountsByUserNames(String[] usernames);
	public ArrayList<Account> getAccountsByUserIds(String[] userIds);
	public void addAccount(Account account)throws AccountAlreadyPresentException;
	public void setAccount(Account account);
	public ArrayList<Account> getAccounts();
	public Account getByPhone(String phone1);
	
	// we can put some other utility methods as per our needs
	
}
// class implementing this should be a singleton