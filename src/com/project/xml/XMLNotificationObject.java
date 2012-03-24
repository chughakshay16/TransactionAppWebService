package com.project.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.project.dom.Notification;
import com.project.manager.impl.NotificationCreatorImpl;

public class XMLNotificationObject 
{
	public static void createNotificationXMLStream(Notification n,ServletOutputStream stream)
	{
		try
		{
			getMarshaller(false).marshal(n,stream);
		}
		catch(JAXBException e)
		{}
	}
	public static Notification createNotificationObject(File file)throws IOException
	{
		try
		{
			return (Notification)getUnmarshaller(false).unmarshal(file);
		}
		catch(JAXBException e)
		{}
		return null;
	}
	public static Notification createNotificationObject(ServletInputStream stream)throws IOException
	{
		try
		{
			return (Notification)getUnmarshaller(false).unmarshal(stream);
		}
		catch(JAXBException e)
		{}
		return null;
	}
	private static Unmarshaller getUnmarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(NotificationCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Notification.class);
		Unmarshaller m=context.createUnmarshaller();
		//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
	}
	
	public static void createNotificationXMLStream(Notification n,File file)
	{
		try
		{
			getMarshaller(false).marshal(n,file);
		}
		catch(JAXBException e)
		{}
	}
	public static void createNotificationsXMLStream(ArrayList<Notification> notifications,ServletOutputStream stream)throws JAXBException
	{

			NotificationCreatorImpl impl=new NotificationCreatorImpl();
			impl.setNotifications(notifications);
			getMarshaller(true).marshal(impl, stream);
		
	}
	private static Marshaller getMarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(NotificationCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Notification.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
		
	}
}
