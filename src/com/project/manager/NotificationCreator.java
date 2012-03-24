package com.project.manager;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Notification;

public interface NotificationCreator 
{
	public Notification createNotification(Account sender,ArrayList<String> transactionIds,float netValue,boolean pendingStatus,boolean acceptanceStatus,String suggestionId,String ownerId);
}
