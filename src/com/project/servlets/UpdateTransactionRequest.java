package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.business.TransactionModifier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.TransactionModifier;

public class UpdateTransactionRequest extends HttpServlet 
{
	private TransactionModifier modifier;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		modifier=(TransactionModifier)context.getBean("transactionModifier");
		String transactionId=request.getParameter("transactionId");
		//String amount=request.getParameter("amount");
		float amount=new Float(Float.parseFloat(request.getParameter("amount"))).floatValue();
		String username=request.getParameter("username");
		String propertyName=request.getParameter("propertyName");
		String newValue=request.getParameter("value");
		String userId=request.getParameter("userId");
		boolean status=false;
		PrintWriter writer=null;
		try
		{
			if(amount==0)
			{
				if(username==null)
				{
					status=modifier.updateTransaction(transactionId, userId, propertyName, newValue);
				}
				else
					status=modifier.updateTransaction(transactionId, userId, username);
			}
			else
				status=modifier.updateTransaction(transactionId, userId, amount);
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
