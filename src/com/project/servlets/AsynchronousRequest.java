package com.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.Synchronizer;
import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Notification;
import com.project.dom.Transaction;
import com.project.xml.XMLAccountObject;
import com.project.xml.XMLGroupObject;
import com.project.xml.XMLNotificationObject;
import com.project.xml.XMLTransactionsObject;

public class AsynchronousRequest extends HttpServlet 
{
	private Synchronizer synchronizer;

	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		synchronizer=(Synchronizer)context.getBean("synchronizer");
		String userId=request.getParameter("userId");
		String contentType=request.getParameter("contentType");
		Object returnedObject=null;
		ServletOutputStream stream=null;
		try
		{
			returnedObject=synchronizer.asynchronousProcess(userId, contentType);
			stream=response.getOutputStream();
			
				if(contentType.equals("transactions"))
				{
					ArrayList<Transaction> transactions=(ArrayList<Transaction>)returnedObject;
					XMLTransactionsObject.createTransactionsXMLStream(transactions, stream);
				}
				else if(contentType.equals("groups"))
				{
					ArrayList<Group> groups=(ArrayList<Group>)returnedObject;
					XMLGroupObject.createGroupsXMLStream(groups, stream);
				}
				else if(contentType.equals("notifications"))
				{
					ArrayList<Notification> notifications=(ArrayList<Notification>)returnedObject;
					XMLNotificationObject.createNotificationsXMLStream(notifications, stream);
				}
				else if(contentType.equals("appUsers"))
				{
					ArrayList<Account> accounts=(ArrayList<Account>)returnedObject;
					XMLAccountObject.createAccountsXMLStream(accounts, stream);
				}
				stream.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			stream.flush();
		}
		finally
		{
			stream.close();
		}
		
	}
	private ApplicationContext getContext()
	{
		ApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
