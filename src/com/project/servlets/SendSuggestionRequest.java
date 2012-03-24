package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.SuggestionRouter;

public class SendSuggestionRequest extends HttpServlet 
{
	private SuggestionRouter router;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
	{
		ApplicationContext context=getContext();
		router=(SuggestionRouter)context.getBean("suggestionRouter");
		String userId=request.getParameter("userId");
		String transactionIdz=request.getParameter("transactionIdz");
		StringTokenizer tokenizer=new StringTokenizer(transactionIdz,",");
		ArrayList<String> transactionIds=new ArrayList<String>();
		//String netValue=request.getParameter("netValue");
		PrintWriter writer=null;
		try
		{
			float netValue=new Float(Float.parseFloat(request.getParameter("netValue"))).floatValue();
			int i=0;
			while(tokenizer.hasMoreTokens())
			{
				transactionIds.add(tokenizer.nextToken());
				i++;
			}
			writer=response.getWriter();
			boolean success=router.send(userId, transactionIds, netValue);
			writer.println(success);
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
