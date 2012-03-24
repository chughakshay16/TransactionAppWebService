package com.project.xml;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.dom.Group;
import com.project.manager.impl.GroupCreatorImpl;

public class XMLGroupObject 
{
	public static void createGroupXMLStream(Group g,ServletOutputStream stream)
	{
		try
		{
			getMarshaller(false).marshal(g,stream);
		}
		catch(JAXBException e)
		{}
	}
	public static void createGroupsXMLStream(ArrayList<Group> groups,ServletOutputStream stream)throws JAXBException
	{
	
			GroupCreatorImpl impl=new GroupCreatorImpl();
			impl.setGroups(groups);
			getMarshaller(true).marshal(impl, stream);
		
	}
	public static void createGroupsXMLStream(ArrayList<Group> groups,File file)throws JAXBException
	{
	
			GroupCreatorImpl impl=new GroupCreatorImpl();
			impl.setGroups(groups);
			getMarshaller(true).marshal(impl, file);
		
	}
	private static Marshaller getMarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(GroupCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Group.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
		
	}
}
