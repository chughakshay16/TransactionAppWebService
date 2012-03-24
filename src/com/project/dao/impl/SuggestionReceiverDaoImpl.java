package com.project.dao.impl;

import com.project.dao.SuggestionReceiverDao;
import com.project.dom.Account;
import com.project.dom.SuggestionReceiver;

public class SuggestionReceiverDaoImpl
	implements SuggestionReceiverDao
{	
	public SuggestionReceiver createSuggestionReceiver(Account receiver,boolean acceptanceStatus,boolean pendingStatus)
	{
		SuggestionReceiver suggestionReceiver = new SuggestionReceiver(receiver,acceptanceStatus,pendingStatus);
		return suggestionReceiver;
	}
}