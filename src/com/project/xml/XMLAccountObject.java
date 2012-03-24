package com.project.xml;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.dom.Account;

public class XMLAccountObject 
{
	public static void createAccountsXMLStream(ArrayList<Account> accounts,ServletOutputStream stream)throws JAXBException
	{
		
			AccountCollection accountCollection=new AccountCollection();
			accountCollection.setAccounts(accounts);
			getAccountsMarshaller().marshal(accountCollection,stream);
		
	}
	public static void createAccountXMLStream(Account account,ServletOutputStream stream)throws IOException
	{
		try
		{
			//AccountCollection accountCollection=new AccountCollection();
			//accountCollection.setAccounts(accounts);
			getAccountMarshaller().marshal(account,stream);
		}
		catch(JAXBException e)
		{}
	}
	private static Marshaller getAccountsMarshaller()throws JAXBException
	{
		JAXBContext context=JAXBContext.newInstance(AccountCollection.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
	}
	private static Marshaller getAccountMarshaller()throws JAXBException
	{
		JAXBContext context=JAXBContext.newInstance(Account.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
	}
}
