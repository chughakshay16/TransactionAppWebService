package com.project.dao;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.FriendNotFoundException;
import com.project.exceptions.GroupAlreadyPresentException;
import com.project.exceptions.GroupNotFoundException;
import com.project.exceptions.TransactionNotFoundException;
import com.project.manager.User;

public interface GroupDao 
{
	public String getGroupDescription(Account account,String groupName);
	public ArrayList<User> getUsers(Account account,String groupName);
	public boolean addGroup(Group group)throws GroupAlreadyPresentException;
	public boolean modifyGroup(Group group);
	public boolean deleteGroup(Group group)throws GroupNotFoundException;
	public boolean addFriendsToGroup(ArrayList<Account> accounts,Group group)throws GroupNotFoundException;
	public boolean deleteFriendFromGroup(Account friendAccount,Group group)throws GroupNotFoundException,FriendNotFoundException;
	public boolean addTransactionsToGroup(ArrayList<Transaction> transactions,Group group)throws GroupNotFoundException;
	public boolean addTransactionToGroup(Group group,Transaction transactionS);
	public boolean deleteTransactionFromGroup(Group group,String transactionId)throws GroupNotFoundException,TransactionNotFoundException;
	public ArrayList<Group> getGroups(Account account)throws AccountNotFoundException;
	public boolean deleteTransactionsFromGroup(ArrayList<Transaction> transactions,Group g)throws GroupNotFoundException,TransactionNotFoundException;
}
