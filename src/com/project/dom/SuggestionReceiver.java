package com.project.dom;



public class SuggestionReceiver 
{
	private Account receiver;
	private boolean acceptanceStatus;
	private boolean pendingStatus;
	public SuggestionReceiver(){}
	public SuggestionReceiver(Account receiver,boolean accStatus,boolean pendStatus)
	{
		this.receiver=receiver;
		this.acceptanceStatus=accStatus;
		this.pendingStatus=pendStatus;
	}
	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}
	public Account getReceiver() {
		return receiver;
	}
	public void setAcceptanceStatus(boolean acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
	public boolean getAcceptanceStatus() {
		return acceptanceStatus;
	}
	public void setPendingStatus(boolean pendingStatus) {
		this.pendingStatus = pendingStatus;
	}
	public boolean getPendingStatus() {
		return pendingStatus;
	}
}
