package com.project.business.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.GroupModifier;
import com.project.dao.AccountDao;
import com.project.dao.GroupDao;
import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.AllAccountsNotFoundException;
import com.project.exceptions.FriendNotFoundException;
import com.project.exceptions.GroupAlreadyPresentException;
import com.project.exceptions.GroupNotFoundException;
import com.project.exceptions.TransactionNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.GroupCreator;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;
//bean injection still needs to be done
public class GroupModifierImpl implements InitializingBean, GroupModifier {
	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private GroupCreator creator=null;
	private GroupDao groupDao=null;
	private AccountDao accountDao=null;
	private UserUtils utils=null;
	public UserUtils getUtils() {
		return utils;
	}
	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public AccountManager getAccountManager() {
		return accountManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public GroupCreator getCreator() {
		return creator;
	}
	public GroupDao getGroupDao() {
		return groupDao;
	}
	//exception handling : if dao returns false then send back false
	@Override
	public boolean createGroup(String userId, String groupName,
			String description) {
		// TODO Auto-generated method stub
		//Account account=accountManager.getAccountByUserId(userId);

		Account account = accountManager.getAccountByUserId(userId);
		if(account==null)
		{
			try
			{
				System.out.println("if account is null");
				account=accountDao.getByUserId(userId);
				if(account==null)
				{
					System.out.println("if account is still null");
					return false;
				}
			}
			catch(AccountNotFoundException e)
			{
				System.out.println("account not found");
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		//User user=userManager.getUser(account);
		User user;
		if(!userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
			
		}
		user = userManager.getUser(account);
		//rest of the properties will be set when more friends are added
		Group group=creator.createGroup(account, groupName, description);
		user.addNewGroup(group);
		userManager.setUser(user);
		boolean success;
		try
		{
			success=groupDao.addGroup(group);
			if(!success)
			{
				success=groupDao.addGroup(group);
				if(!success)
				{
					return false;
				}
			}
		}
		catch(GroupAlreadyPresentException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return success;
	}
	//exception handling : if dao returns false return false
	@Override
	public boolean deleteGroup(String userId,String groupName) {
		// TODO Auto-generated method stub
		//Account account=accountManager.getAccountByUserId(userId);
		Account account = accountManager.getAccountByUserId(userId);
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
		//User user=userManager.getUser(account);
		User user;
		if(!userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user = userManager.getUser(account);
		// at this step we make the necessary changes to all the properties of user object
		//see the description of user.removeGroup()
		Group tempG=user.removeGroup(groupName);
		boolean value;
		try
		{
			value= groupDao.deleteGroup(tempG);//this will delete groupname from all the three tables
			if(!value)
			{
				value= groupDao.deleteGroup(tempG);
				if(!value)
				{
					return false;
				}
			}
		}
		catch(GroupNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		userManager.setUser(user);
		return value;
	}

	@Override
	public boolean addFriendsToGroup(String userId,String[] usernames,String groupName) {
		// TODO Auto-generated method stub
		//Account account=accountManager.getAccountByUserId(userId);
		Account account = accountManager.getAccountByUserId(userId);
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
		ArrayList<Account> friendsAccounts=accountManager.getAccountsByUserNames(usernames);
		if(friendsAccounts==null)
		{
			try
			{
				friendsAccounts=accountDao.getAccountsByUsername(usernames);
				if(friendsAccounts==null)
				{
					return false;
				}
			}
			catch(AllAccountsNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
		}
		//User user=userManager.getUser(account);
		User user;
		if(!userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user = userManager.getUser(account);
		ArrayList<Transaction> transactions=user.getCommonTransactions(friendsAccounts);
		ArrayList<String> tIds=getTransactionIds(transactions);
		// this method will change the transactions elements by making change in their groups property and will return the modified transactions
		//user.modifyCommonTransactions(transactions);
		Group group=user.getGroup(groupName);
		group.addUsers(friendsAccounts);
		group.setTransactionIds(tIds);
		//bgroup.addUsers(friendsAccounts);
		user.setGroup(group);
		//user.modifyTransactionsGroupNamesProperty(tIds,groupName);//this will modify the transaction objects of the user
		//user.setGroup(group);
		boolean value1=false;
		try
		{
			value1=groupDao.addFriendsToGroup(friendsAccounts, user.getGroup(groupName));//will only make changes to username_X
			if(!value1)
			{
				value1=groupDao.addFriendsToGroup(friendsAccounts, user.getGroup(groupName));
				if(!value1)
				{
					//exception should be thrown here to work for the transaction management
					return false;
				}
			}
		}
		catch(GroupNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		ArrayList<Transaction> tempTs=user.modifyTransactionsGroupNamesProperty(tIds,groupName);
		boolean value2=false;
		try
		{
			value2=groupDao.addTransactionsToGroup(tempTs, user.getGroup(groupName));
			if(!value2)
			{
				value2=groupDao.addTransactionsToGroup(tempTs, user.getGroup(groupName));
				if(!value2)
					return false;
			}
		}
		catch(GroupNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		//transactionX
		userManager.setUser(user);
		return value1&&value2;
		
	}
	private ArrayList<String> getTransactionIds(ArrayList<Transaction> transactions)
	{
		ArrayList<String> temp=new ArrayList<String>();
		for(int i=0;i<transactions.size();i++)
		{
			temp.add(transactions.get(i).getTransactionId());
		}
		return temp;
	}
	private void modifyGroupNamesProperty(ArrayList<Transaction> transactions,String groupName)
	{
		for(int i=0;i<transactions.size();i++)
		{
			String senderUsername=transactions.get(i).getSender().getUsername();
			String receiverUsername=transactions.get(i).getReceiver().getUsername();
			
		}
	}
	@Override
	public boolean deleteFriendFromGroup(String userId,String username,String groupName) {
		// TODO Auto-generated method stub
		//Account account=accountManager.getAccountByUserId(userId);
		Account account = accountManager.getAccountByUserId(userId);
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
		//User user=userManager.getUser(account);
		User user;
		if(!userManager.hasUser(account))
		{
			user=utils.reinitializeUser(account);
			if(user==null)
			{
				//throw exception
				return false;
			}
		}
		user = userManager.getUser(account);
		//Account friend=accountManager.getAccountByUserName(username);
		Account friend = accountManager.getAccountByUserName(username);
		if(friend==null)
		{
			try
			{
				friend=accountDao.getByUsername(username);
				if(friend==null)
					return false;
			}
			catch(AccountNotFoundException e)
			{
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		//user.deleteFriendFromGroup(friend, groupName);//this method will make all the changes required
		
		//Group group=user.getGroup(groupName);
		//ArrayList<Transaction> transactions=user.getTransactionsFromGroup(friend, groupName);
//		Group unmodifiedGroup=user.getGroup(groupName);
//		ArrayList<String> tempIds=user.getTransactionsByUserInGroup(friend,unmodifiedGroup);
//		ArrayList<Transaction> tempTransactions=user.getTransactions(tempIds);
		Group tempG=user.deleteFriendFromGroup(friend,groupName);
		
		boolean value1=false;
		try
		{
			value1=groupDao.deleteFriendFromGroup(friend,tempG);//this method will take care of all the transactions involved as well(in groups as well as transactions)
			if(!value1)
			{
				value1=groupDao.deleteFriendFromGroup(friend,tempG);
				if(!value1)
				{
					//an exception should be thrown at this point for transaction management
			
					return false;
				}
			}
		}
		catch(GroupNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		catch(FriendNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		//ArrayList<String> Ids=getTransactionIds(user,friend,groupName);
		boolean value2=false;
		ArrayList<Transaction> tempTs=user.getTransactions(getTransactionIds(user,friend,groupName));
		try
		{
				value2=groupDao.deleteTransactionsFromGroup(tempTs,tempG);//this is a call for making changes in transactionX
				if(!value2)
				{
					value2=groupDao.deleteTransactionsFromGroup(tempTs,tempG);//this is a call for making changes in transactionX
					if(!value2)
					{
						//an exception should be thrown for transaction management
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
		userManager.setUser(user);
		return value1&&value2;
	}
	private ArrayList<String> getTransactionIds(User user,Account account,String groupName)
	{
		ArrayList<Group> tempGroups=user.getGroups();
		ArrayList<String> tempReturn=new ArrayList<String>();
		for(int i=0;i<tempGroups.size();i++)
		{
			if(tempGroups.get(i).getGroupName().equals(groupName))
			{
				Group tempG=tempGroups.get(i);
				ArrayList<String> tempIds=tempG.getTransactionIds();
				for(int j=0;j<user.getTransactions().size();j++)
				{
					Transaction tempT=user.getTransactions().get(j);
					if(tempT.getSender().equals(account.getUsername())||tempT.getReceiver().equals(account.getUsername()))
					{
						for(int k=0;k<tempIds.size();k++)
						{
							if(!(tempIds.get(k).equals(tempT.getTransactionId())))
							{
								tempReturn.add(tempIds.get(k));
							}
						}
					}
				}
				return tempReturn;
			}
		}
		return null;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//check to see if all the dependencies are set
		System.out.println("initializing beans for GroupModifierImpl");
		if(accountManager==null||userManager==null||accountDao==null||groupDao==null||accountDao==null||creator==null||utils==null)
		{
			throw new NullPointerException("thrown from GroupModifierImpl");
		}
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	

	public void setCreator(GroupCreator creator) {
		this.creator = creator;
	}

	
	public void setGroupDao(GroupDao groupDao)
	{
		this.groupDao=groupDao;
	}


}
