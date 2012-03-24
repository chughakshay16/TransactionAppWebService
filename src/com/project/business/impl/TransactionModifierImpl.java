package com.project.business.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.TransactionModifier;
import com.project.dao.AccountDao;
import com.project.dao.GroupDao;
import com.project.dao.TransactionDao;
import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.GroupNotFoundException;
import com.project.exceptions.TransactionNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.TransactionCreator;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;
//bean injection still needs to be done
public class TransactionModifierImpl implements InitializingBean,
		TransactionModifier {
	private TransactionDao transactionDao=null;
	private TransactionCreator creator=null;
	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private GroupDao groupDao=null;
	private AccountDao accountDao=null;
	private UserUtils utils=null;
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	//this function not mantains a check whether all the dao calls are performed as expected(so do fix this)
	@Override
	public String addTransaction(String senderUserId, String receiverUserId,
			Date date, float amount, String taglist, String description) {
		
		//Account senderAccount=accountManager.getAccountByUserId(senderUserId);
		Account senderAccount = accountManager.getAccountByUserId(senderUserId);
		if(senderAccount==null)
		{
			try
			{
				senderAccount=accountDao.getByUserId(senderUserId);
				if(senderAccount==null)
					return null;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			
		}
		Account receiverAccount=accountManager.getAccountByUserId(receiverUserId);
		if(receiverAccount==null)
		{
			try
			{
				receiverAccount=accountDao.getByUserId(receiverUserId);
				if(receiverAccount==null)
					return null;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return null;
			}
			
		}
		User sender;//=userManager.getUser(senderAccount);
		if(userManager.hasUser(senderAccount))
		{
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return null;
			}
		}
		sender=userManager.getUser(senderAccount);
		User receiver;//=userManager.getUser(receiverAccount);
		if(userManager.hasUser(receiverAccount))
		{
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return null;
			}
		}
		receiver=userManager.getUser(receiverAccount);
		ArrayList<Account> commonFriendsAccounts=utils.getCommonFriends(sender.getAddressBook(), receiver.getAddressBook());
		ArrayList<User> commonFriends=userManager.getUsers(commonFriendsAccounts);
		if(commonFriends==null)
		{
			commonFriends=userManager.reInitializeUsers(commonFriendsAccounts);
			//fix this
		}
		commonFriends.add(sender);
		commonFriends.add(receiver);
		// there is no need to mantain a check here(we fetched the user objects from userManager only)
		ArrayList<ArrayList<Group>> groups=userManager.getGroupsForEachUser(commonFriends);//remember last two members are sender and receiver
		//ArrayList<Transaction> allNewTransactions=new ArrayList<Transaction>();
		String transactionId="";
		for(int i=0;i<commonFriends.size();i++)
		{
			//allNewTransactions.add(creator.createTransaction(null, senderAccount, receiverAccount, date, amount, taglist, description, groups.get(i)));
			//Transaction temp=allNewTransactions.get(i);
			Transaction temp=creator.createTransaction(null, senderAccount, receiverAccount, date, amount, taglist, description, groups.get(i));
			transactionId=transactionDao.addTransaction(temp,commonFriendsAccounts.get(i));//will only modify username_transactions
			if(transactionId==null)
			{
				transactionId=transactionDao.addTransaction(temp,commonFriendsAccounts.get(i));
				if(transactionId==null)
				{
					//throw an exception
					return null;
				}
			}
			temp.setTransactionId(transactionId);
			//allNewTransactions.set(i,temp);
			//commonFriends.get(i).addNewTransaction(allNewTransactions.get(i));
			User tempUser=commonFriends.get(i);
			tempUser.addNewTransaction(temp);
			ArrayList<Group> groupList=groups.get(i);
			for(int j=0;j<groupList.size();j++)
			{
				//commonFriends.get(i).addTransactionToGroup(groupList.get(j), allNewTransactions.get(i));
				tempUser.addTransactionToGroup(groupList.get(j), temp);
				boolean success=groupDao.addTransactionToGroup(groupList.get(j), temp);
				if(!success)
				{
					success=groupDao.addTransactionToGroup(groupList.get(j), temp);
					if(!success)
					{
						return null;
					}
				}
			}
			userManager.setUser(tempUser);
		}
		
		return transactionId;
	}
	//this method can only be called by sender or receiver
	@Override
	public boolean deleteTransaction(String transactionId, String userId) {
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
		//user.removeTransaction(transactionId);
		//user.removeTransactionFromGroups(transactionId)
		Transaction transaction=user.getTransaction(transactionId);
		Account senderAccount=transaction.getSender();
		Account receiverAccount=transaction.getReceiver();
		User sender;//=userManager.getUser(senderAccount);
		//User user;//=userManager.getUser(userAccount);
		if(!userManager.hasUser(senderAccount))
		{
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender=userManager.getUser(senderAccount);
		User receiver;//=userManager.getUser(receiverAccount);
		//User user;//=userManager.getUser(userAccount);
		if(!userManager.hasUser(receiverAccount))
		{
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return false;
			}
		}
		receiver=userManager.getUser(receiverAccount);
		ArrayList<Account> commonFriendsAccounts=utils.getCommonFriends(sender.getAddressBook(), receiver.getAddressBook());
		ArrayList<User> commonFriends=userManager.getUsers(commonFriendsAccounts);
		if(commonFriends==null)
		{
			commonFriends=userManager.reInitializeUsers(commonFriendsAccounts);
			//fix this
		}
		//commonFriendsAccounts.add(senderAccount);
		//commonFriendsAccounts.add(receiverAccount);
		commonFriends.add(sender);
		commonFriends.add(receiver);
		boolean value1=false,value2=false;
		for(int k=0;k<commonFriends.size();k++)
		{
			User tempU=commonFriends.get(k);
			ArrayList<String> tempGs=tempU.getTransaction(transactionId).getGroupNames();
			//tempU.removeTransaction(transactionId);
			try
			{
				value1=transactionDao.removeTransaction(tempU.getTransaction(transactionId), commonFriends.get(k).getAccount());
				if(!value1)
				{
					value1=transactionDao.removeTransaction(tempU.getTransaction(transactionId), commonFriends.get(k).getAccount());
					if(!value1)
					{
						//throw exception
						return false;
					}
				}
			}
			catch(TransactionNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			tempU.removeTransaction(transactionId);
			for(int l=0;l<tempGs.size();l++)
			{
				//tempU.removeTransactionFromGroup(transactionId,tempGs.get(l));
				Group tempG=tempU.removeTransactionFromGroup(transactionId,tempGs.get(l));
				try
				{
					value2=groupDao.deleteTransactionFromGroup(tempG, transactionId);
					if(!value2)
					{
						value2=groupDao.deleteTransactionFromGroup(tempG, transactionId);
						if(!value2)
						{
							//throw exception
							return false;
						}
					}
				}
				catch(GroupNotFoundException e)
				{
					System.out.println(e.getMessage());
					return false;
				}
				catch(TransactionNotFoundException e)
				{
					System.out.println(e.getMessage());
					return false;
				}
			}
			userManager.setUser(tempU);
		}
		return value1&&value2;
		
		
		
		
		
//		ArrayList<ArrayList<Group>> groups=userManager.getGroupsForEachUser(commonFriends);
//		for(int i=0;i<commonFriends.size();i++)
//		{
//			User temp=commonFriends.get(i);
//			transactionDao.removeTransaction(temp.removeTransaction(transactionId),temp.getAccount());
//			transaction=temp.removeTransactionFromGroups(transactionId);
//			ArrayList<Group> groupList=groups.get(i);
//			for(int j=0;j<groupList.size();j++)
//			{
//				Group tempGroup=groupList.get(j);
//				groupDao.deleteTransactionFromGroup(tempGroup);
//			}
//			userManager.setUser(temp);
//			
//		}
//		return false;
	}
	//this method is for modifying description and taglist
	@Override
	public boolean updateTransaction(String transactionId, String userId,String propertyName,String newValue) {
		// TODO Auto-generated method stub
		Account account=accountManager.getAccountByUserId(userId);
		if(account==null)
		{
			try
			{
				account=accountDao.getByUserId(userId);
				if(account==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		User user;//=userManager.getUser(account);
		if(userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user=userManager.getUser(account);
		Transaction transaction=user.getTransaction(transactionId);
		if(propertyName.equals("description"))
		{
			transaction.setDescription(newValue);
		}
		else
			transaction.setTagList(newValue);
		Account senderAccount=transaction.getSender();
		Account receiverAccount=transaction.getReceiver();
		User sender;//=userManager.getUser(senderAccount);
		if(userManager.hasUser(senderAccount))
		{
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender=userManager.getUser(senderAccount);
		User receiver;//=userManager.getUser(receiverAccount);
		if(userManager.hasUser(receiverAccount))
		{
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return false;
			}
		}
		receiver=userManager.getUser(receiverAccount);
		ArrayList<Account> commonFriendsAccounts=utils.getCommonFriends(sender.getAddressBook(), receiver.getAddressBook());
		ArrayList<User> commonFriends=userManager.getUsers(commonFriendsAccounts);
		if(commonFriends==null)
		{
			commonFriends=userManager.reInitializeUsers(commonFriendsAccounts);
			//fix this
		}
		commonFriends.add(sender);
		commonFriends.add(receiver);
		//ArrayList<ArrayList<Group>> groups=userManager.getGroupsForEachUser(commonFriends);
		boolean success=false;
		for(int i=0;i<commonFriends.size();i++)
		{
			User temp=commonFriends.get(i);
			temp.modifyTransaction(transaction);
			temp.modifyTransactionInGroups(transaction);
			//success=transactionDao.modifyTransaction(transaction,propertyName,temp.getAccount());//will only make change in username_transactions
			try
			{
				success=transactionDao.modifyTransaction(transaction,propertyName,temp.getAccount());//will only make change in username_transactions
				if(!success)
					{
						success=transactionDao.modifyTransaction(transaction,propertyName,temp.getAccount());
						if(!success)
						{
							//throw exception
							return false;
						}
					}
				
			}
			catch(TransactionNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			userManager.setUser(temp);
			
		}
		return success;
	}
	
	public boolean updateTransaction(String transactionId,String userId,float newAmount)
	{
		Account account=accountManager.getAccountByUserId(userId);
		if(account==null)
		{
			try
			{
				account=accountDao.getByUserId(userId);
				if(account==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		User user;//=userManager.getUser(account);
		if(userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user=userManager.getUser(account);
		Transaction transaction=user.getTransaction(transactionId);
		transaction.setAmount(newAmount);
		Account senderAccount=transaction.getSender();
		Account receiverAccount=transaction.getReceiver();
		User sender;//=userManager.getUser(senderAccount);
		if(userManager.hasUser(senderAccount))
		{
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender=userManager.getUser(senderAccount);
		User receiver;//=userManager.getUser(receiverAccount);
		if(userManager.hasUser(receiverAccount))
		{
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return false;
			}
		}
		receiver=userManager.getUser(receiverAccount);
		ArrayList<Account> commonFriendsAccounts=utils.getCommonFriends(sender.getAddressBook(), receiver.getAddressBook());
		ArrayList<User> commonFriends=userManager.getUsers(commonFriendsAccounts);
		if(commonFriends==null)
		{
			commonFriends=userManager.reInitializeUsers(commonFriendsAccounts);
			//fix this
		}
		commonFriends.add(sender);
		commonFriends.add(receiver);
		boolean success=false;
		for(int i=0;i<commonFriends.size();i++)
		{
			User temp=commonFriends.get(i);
			temp.modifyTransaction(transaction);
			temp.modifyTransactionInGroups(transaction);
			try
			{
				success=transactionDao.modifyTransaction(transaction,"amount",temp.getAccount());
				if(!success)
					{
						success=transactionDao.modifyTransaction(transaction,"amount",temp.getAccount());
						if(!success)
						{
							//throw exception
							return false;
						}
					}
				
			}
			catch(TransactionNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			userManager.setUser(temp);
			
		}
		return success;
	}	
		
	public boolean updateTransaction(String transactionId,String userId,String newReceiverUsername)
	{
		Account account=accountManager.getAccountByUserId(userId);
		if(account==null)
		{
			try
			{
				account=accountDao.getByUserId(userId);
				if(account==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		User user;//=userManager.getUser(account);
		if(userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user=userManager.getUser(account);
		Transaction transaction=user.getTransaction(transactionId);
		Account tempA=accountManager.getAccountByUserName(newReceiverUsername);
		if(tempA==null)
		{
			try
			{
				tempA=accountDao.getByUsername(newReceiverUsername);
				if(tempA==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		transaction.setReceiver(tempA);
		Account senderAccount=transaction.getSender();
		Account receiverAccount=transaction.getReceiver();
		User sender;//=userManager.getUser(senderAccount);
		if(userManager.hasUser(senderAccount))
		{
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender=userManager.getUser(senderAccount);
		User receiver;//=userManager.getUser(receiverAccount);
		if(userManager.hasUser(receiverAccount))
		{
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return false;
			}
		}
		receiver=userManager.getUser(receiverAccount);
		ArrayList<Account> commonFriendsAccounts=utils.getCommonFriends(sender.getAddressBook(), receiver.getAddressBook());
		ArrayList<User> commonFriends=userManager.getUsers(commonFriendsAccounts);
		if(commonFriends==null)
		{
			commonFriends=userManager.reInitializeUsers(commonFriendsAccounts);
			//fix this
		}
		commonFriends.add(sender);
		commonFriends.add(receiver);
		boolean success=false;
		//int count=0;
		for(int i=0;i<commonFriends.size();i++)
		{
			User temp=commonFriends.get(i);
			temp.modifyTransaction(transaction);
			temp.modifyTransactionInGroups(transaction);
			//success=transactionDao.modifyTransaction(transaction,"receiver",temp.getAccount());
			try
			{
				success=transactionDao.modifyTransaction(transaction,"receiver",temp.getAccount());
				if(!success)
					{
						success=transactionDao.modifyTransaction(transaction,"receiver",temp.getAccount());
						if(!success)
						{
							//throw exception
							return false;
						}
					}
				
			}
			catch(TransactionNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			userManager.setUser(temp);
			
		}
		return success;
			
		
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing beans for TransactionModifierImpl");
		if(accountManager==null||userManager==null||accountDao==null||accountDao==null||transactionDao==null||groupDao==null||creator==null||utils==null)
		{
			throw new NullPointerException("thrown from TransactionModifierImpl");
		}
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setCreator(TransactionCreator creator) {
		this.creator = creator;
	}

	public TransactionCreator getCreator() {
		return creator;
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

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}
	public UserUtils getUtils() {
		return utils;
	}

}
