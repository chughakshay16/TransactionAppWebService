package com.project.manager;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;
import com.project.dom.Group;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.dom.Transaction;
//in this class some methods which modify the class properties, dont insert the parameter objects into these properties. Even those objects can be used but as of now we are not using them.
//in this class no setter injection should be carried out
@XmlRootElement
public class User 
{
	private Account account;
	//@XmlElementWrapper(name = "groupz")
	//@XmlElement(name = "group")
	private ArrayList<Group> groups;
	//@XmlElementWrapper(name = "transactions")
	//@XmlElement(name = "transaction")
	private ArrayList<Transaction> transactions;
	private ArrayList<Suggestion> suggestions;
	private AddressBook addressBook;
	private ArrayList<Notification> notifications;
	public User()
	{
		this.account=new Account();
		this.addressBook=new AddressBook();
		this.transactions=new ArrayList<Transaction>();
		this.groups=new ArrayList<Group>();
		this.suggestions=new ArrayList<Suggestion>();
		this.notifications=new ArrayList<Notification>();
	}
	public User(Account account)
	{
		this.account=account;
		this.groups=new ArrayList<Group>();
		this.transactions=new ArrayList<Transaction>();
		this.suggestions=new ArrayList<Suggestion>();
		this.addressBook=null;
		this.notifications=new ArrayList<Notification>();
	}
	public Account getAccount()
	{
		return account;//modify this method by using AccountManager
	}
	public void setAccount(Account account)
	{
		this.account=account;//modify this method by using AcccountManager
	}
	@XmlElementWrapper(name="groups")
	@XmlElement(name = "group")
	public ArrayList<Group> getGroups()
	{
		return groups;
	}
	public void setGroups(ArrayList<Group> groups)
	{
		this.groups=groups;
	}
	public void addNewGroup(Group group)
	{
		this.groups.add(group);
	}
	public void setGroup(Group group)
	{
		String key=group.getGroupName();
		for(int i=0;i<groups.size();i++)
		{
			if(groups.get(i).getGroupName().equals(key))
			{
				groups.set(i, group);
				break;
			}
		}
	}
	//this function removes the group from groups list, and will also modify the transactions list(will make changes in groups property of transactions)
	public Group removeGroup(String groupName)
	{
		for(int i=0;i<groups.size();i++)
		{
			Group temp=groups.get(i);
			if(temp.getGroupName().equals(groupName))
			{
				//ArrayList<Transaction> tempTransactions=temp.getTransactions();
				ArrayList<String> tempIds=temp.getTransactionIds();
				for(int j=0;j<tempIds.size();j++)
				{
					for(int k=0;k<transactions.size();k++)
					{
						if(transactions.get(k).getTransactionId().equals(tempIds.get(j)))
						{
							Transaction tempTransaction=transactions.get(k);
							//ArrayList<Group> tempGroups=tempTransaction.getGroups();
							ArrayList<String> tempN=tempTransaction.getGroupNames();
							for(int l=0;l<tempN.size();l++)
							{
								if(tempN.get(l).equals(groupName))
								{
									tempN.remove(l);
									tempTransaction.setGroupNames(tempN);
									//transactions.set(k, tempTransaction);
									
								}
							}
							transactions.set(k, tempTransaction);
							break;
						}
					}
				}
				groups.remove(i);
			}
		}
		return null;
	}
	public void addTagToTransaction(String tag,String transactionId)
	{
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId().equals(transactionId))
			{
				Transaction tempT=transactions.get(i);
				String tempTagList=tempT.getTagList();
				tempTagList+="#"+tag;
				tempT.setTagList(tempTagList);
				transactions.set(i, tempT);
				break;
			}
		}
	}
	public String deleteTagFromTransaction(String tag,String transactionId)
	{
		
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId().equals(transactionId))
			{
				Transaction tempT=transactions.get(i);
				String tempTg=tempT.getTagList();
				StringTokenizer tokenizer=new StringTokenizer(tempTg,"#");
				String tagL="";
				while(tokenizer.hasMoreTokens())
				{
					String token=tokenizer.nextToken();
					if(token.equals(tag))
						continue;
					tagL+="#"+token;
				}
				return tagL;
			}
		}
		return null;
	}
	public ArrayList<Transaction> modifyTransactionsGroupNamesProperty(ArrayList<String> tIds,String groupName)
	{
		ArrayList<Transaction> tempReturn=new ArrayList<Transaction>();
		for(int i=0;i<tIds.size();i++)
		{
			for(int k=0;k<transactions.size();k++)
			{
				if(transactions.get(k).getTransactionId().equals(tIds.get(i)))
				{
					Transaction tempT=transactions.get(k);
					ArrayList<String> tempGns=tempT.getGroupNames();
					tempGns.add(groupName);
					tempT.setGroupNames(tempGns);
					tempReturn.add(tempT);
					transactions.set(k, tempT);
				}
			}
		}
		return tempReturn;
	}
	public Group removeTransactionFromGroup(String transactionId,String groupName)
	{
		for(int i=0;i<groups.size();i++)
		{
			if(groups.get(i).getGroupName().equals(groupName))
			{
				Group tempG=groups.get(i);
				tempG.removeTransactionIdFromGroup(transactionId);
				groups.set(i, tempG);
				return tempG;
			}
		}
		return null;
	}
