package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.GroupModifier;

public class AddFriendstoGroupRequest extends HttpServlet 
{
	private GroupModifier modifier;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		modifier=(GroupModifier)context.getBean("groupModifier");
		String userId=request.getParameter("userId");
		String groupName=request.getParameter("groupName");
		String namesList=request.getParameter("usernames");
		StringTokenizer tokenizer=new StringTokenizer(namesList,",");
		int count=tokenizer.countTokens();
		String usernames[]=new String[count];
		int i=0;
		while(tokenizer.hasMoreTokens())
		{
			usernames[i]=tokenizer.nextToken();
			i++;
		}
		PrintWriter writer=null;

		try
		{
			writer=response.getWriter();
			writer.println(modifier.addFriendsToGroup(userId, usernames, groupName));
			writer.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			writer.flush();
		}
		finally
		{
			writer.close();
		}
		//modifier.addFriendsToGroup(userId, usernames, groupName);
	}
	private ApplicationContext getContext()
	{
		ApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
