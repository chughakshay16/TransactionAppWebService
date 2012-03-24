package com.project.xml;

import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.dom.Group;
import com.project.dom.Suggestion;
import com.project.manager.impl.GroupCreatorImpl;
import com.project.manager.impl.SuggestionCreatorImpl;

public class XMLSuggestionObject 
{
	public static void createGroupXMLStream(Suggestion s,ServletOutputStream stream)
	{
		try
		{
			getMarshaller(false).marshal(s,stream);
		}
		catch(JAXBException e)
		{}
	}
	public static void createGroupsXMLStream(ArrayList<Suggestion> suggestions,ServletOutputStream stream)
	{
		try
		{
			//GroupCollection groupCollection=new GroupCollection();
			//groupCollection.setGroups(groups);
			SuggestionCreatorImpl impl=new SuggestionCreatorImpl();
			impl.setSuggestions(suggestions);
			getMarshaller(true).marshal(impl, stream);
		}
		catch(JAXBException e)
		{}
	}
	private static Marshaller getMarshaller(boolean value)throws JAXBException
	{
		JAXBContext context;
		if(value)
		{
			context=JAXBContext.newInstance(SuggestionCreatorImpl.class);
		}
		else
			context=JAXBContext.newInstance(Suggestion.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
		
	}
}