//	public ArrayList<Transaction> modifyTransactions(ArrayList<Transaction> ts)
//	{
//		ArrayList<Transaction> temps=new ArrayList<Transaction>();
//		for(int i=0;i<ts.size();i++)
//		{
//			Transaction temp=ts.get(i);
//			for(int k=0;k<transactions.size();k++)
//			{
//				if(transactions.get(k).getTransactionId().equals(temp.getTransactionId()))
//				{
//					Transaction tempAdd=transactions.get(k);
//					//now we will modify the groups property of transaction object
//					ArrayList<Group> tempG=tempAdd.getGroups();
//					
//				}
//			}
//		}
//	}
	public Group getGroup(String groupName)
	{
		for(int i=0;i<groups.size();i++)
		{
			if(groupName.equals(groups.get(i).getGroupName()))
			{
				return groups.get(i);
			}
		}
		return null;
	}
	public void setAcceptanceStatus(Account receiverAccount,boolean acceptanceStatus,String suggestionId)
	{
		for(int i=0;i<suggestions.size();i++)
		{
			if(suggestions.get(i).getSuggestionId().equals(suggestionId))
			{
				Suggestion temp=suggestions.get(i);
				temp.setAcceptanceStatus(receiverAccount, acceptanceStatus);
				suggestions.set(i, temp);
				break;
			}
		}
	}
	public Suggestion getSuggestion(String suggestionId)
	{
		for(int i=0;i<suggestions.size();i++)
		{
			if(suggestions.get(i).getSuggestionId().equals(suggestionId))
				return suggestions.get(i);
		}
		return null;
	}
	public Group addFriendsToGroup(ArrayList<Account> accounts,String groupName)
	{
		for(int i=0;i<groups.size();i++)
		{
			if(groupName.equals(groups.get(i).getGroupName()))
			{
				Group tempGroup=groups.get(i);
				tempGroup.addUsers(accounts);
				groups.set(i,tempGroup);
				return groups.get(i);
			}
		}
		return null;
	}
	public void deleteNotification(Notification notification)
	{
		for(int i=0;i<notifications.size();i++)
		{
			Notification notificationTemp=notifications.get(i);
			if(notificationTemp.getSuggestionId().equals(notification.getSuggestionId()))
			{
				notifications.remove(i);
				break;
			}
		}
	}
	// this method will delete the friend from the group,delete its transactions from the group and will also modify the groups property of all the deleted transactions in transactions property
