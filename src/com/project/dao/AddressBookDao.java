package com.project.dao;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Friend;
import com.project.exceptions.FriendNotFoundException;

public interface AddressBookDao 
{
	public ArrayList<Friend> getAll(Account userAccount);
	public Friend getByPhone(Account account,String phone1);
	public ArrayList<Friend> getByFirstName(Account account,String firstName);
	public ArrayList<Friend> getByLastName(Account account,String lastName);
	public boolean addNewFriend(Friend friend,Account account);//this creates a new row in table
	public boolean deleteFriend(Friend friend,Account account)throws FriendNotFoundException;//this deletes a row in  table
	//public boolean updateFriend(Friend friend,Account account);//this updates a row in table
}
