package com.project.manager.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Group;
import com.project.exceptions.UserNotFoundException;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.Transaction;
import com.project.manager.User;
import com.project.manager.UserManager;
//bean injection still needs to be done
public class UserManagerImpl implements UserManager {

	private Hashtable<Account,User> usersHashtable;

	@Override
	public User deleteUser(Account account) {
		// TODO Auto-generated method stub
		return usersHashtable.remove(account);
		
	}

	@Override
	public boolean modifyUser(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(Account account) {
		// TODO Auto-generated method stub
		return usersHashtable.get(account);
		
	}

	@Override
	public User createNewUser(Account account) {
		// TODO Auto-generated method stub
		return new User(account);
		
	}

	@Override
	public User reInitializeUser(Account account) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setAccount(account);
//		AddressBook book=new AddressBook();
//		book.setAppUsers(appUsers);
//		book.setContacts(contacts);
//		user.setAddressBook(book);
//		user.setTransactions(transactionDao);
//		user.setSuggestions(suggestions);
//		user.setGroups(groups);
		return user;

	}

	@Override
	public void addNewUser(User user) {
		// TODO Auto-generated method stub
		usersHashtable.put(user.getAccount(),user);
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		usersHashtable.put(user.getAccount(), user);
	}

	@Override
	public ArrayList<User> getUsers(ArrayList<Account> accounts) {
		// TODO Auto-generated method stub
		ArrayList<User> users=new ArrayList<User>();
		for(int i=0;i<accounts.size();i++)
		{
			users.add(usersHashtable.get(accounts.get(i)));
		}
		
		return users;
	}

	@Override
	public ArrayList<ArrayList<Group>> getGroupsForEachUser(
			ArrayList<User> users) {
		// TODO Auto-generated method stub
		int count=0;
		ArrayList<ArrayList<Group>> gReturn=new ArrayList<ArrayList<Group>>();
		ArrayList<Group> gAdd=new ArrayList<Group>();
		for(int i=0;i<users.size();i++)
		{
			User tempU=users.get(i);//get one user
			ArrayList<Group> tempGs=tempU.getGroups();//get all its groups
			for(int j=0;j<tempGs.size();j++)
			{
				Group tempG=tempGs.get(j);//get one group
				//now check in users of each group
				ArrayList<Account> tempAs=tempG.getUsers();
				for(int k=0;k<users.size();k++)//going through all the users list except the one for which i loop is running
				{
					boolean flag=true;
					if(k==i)
						flag=false;
						User tempUU=users.get(k);
					
					for(int l=0;l<tempAs.size()&&flag;l++)
					{
						flag=true;
						if(tempAs.get(l).getUsername().equals(tempUU.getAccount().getUsername()))
						{
							count++;
						}
					}
					if(count==(users.size()-1))
					{
						gAdd.add(tempG);
					}
				}
			}
			gReturn.add(gAdd);
		}
		return gReturn;
	}
@Override
	public boolean hasUser(Account acccount) {
		// TODO Auto-generated method stub









		
		return (usersHashtable.containsKey(acccount));
	}
	
	public User createUser(Account account,ArrayList<Group> groups,ArrayList<Transaction> transactions,ArrayList<Suggestion> suggestions,AddressBook addressBook,ArrayList<Notification> notifications){
		User user = new User();
		user.setAccount(account);
		user.setAddressBook(addressBook);
		user.setGroups(groups);
		user.setNotifications(notifications);
		user.setSuggestions(suggestions);
		user.setTransactions(transactions);
		
		return user;
	}
	
	public Hashtable<Account, User> getUsersHashtable() {
		return usersHashtable;
	}

	public void setUsersHashtable(Hashtable<Account, User> usersHashtable) {
		this.usersHashtable = usersHashtable;
	}

	@Override
	public ArrayList<User> reInitializeUsers(ArrayList<Account> accounts) {
		// TODO Auto-generated method stub
		return null;

	}

}