//	public Group deleteFriendFromGroup(Account account,String groupName)
//	{
//		String tempName;
//		for(int i=0;i<groups.size();i++)
//		{
//			if(groupName.equals(tempName=groups.get(i).getGroupName()))
//			{
//				Group tempGroup=groups.get(i);
//				//now we will remove the user
//				tempGroup.removeUser(account);
//				//now we will remove all the transactions involved with this user
//				String[] tempIds=tempGroup.removeTransactions(account);
//				groups.set(i, tempGroup);
//				//now we will change the groups property in the transactions objects
//				for(int m=0;m<tempIds.length;m++)
//				{
//					for(int l=0;l<transactions.size();l++)
//					{
//						if(transactions.get(l).getTransactionId().equals(tempIds[m]))
//						{
//							Transaction tempT=transactions.get(l);
//							ArrayList<Group> tempG=tempT.getGroups();
//							for(int k=0;k<tempG.size();k++)
//							{
//								if(tempG.get(k).getGroupName().equals(tempName))
//								{
//									tempG.remove(k);
//									tempT.setGroups(tempG);
//									break;
//								}
//							}
//							transactions.set(l, tempT);
//							break;
//						}
//					}
//				}
//				return groups.get(i);
//				}
//		}
//		return null;
//	}
			
				
								
