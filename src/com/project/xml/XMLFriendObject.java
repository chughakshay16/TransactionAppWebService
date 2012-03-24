package com.project.xml;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.project.dom.Friend;
import com.project.manager.impl.FriendCreatorImpl;

public class XMLFriendObject 
{
	public static void createFriendsXMLStream(ArrayList<Friend> friends,ServletOutputStream stream)throws JAXBException
	{
		
			Marshaller marshaller=getMarshaller(true);
			FriendCreatorImpl impl=new FriendCreatorImpl();
			impl.setFriends(friends);
			marshaller.marshal(impl,stream);
		
		
	}
	public static void createFriendsXMLStream(ArrayList<Friend> friends,File file)throws JAXBException
	{
		
			Marshaller marshaller=getMarshaller(true);
			FriendCreatorImpl impl=new FriendCreatorImpl();
			impl.setFriends(friends);
			marshaller.marshal(impl,file);
		
	}
	
	public static void createFriendXMLStream(Friend friend,ServletOutputStream stream)throws JAXBException
	{
		
				getMarshaller(false).marshal(friend,stream);
		
	}
	public static Friend createFriendObject(ServletInputStream stream)
	{
		try
		{
			return (Friend)getUnmarshaller(false).unmarshal(stream);
		}
		catch(JAXBException e){}
		return null;
		
	}
	public static FriendCreatorImpl createFriendObjects(ServletInputStream stream)
	{
		try
		{
			return (FriendCreatorImpl)getUnmarshaller(true).unmarshal(stream);
		}
		catch(JAXBException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public static FriendCreatorImpl createFriendObjects(File file)
	{
		try
		{
			return ((FriendCreatorImpl)getUnmarshaller(true).unmarshal(file));
			//return impl.getFriends();
		}
		catch(JAXBException e){}
		return null;
	}
	public static FriendCreatorImpl createFriendCollectionObject(ServletInputStream stream)
	{
		try
		{
			return (FriendCreatorImpl)getUnmarshaller(true).unmarshal(stream);
			//return impl.getFriends();
		}
		catch(JAXBException e){
			return null;
		}
		//return null;
	}
	public static FriendCreatorImpl createFriendCollectionObject(BufferedReader reader)
	{
		try
		{
			return (FriendCreatorImpl)getUnmarshaller(true).unmarshal(reader);
			//return impl.getFriends();
		}
		catch(JAXBException e){}
		return null;
	}
	public static FriendCreatorImpl createFriendCollectionObject(StreamSource source)
	{
		try
		{
			return (FriendCreatorImpl)getUnmarshaller(true).unmarshal(source);
			//return impl.getFriends();
		}
		catch(JAXBException e){}
		return null;
	}
	private static Marshaller getMarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(FriendCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Friend.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
	}
	private static Unmarshaller getUnmarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(FriendCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Friend.class);
		Unmarshaller m=context.createUnmarshaller();
		//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
	}
}
