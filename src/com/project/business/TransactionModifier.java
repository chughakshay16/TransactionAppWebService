package com.project.business;
import java.util.Date;
public interface TransactionModifier 
{
	public String addTransaction(String senderUserId,String receiverUserId,Date date,float amount,String taglist,String description);//it returns the transactionIdz
	public boolean deleteTransaction(String transactionId,String userId);
	public boolean updateTransaction(String transactionId,String userId,String propertyName,String newValue);
	public boolean updateTransaction(String transactionId,String userId,float newAmount);
	public boolean updateTransaction(String transactionId,String userId,String newReceiverUsername);
}
