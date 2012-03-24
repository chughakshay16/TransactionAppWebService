package com.project.business;

public interface GroupModifier 
{
	public boolean createGroup(String userId,String groupName,String description);
	public boolean deleteGroup(String userId,String groupName);
	public boolean addFriendsToGroup(String userId,String usernames[],String groupName);
	public boolean deleteFriendFromGroup(String userId,String userName,String groupName);
}
