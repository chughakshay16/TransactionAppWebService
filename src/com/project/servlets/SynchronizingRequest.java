package com.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.Synchronizer;
import com.project.xml.XMLUserObject;

public class SynchronizingRequest extends HttpServlet 
{
	private Synchronizer synchronizer;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
    {
		ApplicationContext context =getContext();
		synchronizer=(Synchronizer)context.getBean("synchronizer");
		ServletOutputStream stream=null;
		try
		{
			String userId=req.getParameter("userId");
			stream=resp.getOutputStream();
			XMLUserObject.createUserXMLStream(synchronizer.synchronize(userId), stream);
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
