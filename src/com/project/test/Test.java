package com.project.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.manager.impl.AccountManagerImpl;
import com.project.manager.impl.FriendCreatorImpl;
import com.project.manager.impl.GroupCreatorImpl;
import com.project.manager.impl.TransactionCreatorImpl;
import com.project.xml.XMLFriendObject;
import com.project.xml.XMLGroupObject;

public class Test{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			File file =new File("C:/merchandising/groups.xml");
			//File file2=new File("../resources/sample.xml");
			TransactionCreatorImpl impl=new TransactionCreatorImpl();
			AccountManagerImpl implA=new AccountManagerImpl();
			Date date= new Date();
			Account sender=implA.createAccount("chughakshay16", "160488", "akshay", "chugh", date, "male", "676868686");
			Account receiver=implA.createAccount("gsdgsg", "35423", "gwee", "gweg", date, "male", "42523562356");
			ArrayList<Account> users=new ArrayList<Account>();
			users.add(sender);
			users.add(receiver);
			ArrayList<String> transactionIds=new ArrayList<String>();
			transactionIds.add("fsdjfhf");
			transactionIds.add("asffshf");
			GroupCreatorImpl implG=new GroupCreatorImpl();
			Group g1=implG.createGroup("ffwfa", "fffqefefaefdfefg", transactionIds, users, sender);
			Group g2=implG.createGroup("hfahffh", "fjfasslafa", transactionIds, users, receiver);
			ArrayList<Group> groups=new ArrayList<Group>();
			groups.add(g1);
			groups.add(g2);
			XMLGroupObject.createGroupsXMLStream(groups, file);
			
			//Account sender=implA.createAccount("chughakshay16", "160488", "akshay", "chugh", date, "male", "676868686");
			Transaction t1=impl.createTransaction("321141412", sender, receiver, date, 100, "fsfge#fafa", "fafafaf", groups);
			Transaction t2=impl.createTransaction("738573", sender, receiver, date, 100, "#falfa#rhairh#ruiue", "fafafaf", groups);
			ArrayList<Transaction> transactions=new ArrayList<Transaction>();
			transactions.add(t1);
			transactions.add(t2);
			impl.setTransactions(transactions);
			//File file =new File()
			//FriendCreatorImpl impl=XMLFriendObject.createFriendObjects(file2);
			//System.out.println(impl.getUserId());
			//System.out.println(impl.getFriends().get(0).getFirstName());
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
		
	}

}
