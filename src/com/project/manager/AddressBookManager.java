package com.project.manager;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;

public interface AddressBookManager{
	public AddressBook createAddressBook(ArrayList<Account> appUsers,ArrayList<Friend> contacts);
}