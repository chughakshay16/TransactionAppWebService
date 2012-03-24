package com.project.dom;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name="notification")
@XmlType(propOrder={"netValue", "transactionIds","sender","pendingStatus","suggestionId","ownerId","acceptanceStatus"})
public class Notification 
{
	private float netValue;
	private ArrayList<String> transactionIds;
	private Account sender;
	private boolean pendingStatus;
	private String suggestionId;
	private String ownerId;
	private boolean acceptanceStatus;
	public Notification(){}
	public Notification(Account sender,ArrayList<String> transactionIds,float netValue,boolean pendingStatus,boolean acceptanceStatus,String suggestionId,String ownerId)
	{
		this.sender=sender;
		this.transactionIds=transactionIds;
		this.netValue=netValue;
		this.pendingStatus=pendingStatus;
		this.suggestionId=suggestionId;
		this.ownerId=ownerId;
		this.acceptanceStatus=acceptanceStatus;
	}
	public void setNetValue(float netValue) {
		this.netValue = netValue;
	}
	@XmlElement(name = "netValue")
	public float getNetValue() {
		return netValue;
	}
	public void setTransactionIds(ArrayList<String> transactionIds) {
		this.transactionIds = transactionIds;
	}
	@XmlElementWrapper(name = "transactionIds")
	@XmlElement(name = "transactionId")
	public ArrayList<String> getTransactionIds() {
		return transactionIds;
	}
	public void setSender(Account sender) {
		this.sender = sender;
	}
	@XmlElement(name = "sender")
	public Account getSender() {
		return sender;
	}
	public void setPendingStatus(boolean pendingStatus) {
		this.pendingStatus = pendingStatus;
	}
	@XmlElement(name = "pendingStatus")
	public boolean getPendingStatus() {
		return pendingStatus;
	}
	public void setSuggestionId(String suggestionId) {
		this.suggestionId = suggestionId;
	}
	@XmlElement(name = "suggestionId")
	public String getSuggestionId() {
		return suggestionId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@XmlElement(name = "ownerId")
	public String getOwnerId() {
		return ownerId;
	}
	public void setAcceptanceStatus(boolean acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
	@XmlElement(name = "acceptanceStatus")
	public boolean getAcceptanceStatus() {
		return acceptanceStatus;
	}
	
}
