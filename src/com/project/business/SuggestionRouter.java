package com.project.business;

import java.util.ArrayList;

import com.project.dom.Notification;

public interface SuggestionRouter 
{
	
	public boolean send(String userId,ArrayList<String> transactionIds,float netValue);//just remember that this is the only method that creates new Suggestion 
	//this above method will contain some more details about the net value 
	public boolean response(Notification notificationObject);
	//public boolean reject(String userId);
}
