package com.project.dom;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement(name = "transaction")
@XmlType(propOrder={"transactionId","sender","receiver","dateOfCreation","amount","tagList","description","groupNames"})
public class Transaction 
{
	private String transactionId;
	private Account sender;
	private Account receiver;
	private Date dateOfCreation;
	private float amount;
	private String tagList;
	private String description;
	private ArrayList<String> groupNames;
	public Transaction()
	{}
	public Transaction(String transactionId,Account sender,Account receiver,float amount,String taglist,String description,ArrayList<String> groupNames)
	{
		this.transactionId=transactionId;
		this.sender=sender;
		this.receiver=receiver;
		this.amount=amount;
		this.tagList=taglist;
		this.description=description;
		this.groupNames=groupNames;
		this.dateOfCreation=new Date();
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@XmlElement(name = "transactionId")
	public String getTransactionId() {
		return transactionId;
	}
	public void setReceiver(Account reciever) {
		this.receiver = reciever;
	}
	@XmlElement(name = "receiver")
	public Account getReceiver() {
		return receiver;
	}
	public void setSender(Account sender) {
		this.sender = sender;
	}
	@XmlElement(name = "sender")
	public Account getSender() {
		return sender;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	@XmlElement(name = "dateOfCreation")
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@XmlElement(name = "amount")
	public float getAmount() {
		return amount;
	}
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	@XmlElement(name = "tagList")
	public String getTagList() {
		return tagList;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setGroupNames(ArrayList<String> groupNames) {
		this.groupNames = groupNames;
	}
	@XmlElementWrapper(name = "groupNames")
	@XmlElement(name = "groupName")
	public ArrayList<String> getGroupNames() {
		return groupNames;
	}
	public void deleteGroupName(String groupName)
	{
		for(int i=0;i<groupNames.size();i++)
		{
			if(groupNames.get(i).equals(groupName))
			{
				groupNames.remove(i);
			}
		}
	}
	
}