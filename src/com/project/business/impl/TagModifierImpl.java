package com.project.business.impl;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.TagModifier;
import com.project.dao.AccountDao;
import com.project.dao.TransactionDao;
import com.project.dom.Account;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.TransactionNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;

public class TagModifierImpl implements TagModifier,InitializingBean {

	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private TransactionDao transactionDao=null;
	private AccountDao accountDao=null;
	private UserUtils utils=null;
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	
	@Override
	public boolean addTag(String tag, String transactionId, String userId) {
		// TODO Auto-generated method stub
		Account userAccount=accountManager.getAccountByUserId(userId);
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
		User user;//=userManager.getUser(userAccount);
		if(userManager.hasUser(userAccount))
		{
			user=utils.reinitializeUser(userAccount);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user=userManager.getUser(userAccount);
		user.addTagToTransaction(tag,transactionId);
		boolean success=false;
		try
		{
			success=transactionDao.modifyTransaction(user.getTransaction(transactionId), "taglist", userAccount);
			if(!success)
			{
				transactionDao.modifyTransaction(user.getTransaction(transactionId), "taglist", userAccount);
				if(!success)
				{
					//throw exception
					return false;
				}
			}
		}
		catch(AccountNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		catch(TransactionNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return success;
	}

	@Override
	public boolean deleteTag(String tag, String transactionId, String userId) {
		// TODO Auto-generated method stub
		Account userAccount=accountManager.getAccountByUserId(userId);
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
		User user;//=userManager.getUser(userAccount);
		if(userManager.hasUser(userAccount))
		{
			user=utils.reinitializeUser(userAccount);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user=userManager.getUser(userAccount);
		user.deleteTagFromTransaction(tag,transactionId);
		//return transactionDao.modifyTransaction(user.getTransaction(transactionId), "taglist", userAccount);
		boolean success=false;
		try
		{
			success=transactionDao.modifyTransaction(user.getTransaction(transactionId), "taglist", userAccount);
			if(!success)
			{
				transactionDao.modifyTransaction(user.getTransaction(transactionId), "taglist", userAccount);
				if(!success)
				{
					//throw exception
					return false;
				}
			}
		}
		catch(AccountNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		catch(TransactionNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return success;
	}

	
	//this method doesnt make any sense(remove it)
	@Override
	public boolean updateTag(String newTag, String transactionId, String userId) {
		// TODO Auto-generated method stub
		Account userAccount=accountManager.getAccountByUserId(userId);
		User user=userManager.getUser(userAccount);
		
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing beans for TagModifierImpl");
		if(accountManager==null||userManager==null||accountDao==null||accountDao==null||transactionDao==null||utils==null)
		{
			throw new NullPointerException("thrown from TagModifierImpl");
		}
	}

	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}

	public UserUtils getUtils() {
		return utils;
	}

}
