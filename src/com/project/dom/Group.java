package com.project.dom;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="group")
@XmlType(propOrder={"groupName","groupDescription","transactionIds","users"})
public class Group{
	private String groupName;
	private String groupDescription;
	private ArrayList<String> transactionIds;
	private ArrayList<Account> users;
	private Account account;
	
	public Group(){}
	public Group(String name,String description)
	{
		this.groupName=name;
		this.groupDescription=description;
		this.transactionIds=new ArrayList<String>();
		this.users=new ArrayList<Account>();
	}
	public Group(Account account,String groupName,String description)
	{
		this.account=account;
		this.groupName=groupName;
		this.groupDescription=description;
		this.transactionIds=new ArrayList<String>();
		this.users=new ArrayList<Account>();
	}
	public void addTransactionIdToGroup(String transactionId)
	{
		transactionIds.add(transactionId);
	}
	public void setTransactionIdInGroup(int index,String transactionId)
	{
		transactionIds.set(index, transactionId);
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@XmlElement(name = "groupName")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	@XmlElement(name = "groupDescription")
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setTransactionIds(ArrayList<String> transactionIds) {
		this.transactionIds = transactionIds;
	}
	@XmlElementWrapper(name = "transactionIds")
	@XmlElement(name = "transactionId")
	public ArrayList<String> getTransactionIds() {
		return transactionIds;
	}
		public void setUsers(ArrayList<Account> users) {
		this.users = users;
	}
		@XmlElementWrapper(name = "users")
		@XmlElement(name = "user")
	public ArrayList<Account> getUsers() {
		return users;
	}
	public void addUsers(ArrayList<Account> accounts)
	{
		users.addAll(accounts);
		
	}
	public ArrayList<Account> removeUser(Account account)
	{
		for(int i=0;i<users.size();i++)
		{
			if(account.getUsername().equals(users.get(i).getUsername()))
			{
				users.remove(i);
				
			}
		}
		return users;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Account getAccount() {
		return account;
	}
//	public String[]  removeTransactions(Account friendAccount)
//	{
//		String[] tempTransactions=new String[transactionIds.size()];
//		for(int i=0;i<transactionIds.size();i++)
//		{
//			if(transactions.get(i).getSender().getUsername().equals(friendAccount.getUsername())||transactions.get(i).getReceiver().getUsername().equals(friendAccount.getUsername()))
//			{
//				tempTransactions[i]=((transactionIds.remove(i));
//			}
//		}
//		return tempTransactions;
//	}
//	public String[]  getTransactions(Account friendAccount)
//	{
//		String[] tempTransactions=new String[transactions.size()];
//		for(int i=0;i<transactions.size();i++)
//		{
//			if(transactions.get(i).getSender().getUsername().equals(friendAccount.getUsername())||transactions.get(i).getReceiver().getUsername().equals(friendAccount.getUsername()))
//			{
//				tempTransactions[i]=((transactions.get(i)).getTransactionId());
//			}
//		}
//		return tempTransactions;
//	}
//	public ArrayList<Transaction> getTransactionIds(String username)
//	{
//		ArrayList<String> tempTransactions=new ArrayList<String>();
//		for(int i=0;i<transactionIds.size();i++)
//		{
//			if(transactionIds.get(i).getSender().getUsername().equals(username)||transactions.get(i).getReceiver().getUsername().equals(username))
//			{
//				tempTransactions.add(transactions.get(i));
//			}
//		}
//		return tempTransactions;
//	}
	public void removeTransactionIdFromGroup(String transactionId)
	{
		for(int i=0;i<transactionIds.size();i++)
		{
			if(transactionIds.get(i).equals(transactionId))
			{
				transactionIds.remove(i);
				break;
			}
		}
	}
	
	
}
