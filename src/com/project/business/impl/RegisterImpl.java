package com.project.business.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.Register;
import com.project.dao.AccountDao;
import com.project.dao.AddressBookDao;
import com.project.dao.CreateTablesForUser;
import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;
import com.project.exceptions.AccountDaoFailException;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.FriendNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;
//bean injection still needs to be done
public class RegisterImpl implements Register,InitializingBean{

	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private AccountDao accountDao=null;
	private AddressBookDao addressBookDao=null;
	private UserUtils utils=null;
	private CreateTablesForUser createTables;
	public UserUtils getUtils() {
		return utils;
	}

	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}

	//private AddressBookCreator addressBookCreator;
	//try to see if all multiple calls to database are executed
	@Override
	public String register(String username, String password, String firstName,
			String lastName, Date dob,
			 String gender, String phoneNo) {
		
		try
		{
			//accountDao will do exception handling within its own methods(if something goes wrong it just returns false)
			System.out.println("thank you");
			System.out.println(accountManager);
			Account newAccount=accountManager.createAccount(username, password, firstName, lastName, dob, gender, phoneNo);
			System.out.println("thanks");
			System.out.println(newAccount.getUserId());
			System.out.println(newAccount.getUsername());
			System.out.println(newAccount.getFirstName());
			System.out.println(newAccount.getLastName());
			System.out.println(newAccount.getGender());
			accountManager.addAccount(newAccount);
			System.out.println("hello again");
			//if dao fails then we just get the boolean value false(we are not catching that exception in this method)
			boolean flag=true;
			flag=createTables.createUserAddressBook(username);
			flag=createTables.createUserGroups(username);
			flag=createTables.createUserGroupUsers(username);
			flag=createTables.createUserTransactions(username);
			flag=createTables.createUserGroupTransactions(username);
			if(!flag)
			{
				System.out.println("flag is false");
				return null;
			}
			System.out.println("hello again1");
			boolean success=accountDao.addNewAccount(accountManager.getAccountByUserName(username));
			if(success)
			{
				System.out.println("success");
				return accountManager.getAccountByUserName(username).getUserId();
			}
			else 
			{
				success=accountDao.addNewAccount(accountManager.getAccountByUserName(username));
				if(success)
				{
					System.out.println("empty userID");
					return accountManager.getAccountByUserName(username).getUserId();
				}
				else throw new AccountDaoFailException("failed to add account to database");
			}
			
		}
		catch(Exception e)
		{
			//System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}

	public boolean registerNext(String userId,ArrayList<Friend> contacts)
	{
	
		Account newAccount = accountManager.getAccountByUserId(userId);
		if(newAccount==null)
		{
			try
			{
				newAccount=accountDao.getByUserId(userId);
				System.out.println("account has been retreived");
				if(newAccount==null)
				{
					System.out.println("inside null block");
					return false;
				}
			}
			catch(AccountNotFoundException e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		User newUser = userManager.createNewUser(newAccount);
		System.out.println("new user has been created");			
		AddressBook addressBook = new AddressBook();
					Account tempAccount;
					//here we can have more than one entry for appUsers if they have different numbers
					boolean success=false;
					for (int i = 0; i < contacts.size(); i++) {
						System.out.println("inside for loop");
						String phoneNo=contacts.get(i).getPhone1();
						tempAccount = accountManager.getByPhone(phoneNo);
						System.out.println("tempAccount retreived from manager");
						tempAccount=accountDao.getByPhoneNo(phoneNo);
						System.out.println("tempAccount retreived from database");
						// if account is not null it means it is already an appUser
						if (tempAccount != null) {
							System.out.println("adding appUser to the addressbook");
							addressBook.addAppUser(tempAccount);
							User tempUser;
							if(!userManager.hasUser(tempAccount))
							{
								System.out.println("initializing user");
								tempUser=utils.reinitializeUser(tempAccount);
								if(tempUser==null)
								{
									System.out.println("tempUSer is null");
									//throw exception
									return false;
								}
							}
							tempUser = userManager.getUser(tempAccount);
							System.out.println("getting user from the userManager");
							//now we are checking if this new user is present in the tempUsers friends list
							if (tempUser.isFriend(newAccount.getPhone1())) {
								tempUser.addAppUserToAddressBook(newAccount);
								//tempUser.removeFriendFromAddressBook(tempAccount.getPhone1());
								try
								{
									success=addressBookDao.deleteFriend(tempUser
											.removeFriendFromAddressBook(newAccount
													.getPhone1()), tempAccount);
									System.out.println("deleting friend by calling addressBookDao");
									//if dao operation has failed then there is no point in making changes to the userManager
									if(!success)
									{
										//System.out.println("addressBookDao operation failed");
										success=addressBookDao.deleteFriend(tempUser
												.removeFriendFromAddressBook(newAccount
														.getPhone1()), tempAccount);
										if(!success)
										//System.out.println("error in addressBookDao operation");
											return false;
									}
								}
								catch(FriendNotFoundException e)
								{
									e.printStackTrace();
									System.out.println(e.getMessage());
									return false;
								}
								userManager.setUser(tempUser);
							}
						} else {
							addressBook.addFriend(contacts.get(i));
							success=addressBookDao.addNewFriend(contacts.get(i), newAccount);
							System.out.println("adding new friend by calling addressBookDao");
							if(!success)
							{
								//System.out.println("addressBookDao operation failed");
								success=addressBookDao.addNewFriend(contacts.get(i), newAccount);
								if(!success)
								return false;
							}
						}
						
					}
					newUser.setAddressBook(addressBook);
					userManager.addNewUser(newUser);
					System.out.println("adding user to the manager");
					return true;
				}
	
	@Override
	public boolean authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		
		Account tempAccount=accountManager.getAccountByUserName(userName);
		try
		{
			if(tempAccount!=null)
				return true;
				else
				{
					tempAccount=accountDao.getByUsername(userName);
					if(tempAccount!=null)
					{
						accountManager.setAccount(tempAccount);
						return true;
					}
					else 
						return false;
				}
		}
		catch(AccountNotFoundException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		
		
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

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAddressBookDao(AddressBookDao addressBookDao) {
		this.addressBookDao = addressBookDao;
	}

	public AddressBookDao getAddressBookDao() {
		return addressBookDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing beans for RegisterImpl");
		if(accountManager==null||userManager==null||accountDao==null||addressBookDao==null||utils==null)
		{
			System.out.println("something is null");
			throw new NullPointerException("thrown from RegisterImpl");
		}
	}

	public void setCreateTables(CreateTablesForUser createTables) {
		this.createTables = createTables;
	}

	public CreateTablesForUser getCreateTables() {
		return createTables;
	}

}
