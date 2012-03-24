package com.project.dao;

import java.util.Date;

public interface CreateTablesForUser{
	//public boolean insertIntoAccounts(String username,String password,String firstName,String lastName,Date dateOfBirth,Date dateOfCreation,Date lastAccess,String userId,String sex,String phone);
	public boolean createUserAddressBook(String username);
	public boolean createUserGroups(String username);
	public boolean createUserGroupUsers(String username);
	public boolean createUserGroupTransactions(String username);
	public boolean createUserTransactions(String username);
}