package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.TagModifier;

public class TagRequest extends HttpServlet 
{
	private TagModifier modifier;

	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		modifier=(TagModifier)context.getBean("tagModifier");
		String userId=request.getParameter("userId");
		String transactionId=request.getParameter("transactionId");
		String tag=request.getParameter("tag");
		String actionType=request.getParameter("actionType");
		boolean status=false;
		PrintWriter writer=null;
		try
		{
			if(actionType.equals("add"))
			{
				status=modifier.addTag(tag, transactionId, userId);
			}
			else if(actionType.equals("delete"))
				status=modifier.deleteTag(tag, transactionId, userId);
//			else if(actionType.equals("update"))
//				status=modifier.updateTag(tag, transactionId, userId);
			writer=response.getWriter();
			writer.println(status);
			//writer.close();
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
	}
	private ApplicationContext getContext()
	{
		ApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
