package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.Register;

public class AuthenticationRequest extends HttpServlet 
{
	private Register register;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
    {
		ApplicationContext context=getContext();
		register=(Register)context.getBean("register");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		PrintWriter writer=null;
		try
		{
			boolean status=register.authenticate(username, password);
			resp.setContentType("text/plain");
			writer=resp.getWriter();
			writer.println(status);
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
	
    }
	private ApplicationContext getContext()
	{
		ApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
