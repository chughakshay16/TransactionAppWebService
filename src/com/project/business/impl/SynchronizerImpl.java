package com.project.business.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.Synchronizer;
import com.project.dao.AccountDao;
import com.project.dao.AddressBookDao;
import com.project.dao.GroupDao;
import com.project.dao.SuggestionDao;
import com.project.dao.TransactionDao;
import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;
//bean injection still needs to be done
public class SynchronizerImpl implements Synchronizer,InitializingBean {

	private TransactionDao transactionDao=null;
	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private GroupDao groupDao=null;
	private AddressBookDao addressBookDao=null;
	private SuggestionDao suggestionDao=null;
	private AccountDao accountDao=null;
	private UserUtils utils=null;
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}

	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}

	public AddressBookDao getAddressBookDao() {
		return addressBookDao;
	}

	public void setAddressBookDao(AddressBookDao addressBookDao) {
		this.addressBookDao = addressBookDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public User synchronize(String userId) {

		Account accountUser = accountManager.getAccountByUserId(userId);
		if(accountUser==null)
		{
			try
			{
				accountUser=accountDao.getByUserId(userId);
				if(accountUser==null)
					return null;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			
		}

		User user=null;
		if(!userManager.hasUser(accountUser))
		{
			user=utils.reinitializeUser(accountUser);
			if(user==null)
			{
				//throw exception
				return null;
			}
		}
		user = userManager.getUser(accountUser);
		user.modifyPendingStatusForNotifications(false);
			//ArrayList<Notification> tempNotifications=new ArrayList<Notification>(user.getNotifications());
			//userManager.deleteUser(accountUser);
			//User newUser=userManager.createNewUser(accountUser);
			//newUser.setNotifications(tempNotifications);
			//AddressBook book=new AddressBook();
			//book.setAppUsers();
			//book.setContacts(addressBookDao.getAll(accountUser));
			//newUser.setAddressBook(book);
			try
			{
				ArrayList<Transaction> tempTs=transactionDao.getTransactions(accountUser);
				if(tempTs==null)
				{
					tempTs=transactionDao.getTransactions(accountUser);
					if(tempTs==null)
					{
						return null;
					}
				}
				user.setTransactions(tempTs);
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			try
			{
				ArrayList<Suggestion> tempSs=suggestionDao.getAll(accountUser);
				if(tempSs==null)
				{
					tempSs=suggestionDao.getAll(accountUser);
					if(tempSs==null)
					{
						return null;
					}
				}
				
				user.setSuggestions(tempSs);
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			try
			{
				ArrayList<Group> tempGs=groupDao.getGroups(accountUser);
				if(tempGs==null)
				{
					tempGs=groupDao.getGroups(accountUser);
					if(tempGs==null)
					{
						return null;
					}
				}
				
				user.setGroups(tempGs);
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}

			userManager.setUser(user);
			return user;
		
		
	}

	@Override
	public Object asynchronousProcess(String userId, String contentType) {
		// TODO Auto-generated method stub
		
		Account userAccount = accountManager.getAccountByUserId(userId);
		if(userAccount==null)
		{
			try
			{
				userAccount=accountDao.getByUserId(userId);
				if(userAccount==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		User user=null;
		if(!userManager.hasUser(userAccount))
		{
			user=utils.reinitializeUser(userAccount);
			if(user==null)
			{
				//throw exception
				return null;
			}
		}
		user = userManager.getUser(userAccount);
		if(contentType.equals("transactions"))
		{
			try
			{
				ArrayList<Transaction> tempTs=transactionDao.getTransactions(userAccount);
				if(tempTs==null)
				{
					tempTs=transactionDao.getTransactions(userAccount);
					if(tempTs==null)
						return null;
				}
				user.setTransactions(tempTs);
				userManager.setUser(user);
				return user.getTransactions();
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
			}
			//return transactions;
		}
		if(contentType.equals("notifications"))
		{
			//this will make changes to the pending status of all the notifications
			user.modifyPendingStatusForNotifications(false);
			userManager.setUser(user);
			//now we will make changes to the suggestions table and to the sender object(modify pending status)
			ArrayList<Notification> tempNotifications=user.getNotifications();
			for(int i=0;i<tempNotifications.size();i++)
			{
				Account senderAccount=tempNotifications.get(i).getSender();
				User sender;
				if(!userManager.hasUser(senderAccount))
				{
					sender=utils.reinitializeUser(senderAccount);
					if(user==null)
					{
						//throw exception
						return null;
					}
				}
				sender=userManager.getUser(senderAccount);
				sender.modifyPendingStatusForSuggestion(tempNotifications.get(i).getSuggestionId(),false,tempNotifications.get(i).getOwnerId());
				try
				{
					boolean success=suggestionDao.modifySuggestionReceiver(tempNotifications.get(i),"pendingStatus");
					if(!success)
					{
						success=suggestionDao.modifySuggestionReceiver(tempNotifications.get(i),"pendingStatus");
						if(!success)
						{
							return null;
						}
					}
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					return null;
				}
			}
			return user.getNotifications();
			
		}
		if(contentType.equals("groups"))
		{
			//ArrayList<Group> tempGs=null;
			try
			{
				ArrayList<Group> tempGs=groupDao.getGroups(userAccount);
				if(tempGs==null)
				{
					tempGs=groupDao.getGroups(userAccount);
					if(tempGs==null)
					{
						return null;
					}
				}
				user.setGroups(tempGs);
				userManager.setUser(user);
				return user.getGroups();
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			
		}
		//remember one thing we will never delete any user's addressbook
		if(contentType.equals("appUsers"))
		{
			return user.getAppUsers();
		}
		return null;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing beans for SynchronizerImpl");
		if(accountManager==null||userManager==null||accountDao==null||addressBookDao==null||transactionDao==null||groupDao==null||suggestionDao==null||accountDao==null||utils==null)
		{
			throw new NullPointerException("thrown from SynchronizerImpl");
		}
	}

	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}

	public UserUtils getUtils() {
		return utils;
	}

}
