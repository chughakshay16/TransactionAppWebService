package com.project.xml;

import java.util.ArrayList;

import com.project.dom.Account;

public class AccountCollection 
{
	private ArrayList<Account> accounts;

	public AccountCollection()
	{}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
}
