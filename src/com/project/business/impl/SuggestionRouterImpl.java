package com.project.business.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;

import com.project.business.SuggestionRouter;
import com.project.dao.AccountDao;
import com.project.dao.SuggestionDao;
import com.project.dom.Account;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.SuggestionNotFoundException;
import com.project.manager.AccountManager;
import com.project.manager.NotificationCreator;
import com.project.manager.SuggestionCreator;
import com.project.manager.User;
import com.project.manager.UserManager;
import com.project.utils.UserUtils;

//bean injection still needs to be done
public class SuggestionRouterImpl implements SuggestionRouter, InitializingBean {

	private AccountManager accountManager=null;
	private UserManager userManager=null;
	private SuggestionCreator suggestionCreator=null;
	private SuggestionDao suggestionDao=null;
	private NotificationCreator notificationCreator=null;
	private AccountDao accountDao=null;
	private UserUtils utils=null;
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	// this function doesnt check for failed DAO calls(so do fix this)
	@Override
	public boolean send(String userId, ArrayList<String> transactionIds,
			float netValue) {
		// TODO Auto-generated method stub
		// Account senderAccount = accountManager.getAccountByUserId(userId);
		Account senderAccount = accountManager.getAccountByUserId(userId);
		if (senderAccount == null) {
			try {
				senderAccount = accountDao.getByUserId(userId);
				if (senderAccount == null)
					return false;
			} catch (AccountNotFoundException e) {
				System.out.println(e.getMessage());
				return false;
			}

		}
		User sender;// = userManager.getUser(senderAccount);
		if (userManager.hasUser(senderAccount)) {
			sender=utils.reinitializeUser(senderAccount);
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender = userManager.getUser(senderAccount);
		ArrayList<Transaction> transactions = sender
				.getTransactions(transactionIds);
		// this below statement also sets the receiver objects depending on the
		// transactions
		Suggestion newSuggestion = suggestionCreator.createSuggestion(
				senderAccount, transactions, netValue);
		String suggestionId = suggestionDao.addSuggestion(newSuggestion);
		if (suggestionId == null) {
			suggestionId = suggestionDao.addSuggestion(newSuggestion);
			if (suggestionId == null) {
				// an exception should be thrown
				return false;
			}
		}
		newSuggestion.setSuggestionId(suggestionId);
		ArrayList<SuggestionReceiver> receivers = newSuggestion.getReceivers();
		sender.addSuggestion(newSuggestion);
		for (int i = 0; i < receivers.size(); i++) {
			// we are creating seperate notification object for each receiver so
			// each one of them has its own identity
			Notification newNotification = notificationCreator
					.createNotification(senderAccount, transactionIds,
							netValue, true, false,
							newSuggestion.getSuggestionId(), receivers.get(i)
									.getReceiver().getUserId());
			User receiver;
			if (!userManager.hasUser(receivers.get(i).getReceiver())) {
				receiver = userManager.reInitializeUser(receivers.get(i)
						.getReceiver());
				// fix this
			}
			receiver = userManager.getUser(receivers.get(i).getReceiver());
			receiver.addNewNotification(newNotification);
			userManager.setUser(receiver);
		}
		return true;
	}

	@Override
	public boolean response(Notification notification) {
		// TODO Auto-generated method stub
		// User sender = userManager.getUser(notification.getSender());
		User sender;
		if (!userManager.hasUser(notification.getSender())) {
			sender=utils.reinitializeUser(notification.getSender());
			if(sender==null)
			{
				//throw exception
				return false;
			}
		}
		sender = userManager.getUser(notification.getSender());
		String receiverUserId = notification.getOwnerId();
		// Account account;
		Account account = accountManager.getAccountByUserId(receiverUserId);
		if (account == null) {
			try {
				account = accountDao.getByUserId(receiverUserId);
				if (account == null)
					return false;
			} catch (AccountNotFoundException e) {
				System.out.println(e.getMessage());
				return false;
			}

		}
		sender.setAcceptanceStatus(account, notification.getAcceptanceStatus(),
				notification.getSuggestionId());
		Account receiverAccount = accountManager
				.getAccountByUserId(receiverUserId);
		if (receiverAccount == null) {
			try {
				receiverAccount = accountDao.getByUserId(receiverUserId);
				if (receiverAccount == null)
					return false;
			} catch (AccountNotFoundException e) {
				System.out.println(e.getMessage());
				return false;
			}

		}
		User receiver;// =userManager.getUser(receiverAccount);
		if (!userManager.hasUser(receiverAccount)) {
			receiver=utils.reinitializeUser(receiverAccount);
			if(receiver==null)
			{
				//throw exception
				return false;
			}
		}
		receiver = userManager.getUser(receiverAccount);
		receiver.deleteNotification(notification);
		// check for all the other status values(logic)
		// the statement below modifies the acceptanceStatus by the specific
		// ownerID
		boolean success = false;
		try {
			success = suggestionDao.modifySuggestionReceiver(notification,
					"acceptanceStatus");
			if (!success) {
				success = suggestionDao.modifySuggestionReceiver(notification,
						"acceptanceStatus");
				if (!success) {
					// an exception should be thrown for transaction management
					return false;
				}
			}
		} catch (SuggestionNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return success;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public SuggestionCreator getSuggestionCreator() {
		return suggestionCreator;
	}

	public void setSuggestionCreator(SuggestionCreator suggestionCreator) {
		this.suggestionCreator = suggestionCreator;
	}

	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}

	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}

	public NotificationCreator getNotificationCreator() {
		return notificationCreator;
	}

	public void setNotificationCreator(NotificationCreator notificationCreator) {
		this.notificationCreator = notificationCreator;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("initializing beans for SuggestionRouterImpl");
		if (accountManager == null || userManager == null || accountDao == null
				|| accountDao == null || suggestionCreator == null
				|| notificationCreator == null || suggestionDao == null||utils==null) {
			throw new NullPointerException("thrown from SuggestionRouterImpl");
		}
	}

	public void setUtils(UserUtils utils) {
		this.utils = utils;
	}

	public UserUtils getUtils() {
		return utils;
	}

}
