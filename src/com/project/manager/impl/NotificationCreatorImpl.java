package com.project.manager.impl;

import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Notification;
import com.project.manager.NotificationCreator;

public class NotificationCreatorImpl implements NotificationCreator {

	private ArrayList<Notification> notifications;
	public NotificationCreatorImpl()
	{}
	@Override
	public Notification createNotification(Account sender,
			ArrayList<String> transactionIds, float netValue, boolean pendingStatus,
			boolean acceptanceStatus,String suggestionId,String ownerId) {
		// TODO Auto-generated method stub
		return new Notification(sender,transactionIds,netValue,pendingStatus,acceptanceStatus,suggestionId,ownerId);
	}
	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}
	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

}
