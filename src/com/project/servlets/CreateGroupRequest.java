package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.GroupModifier;

public class CreateGroupRequest extends HttpServlet 
{
	private GroupModifier modifier;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
    {
		ApplicationContext context=getContext();
		modifier=(GroupModifier)context.getBean("groupModifier");
		String userId=req.getParameter("userId");
		String groupName=req.getParameter("groupName");
		String description=req.getParameter("groupDescription");
		PrintWriter writer=null;
		try
		{
			writer=resp.getWriter();
			writer.println(modifier.createGroup(userId, groupName, description));
			writer.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			writer.flush();
		}
		writer.close();
		
    }
	private ApplicationContext getContext()
	{
		ApplicationContext context = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
