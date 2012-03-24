package com.project.dao;

import com.project.dom.Account;
import com.project.dom.SuggestionReceiver;

public interface SuggestionReceiverDao
{
	public SuggestionReceiver createSuggestionReceiver(Account receiver,boolean acceptanceStatus,boolean pendingStatus);
	
}