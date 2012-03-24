package com.project.dom;

import java.util.ArrayList;

public class AddressBook 
{
	private ArrayList<Account> appUsers;
	private ArrayList<Friend> contacts;
	public AddressBook()
	{
		this.appUsers=new ArrayList<Account>();
		this.contacts=new ArrayList<Friend>();
	}
	public AddressBook(ArrayList<Account> appUsers,ArrayList<Friend> contacts)
	{
		this.appUsers=appUsers;
		this.contacts=contacts;
	}
	public void addAppUser(Account account)
	{
		appUsers.add(account);
	}
	public void addFriend(Friend friend)
	{
		contacts.add(friend);
	}
	public void removeFriendFromAddressBook(Friend f)
	{
		for(int i=0;i<contacts.size();i++)
		{
			if(contacts.get(i).getPhone1().equals(f.getPhone1()))
			{
				contacts.remove(i);
				break;
			}
		}
	}
	public Friend removeFriendFromAddressBook(String phone1)
	{
		for(int i=0;i<contacts.size();i++)
		{
			if(contacts.get(i).getPhone1().equals(phone1))
			{
				return contacts.remove(i);
				
			}
		}
		return null;
	}
	public void setAppUsers(ArrayList<Account> appUsers) {
		this.appUsers = appUsers;
	}
	public ArrayList<Account> getAppUsers() {
		return appUsers;
	}
	public void setContacts(ArrayList<Friend> contacts) {
		this.contacts = contacts;
	}
	public ArrayList<Friend> getContacts() {
		return contacts;
	}
	public void reset()
	{
		appUsers=null;
		contacts=null;
	}
}
