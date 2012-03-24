package com.project.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.springframework.beans.factory.InitializingBean;

import com.project.dom.Account;
import com.project.exceptions.AccountAlreadyPresentException;
import com.project.manager.AccountManager;
//here the bean injection is yet to be done
public class AccountManagerImpl implements AccountManager,InitializingBean{

	private Hashtable<String,Account> userIdHashtable;
	public Hashtable<String, Account> getUserIdHashtable() {
		return userIdHashtable;
	}

	public void setUserIdHashtable(Hashtable<String, Account> userIdHashtable) {
		this.userIdHashtable = userIdHashtable;
	}

	public Hashtable<String, Account> getUserNameHashtable() {
		return userNameHashtable;
	}

	public void setUserNameHashtable(Hashtable<String, Account> userNameHashtable) {
		this.userNameHashtable = userNameHashtable;
	}

	private Hashtable<String,Account> userNameHashtable;
	@Override
	public void resetAccounts(ArrayList<Account> accounts) {
		// TODO Auto-generated method stub
		userIdHashtable.clear();
		for(int i=0;i<accounts.size();i++)
		{
			userIdHashtable.put(accounts.get(i).getUserId(), accounts.get(i));
		}
	}

	@Override
	public Account getAccountByUserName(String username) {
		// TODO Auto-generated method stub
		
		return userNameHashtable.get(username);
	}

	@Override
	public Account getAccountByUserId(String userId) {
		// TODO Auto-generated method stub
		return userIdHashtable.get(userId);
	}

	@Override
	public Account createAccount(String username, String password,
			String firstname, String lastname, Date dob, String gender,
			String phone1) {
		// TODO Auto-generated method stub
        // constructor sets all the other values not passed here(such as userId,doc,lastAccess)
		System.out.println("thank you once again");
		return new Account(username,password,firstname,lastname,dob,gender,phone1);
	}
	
	public Account createAccount(String username, String password, String firstName, String lastName,
								Date dateOfBirth, Date dateOfCreation, Date lastAccess, String userId,
								String gender, String phone1){
		Account returnAccount = new Account();
		returnAccount.setDateOfBirth(dateOfBirth);
		returnAccount.setDateOfCreation(dateOfCreation);
		returnAccount.setFirstName(firstName);
		returnAccount.setGender(gender);
		returnAccount.setLastAccess(lastAccess);
		returnAccount.setLastName(lastName);
		returnAccount.setPassword(password);
		returnAccount.setPhone1(phone1);
		returnAccount.setUserId(userId);
		returnAccount.setUsername(username);
		
		return returnAccount;
	}

	@Override
	public Account deleteAccountWithUsername(String username) {
		// TODO Auto-generated method stub
		//Account temp=userNameHashtable.remove(username);
		userIdHashtable.remove(userNameHashtable.remove(username).getUserId());
		return null;
	}

	@Override
	public Account deleteAccountWithUserId(String userId) {
		// TODO Auto-generated method stub
		userNameHashtable.remove(userIdHashtable.remove(userId).getUsername());
		return null;
	}

	@Override
	public boolean validateAccount(Account account) {
		// TODO Auto-generated method stub
		return userIdHashtable.containsKey(account.getUserId())&&userNameHashtable.containsKey(account.getUsername());
		
	}

	@Override
	public ArrayList<Account> getAccountsByUserNames(String[] usernames) {
		// TODO Auto-generated method stub
		ArrayList<Account> tempAccounts=new ArrayList<Account>();
		for(int i=0;i<usernames.length;i++)
		{
			tempAccounts.add(userNameHashtable.get(usernames[i]));
		}
		return tempAccounts;
	}

	@Override
	public ArrayList<Account> getAccountsByUserIds(String[] userIds) {
		// TODO Auto-generated method stub
		ArrayList<Account> tempAccounts=new ArrayList<Account>();
		for(int i=0;i<userIds.length;i++)
		{
			tempAccounts.add(userIdHashtable.get(userIds[i]));
		}
		return tempAccounts;
	}

	@Override
	public void addAccount(Account account) throws AccountAlreadyPresentException{
		    System.out.println("its working");
		    System.out.println(account.getUserId());
		    if(userIdHashtable.containsKey(account.getUserId())||userNameHashtable.containsKey(account.getUsername()))
			throw new AccountAlreadyPresentException("account already present");
		userIdHashtable.put(account.getUserId(), account);
		userNameHashtable.put(account.getUsername(),account);
	}

	@Override
	public void setAccount(Account account) {
		// TODO Auto-generated method stub
		userIdHashtable.put(account.getUserId(),account);
		userNameHashtable.put(account.getUsername(), account);
	}

	@Override
	public ArrayList<Account> getAccounts() {
		// TODO Auto-generated method stub
		ArrayList<Account> accounts=new ArrayList<Account>();
		Enumeration<Account> e=userIdHashtable.elements();
		while(e.hasMoreElements())
		{
			accounts.add(e.nextElement());
		}
		return null;
	}

	@Override
	public Account getByPhone(String phone1) {
		// TODO Auto-generated method stub
		//if(userIdHashtable.size()!=userNameHashtable.size())
			//throw new HashtablesMismatchException();
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing AccountManager");
		if(userIdHashtable==null||userNameHashtable==null)
		{
			System.out.println("omg");
		}
	}

	

}
