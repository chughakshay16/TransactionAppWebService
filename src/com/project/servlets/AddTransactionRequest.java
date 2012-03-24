package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.TransactionModifier;

public class AddTransactionRequest extends HttpServlet 
{
	private TransactionModifier modifier;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		modifier=(TransactionModifier)context.getBean("transactionModifier");
		String senderId=request.getParameter("senderId");
		String receiverId=request.getParameter("receiverId");
		Date date=new Date();//havent decided if this value is generated at the server or sent from the client
		PrintWriter writer=null;
		try
		{
			float amount=new Float(Float.parseFloat(request.getParameter("amount"))).floatValue();
			String tagList=request.getParameter("taglist");
			String desc=request.getParameter("description");
			writer=response.getWriter();
			writer.println(modifier.addTransaction(senderId, receiverId, date, amount, tagList, desc));
			
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
