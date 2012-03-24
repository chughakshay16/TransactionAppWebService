package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.TransactionModifier;

public class DeleteTransactionRequest extends HttpServlet 
{
	private TransactionModifier modifier;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		modifier=(TransactionModifier)context.getBean("transactionModifier");
		String transactionId=request.getParameter("transactionId");
		String userId=request.getParameter("userId");
		PrintWriter writer=null;
		try
		{
			 writer=response.getWriter();
			writer.println(modifier.deleteTransaction(transactionId, userId));
			//modifier.deleteTransaction(transactionId, userId);
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
