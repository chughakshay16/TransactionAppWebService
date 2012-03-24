package com.project.dao;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.AllAccountsNotFoundException;

public interface AccountDao 
{
	
	public Account getByUsername(String userName)throws AccountNotFoundException;
	public Account getByUserId(String userId)throws AccountNotFoundException;
	public Account getByPhoneNo(String phoneNo);
	public ArrayList<Account> getAll();//gets back all the accounts stored in the table
	public boolean addNewAccount(Account account);//this adds a new row in table
	public boolean deleteAccount(Account account);//this deletes a row in table
	public boolean modifyLastAccess(Account account);//this modifies the column in any given row
	public ArrayList<Account> getAccountsByUsername(String[] usernames)throws AllAccountsNotFoundException;
	
}
