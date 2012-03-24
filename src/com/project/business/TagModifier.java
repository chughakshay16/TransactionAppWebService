package com.project.business;

public interface TagModifier 
{
	public boolean addTag(String tag,String transactionId,String userId);
	public boolean deleteTag(String tag,String transactionId,String userId);
	public boolean updateTag(String newTag,String transactionId,String userId);
}
