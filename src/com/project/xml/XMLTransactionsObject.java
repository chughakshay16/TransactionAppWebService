package com.project.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.dom.Transaction;
import com.project.manager.impl.TransactionCreatorImpl;

public class XMLTransactionsObject 
{
	public static void createTransactionsXMLStream(ArrayList<Transaction> transactions,File file)throws IOException
	{
		try
		{
			Marshaller marshaller=getMarshaller(true);
			TransactionCreatorImpl impl=new TransactionCreatorImpl();
			impl.setTransactions(transactions);
			marshaller.marshal(impl,file);
		}
		catch(JAXBException e)
		{}
		
	}
	public static void createTransactionsXMLStream(ArrayList<Transaction> transactions,ServletOutputStream stream)throws JAXBException
	{
		
			//Marshaller marshaller=getMarshaller();
			TransactionCreatorImpl impl=new TransactionCreatorImpl();
			impl.setTransactions(transactions);
			getMarshaller(true).marshal(impl,stream);
		
		
	}
	public static void createTransactionXMLStream(Transaction transaction,ServletOutputStream stream)
	{
		try
		{
			//Marshaller marshaller=getMarshaller();
			//TransactionCreatorImpl impl=new TransactionCreatorImpl();
			//impl.setTransactions(transactions);
			getMarshaller(false).marshal(transaction,stream);
		}
		catch(JAXBException e)
		{}
		
	}
	private static Marshaller getMarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(TransactionCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Transaction.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
		
	}
	//here true signifies that it should give back the marshaller for transactions
}
