package com.project.utils;

import java.util.ArrayList;

import com.project.dao.AccountDao;
import com.project.dao.AddressBookDao;
import com.project.dao.GroupDao;
import com.project.dao.SuggestionDao;
import com.project.dao.TransactionDao;
import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;
import com.project.dom.Group;
import com.project.dom.Suggestion;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.manager.User;

public class UserUtils 
{
	private TransactionDao transactionDao;
	private GroupDao groupDao;
	private AddressBookDao addressBookDao;
	private SuggestionDao suggestionDao;
	private AccountDao accountDao;

	
	public TransactionDao getTransactionDao() {
		return transactionDao;
	}
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public AddressBookDao getAddressBookDao() {
		return addressBookDao;
	}
	public void setAddressBookDao(AddressBookDao addressBookDao) {
		this.addressBookDao = addressBookDao;
	}
	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}
	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public static String getUserId(String username)
	{
		return username+"2011";
	}
	public String generateLastAccess()
	{
		return null;
	}
	public String generateDateOfCreation()
	{
		return null;
	}
	public ArrayList<Account> getCommonFriends(AddressBook user1addressBook,AddressBook user2addressBook)
	{
		return null;
	}
	public User reinitializeUser(Account account)
	{
		ArrayList<Friend> friends=null;
		ArrayList<Suggestion> suggestions=null;
		ArrayList<Transaction> transactions=null;
		ArrayList<Group> groups=null;
		try
		{
			friends=addressBookDao.getAll(account);
			suggestions =suggestionDao.getAll(account);
			transactions=transactionDao.getTransactions(account);
			groups=groupDao.getGroups(account);
		}
		catch(AccountNotFoundException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		AddressBook addressBook=new AddressBook();
		addressBook.setContacts(friends);
		//addressBook.setAppUsers(appUsers);
		User user=new User(account);
		user.setAddressBook(addressBook);
		user.setSuggestions(suggestions);
		user.setTransactions(transactions);
		user.setGroups(groups);
		return user;
	}
	public ArrayList<User> reinitializeUsers(ArrayList<Account> accounts)
	{
		ArrayList<User> tempUsers=new ArrayList<User>();
		for(int i=0;i<accounts.size();i++)
		{
			ArrayList<Friend> friends=null;
			ArrayList<Suggestion> suggestions=null;
			ArrayList<Transaction> transactions=null;
			ArrayList<Group> groups=null;
			try
			{
				friends=addressBookDao.getAll(accounts.get(i));
				if(friends==null)
				{
					friends=addressBookDao.getAll(accounts.get(i));
					if(friends==null)
						return null;
				}
				suggestions =suggestionDao.getAll(accounts.get(i));
				if(suggestions==null)
				{
					suggestions =suggestionDao.getAll(accounts.get(i));
					if(suggestions==null)
						return null;
				}
				transactions=transactionDao.getTransactions(accounts.get(i));
				if(transactions==null)
				{
					transactions=transactionDao.getTransactions(accounts.get(i));
					if(transactions==null)
						return null;
				}
				groups=groupDao.getGroups(accounts.get(i));
				if(groups==null)
				{
					groups=groupDao.getGroups(accounts.get(i));
					if(groups==null)
						return null;
				}
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			AddressBook addressBook=new AddressBook();
			addressBook.setContacts(friends);
			//addressBook.setAppUsers(appUsers);
			User user=new User(accounts.get(i));
			user.setAddressBook(addressBook);
			user.setSuggestions(suggestions);
			user.setTransactions(transactions);
			user.setGroups(groups);
			tempUsers.add(user);
		}
		return tempUsers;
	}
}