//			
//	public void removeTransactionsFromGroup(Group group,String friendUsername)//remove transactions from any group object
//	{
//		ArrayList<Transaction> transactions=group.getTransactions();
//		for(int i=0;i<transactions.size();i++)
//		{
//			if(transactions.get(i).getReceiver().getUsername().equals(friendUsername)||transactions.get(i).getSender().getUsername().equals(friendUsername))
//			{
//				transactions.remove(i);
//				group.setTransactions(transactions);
//			}
//		}
//	}
//	public ArrayList<String> getTransactionIdsFromGroup(Account friendAccount,String groupName)
//	{
//		for(int i=0;i<groups.size();i++)
//		{
//			if(groups.get(i).getGroupName().equals(groupName))
//			{
//				return groups.get(i).getTransactionIds(friendAccount);
//			}
//		}
//		return null;
//	}
	/*public ArrayList<Transaction> getTransactionsFromGroup(Account friendAccount,String groupName)
	{
		for(int i=0;i<groups.size();i++)
		{
			if(groups.get(i).getGroupName().equals(groupName))
			{
				return groups.get(i).getTransactions(friendAccount.getUsername());
			}
		}
		return null;
	}*/

	public void addTransactionToGroup(Group group,Transaction transaction)//here we add transaction to the group
	{
		String key=group.getGroupName();
		for(int i=0;i<groups.size();i++)
		{
			if(key.equals(groups.get(i).getGroupName()))
			{
				Group tempGroup=groups.get(i);
				tempGroup.addTransactionIdToGroup(transaction.getTransactionId());
				groups.set(i,tempGroup);
			}
		}
	}
	public void removeTransactionIdFromGroups(String transactionId)
	{
		String tempTransaction=null;
		boolean flag=false;
		for(int i=0;i<groups.size();i++)
		{
			Group temp=groups.get(i);
			ArrayList<String> tempTransactions=temp.getTransactionIds();
			for(int j=0;j<tempTransactions.size();j++)
			{
				//Transaction tempTransaction=tempTransactions.get(j);
				if(tempTransactions.get(j).equals(transactionId))
				{
					tempTransaction=tempTransactions.remove(j);
					temp.setTransactionIds(tempTransactions);
					flag=true;
					break;
				}
			}
			if(flag)
			{
				groups.set(i, temp);
				flag=false;
			}
			
		}
		
	}
	@XmlElementWrapper(name = "transactions")
	@XmlElement(name = "transaction")
	public ArrayList<Transaction> getTransactions()
	{
		return transactions;
	}
	
	public ArrayList<Transaction> getTransactions(ArrayList<String> transactionIds)
	{
		ArrayList<Transaction> tempTransactions=new ArrayList<Transaction>();
		for(int i=0;i<transactions.size();i++)
		{
			String tempTransactionId=transactionIds.get(i);
			for(int j=0;j<transactionIds.size();j++)
			{
				if(tempTransactionId.equals(transactions.get(i).getTransactionId()))
				{
					tempTransactions.add(transactions.get(i));
					break;
				}
			}
		}
		return tempTransactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions)
	{
		this.transactions=transactions;
	}
	public void addNewTransaction(Transaction transaction)
	{
		transactions.add(transaction);
	}
	public Transaction removeTransaction(String transactionId)
	{
		for(int i=0;i<transactions.size();i++)
		{
			if(transactionId.equals(transactions.get(i).getTransactionId()))
			{
				return transactions.remove(i);
			}
		}
		return null;
	}
	public void modifyTransaction(Transaction transaction)
	{
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId().equals(transaction.getTransactionId()))
			{
				transactions.set(i, transaction);
			}
		}
	}
	public void modifyTransactionInGroups(Transaction transaction)//dont remove
	{
		for(int i=0;i<groups.size();i++)
		{
			Group tempGroup=groups.get(i);
			for(int j=0;j<tempGroup.getTransactionIds().size();j++)
			{
				if(transaction.getTransactionId().equals(tempGroup.getTransactionIds().get(j)))
				{
					tempGroup.setTransactionIdInGroup(j,transaction.getTransactionId());
					break;
				}
			}
			groups.set(i, tempGroup);
		}
	}
	public Transaction getTransaction(String transactionId)
	{
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId().equals(transactionId))
				return transactions.get(i);
		}
		return null;
	}
	public ArrayList<Suggestion> getSuggestions()
	{
		return suggestions;
	}
	public ArrayList<Transaction> getCommonTransactions(ArrayList<Account> accounts)
	{
		ArrayList<Transaction> commonTransactions=new ArrayList<Transaction>();
		boolean flag=true;
		//boolean receiverFlag=false;
		for(int i=0;i<transactions.size();i++)
		{
			Account senderTemp=transactions.get(i).getSender();
			Account receiverTemp=transactions.get(i).getReceiver();
			for(int k=0;k<accounts.size();k++)
			{
				if(accounts.get(k).getUsername().equals(senderTemp.getUsername())||accounts.get(k).getUsername().equals(receiverTemp.getUsername()))
				{
					flag=!flag;
				}
			}
			if(flag)
			{
				commonTransactions.add(transactions.get(i));
			}
		}
		return commonTransactions;
	}
	public void setSuggestions(ArrayList<Suggestion> suggestions)
	{
		this.suggestions=suggestions;
	}
	public void addSuggestion(Suggestion suggestion)
	{
		suggestions.add(suggestion);
	}
	public Suggestion removeSuggestion(String suggestionId)
	{
		for(int i=0;i<suggestions.size();i++)
		{
			if(suggestions.get(i).getSuggestionId().equals(suggestionId))
				return suggestions.remove(i);
		}
		return null;
	}
	public void removeSuggestion(Suggestion suggestion)
	{
		for(int i=0;i<suggestions.size();i++)
		{
			if(suggestions.get(i).getSuggestionId().equals(suggestion.getSuggestionId()))
				 suggestions.remove(i);
		}
	}
	
	public void setAddressBook(AddressBook addressBook) {
		this.addressBook = addressBook;
	}
	public AddressBook getAddressBook() {
		return addressBook;
	}
	public void resetAddressBook()
	{
		addressBook.reset();
	}
	public void addFriendToAddressBook(Friend f)
	{
		addressBook.addFriend(f);
	}
	public void removeFriendFromAddressBook(Friend f)
	{
		addressBook.removeFriendFromAddressBook(f);
	}
	public Friend removeFriendFromAddressBook(String phone1)
	{
		return addressBook.removeFriendFromAddressBook(phone1);
	}
	public void addAppUserToAddressBook(Account account)
	{
		addressBook.addAppUser(account);
	}
	public void removeAppUserFromAddressBook(Account account)
	{
		ArrayList<Account> appUsers=addressBook.getAppUsers();
		for(int i=0;i<appUsers.size();i++)
		{
			if(account.getUsername().equals(appUsers.get(i).getUsername()))
			{
				appUsers.remove(i);
				addressBook.setAppUsers(appUsers);
				break;
			}
		}
	}
	public boolean isFriend(Friend friend)
	{
		return isFriend(friend.getPhone1());
	}
	public boolean isFriend(String phone1)
	{
		ArrayList<Friend> friends=addressBook.getContacts();
		for(int i=0;i<friends.size();i++)
		{
			if(friends.get(i).getPhone1().equals(phone1))
				return true;
		}
		return false;
	}
	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}
	public ArrayList<Notification> getNotifications() {
		return notifications;
	}
	public void addNewNotification(Notification newNotification)
	{
		notifications.add(newNotification);
	}
	public void modifyPendingStatusForNotifications(boolean status)
	{
		for(int i=0;i<notifications.size();i++)
		{
			Notification tempNotification=notifications.get(i);
			tempNotification.setPendingStatus(status);
			notifications.set(i, tempNotification);
		}
	}
	public void modifyPendingStatusForSuggestion(String suggestionId,boolean status,String ownerId)
	{
		for(int i=0;i<suggestions.size();i++)
		{
			if(suggestions.get(i).getSuggestionId().equals(suggestionId))
			{
				Suggestion tempS=suggestions.get(i);
				ArrayList<SuggestionReceiver> tempRs=tempS.getReceivers();
				for(int j=0;j<tempRs.size();j++)
				{
					if(tempRs.get(j).getReceiver().getUserId().equals(ownerId))
					{
						SuggestionReceiver tempR=tempRs.get(j);
						tempR.setPendingStatus(status);
						tempRs.set(j, tempR);
						tempS.setReceivers(tempRs);
						break;
					}
				}
				suggestions.set(i, tempS);
			}
		}
	}
	public ArrayList<Notification> getNotificationsForPendingStatus(boolean status)
	{
		ArrayList<Notification> tempN=new ArrayList<Notification>();
		for(int i =0;i<notifications.size();i++)
		{
			if(notifications.get(i).getPendingStatus()==status)
			{
				tempN.add(notifications.get(i));
			}
		}
		return tempN;
	}
	public ArrayList<Account> getAppUsers()
	{
		return addressBook.getAppUsers();
	}
	
	//this will make all the necessary changes
	public Group deleteFriendFromGroup(Account friend,String groupName)
	{
		String username=friend.getUsername();
		for(int i=0;i<groups.size();i++)
		{
			if(groups.get(i).getGroupName().equals(groupName))
					{
						Group tempG=groups.get(i);
						tempG.removeUser(friend);
						ArrayList<String> transactionIds=tempG.getTransactionIds();
						//ArrayList<Transaction> temps=getTransactions(tempG.getTransactionIds());
						for(int k=0;k<transactionIds.size();k++)
						{
							for(int l=0;l<transactions.size();l++)
							{
								if(transactions.get(l).getTransactionId().equals(transactionIds.get(k))&&(transactions.get(l).getSender().equals(username)||transactions.get(l).getReceiver().equals(username)))
								{
									Transaction temp=transactions.get(l);
									temp.deleteGroupName(groupName);
									transactions.set(l, temp);
									transactionIds.remove(k);
									break;
								}
							}
						}
						tempG.setTransactionIds(transactionIds);
						groups.set(i, tempG);
						return tempG;
						
					}
		}
		return null;
	}
	public ArrayList<String> getTransactionsByUserInGroup(Account account,Group group)
	{
		ArrayList<String> tempIds=group.getTransactionIds();
		String username=account.getUsername();
		ArrayList<String> transactionIds=new ArrayList<String>();
		for(int i=0;i<transactions.size();i++)
		{
			Transaction tempT=transactions.get(i);
			if(tempT.getSender().getUsername().equals(username)||tempT.getReceiver().getUsername().equals(username))
			{
				if(tempT.getTransactionId().equals(tempIds.get(i)))
					transactionIds.add(tempIds.get(i));
			}
		}
		return transactionIds;
	}
}
