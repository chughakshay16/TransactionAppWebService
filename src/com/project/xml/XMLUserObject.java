package com.project.xml;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.project.manager.User;
import com.project.manager.impl.TransactionCreatorImpl;

public class XMLUserObject 
{
	public static void createUserXMLStream(User user,ServletOutputStream stream)throws JAXBException
	{
		
			getMarshaller().marshal(user,stream);
		
	}
	private static Marshaller getMarshaller()throws JAXBException
	{
		JAXBContext context=JAXBContext.newInstance(User.class);
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return m;
		
	}
}
