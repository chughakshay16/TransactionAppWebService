package com.project.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.Register;
import com.project.dom.Friend;
import com.project.manager.impl.FriendCreatorImpl;
import com.project.xml.XMLFriendObject;

public class RegistrationRequest extends HttpServlet {
	private Register register;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// here we will assume that we are putting all the parameters on the
		// client side(so nothing will be null here)
		PrintWriter writer=null;
		try
		{
		ApplicationContext context = getContext();
		register = (Register) context.getBean("register");
		String userName = req.getParameter("username");
		//System.out.println("hello");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String dob = req.getParameter("dob");
		//System.out.println("date of birth is:"+dob);
		String gender = req.getParameter("gender");
		String phone1 = req.getParameter("phone1");
		String password = req.getParameter("password");
		writer = resp.getWriter();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		
			date = dateFormat.parse(dob);
			//System.out.println("before regsitration");
			String result = register.register(userName, password, firstname,
					lastname, date, gender, phone1);
			//System.out.println("the result is :"+result);
			resp.setContentType("text/plain");
			if(result!=null)
				writer.println(result);
			else writer.println("fail");
			writer.flush();
			} catch (Exception e) {
			//System.out.println(e.getMessage());
				e.printStackTrace();
			System.out.println("error in registration request");
			writer.flush();
		} finally {
			writer.close();
		}

	}

	// here the exception handling is solely done by XMLFriendObject(so include
	// the code in that class to handle the same)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// how are we gonna send both name,value pairs and xml together from client(solved)
		//here the friendCollection object has the userId value as well
		PrintWriter writer=null;
		ServletInputStream stream=null;
		//BufferedReader reader=null;
		ApplicationContext context=getContext();
		register=(Register)context.getBean("register");
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try
		{
			//System.out.println("hellllooo111");
			stream = req.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(
					stream));
			char[] charBuffer = new char[1024];
			int bytesRead = -1;
			   while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
			    stringBuilder.append(charBuffer, 0, bytesRead);
			   }
			//String str=stream.toString();
			//System.out.println(str
//			int letter;
//			BufferedReader reader=new BufferedReader();
//			while((letter=stream.read())!=-1)
//			{
//				System.out.println(letter);
//			}
			ArrayList<Friend> friends = new ArrayList<Friend>();
			//System.out.println("hellllooo");
			String body = stringBuilder.toString();
			//System.out.println(body);
			//reader=req.getReader();
//			StringBuffer buffer=new StringBuffer();
//			int letter;
//			while((letter=reader.read())!=-1)
//			{
//				buffer.append(letter);
//				
//			}
//			System.out.println("the content of buffer is "+buffer.toString());
			StreamSource source =new StreamSource(new StringReader(body));
			FriendCreatorImpl impl=XMLFriendObject.createFriendCollectionObject(source);
			friends = impl.getFriends();
			System.out.println("helluuuuu");
			if(friends!=null)
			{
				//System.out.println("hellllooo22222");
				resp.setContentType("text/plain");
				//System.out.println("hellllooo6666");
				writer = resp.getWriter();
				//System.out.println("hellllooo3333");
				//FriendCreatorImpl impl=XMLFriendObject.createFriendCollectionObject(source);
				String userId=impl.getUserId();
				boolean value=register.registerNext(userId,friends);
				writer.println(value);
				//System.out.println("hellllooo4444");
			}
			writer.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error occured in regsitration request"+e.getMessage());
			writer.flush();
		}
		finally
		{
			stream.close();
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
