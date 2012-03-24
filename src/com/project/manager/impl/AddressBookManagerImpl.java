package com.project.manager.impl;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;
import com.project.manager.AddressBookManager;

public class AddressBookManagerImpl
	implements AddressBookManager
{
	public AddressBook createAddressBook(ArrayList<Account> appUsers,ArrayList<Friend> contacts){
		AddressBook returnAddressBook = new AddressBook(appUsers,contacts);
		return returnAddressBook;
	}
}