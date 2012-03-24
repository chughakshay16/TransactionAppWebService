package com.project.manager;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.project.dom.Friend;

public interface FriendCreator 
{
	public Friend createFriend(String firstName,String lastName,String phone1);
	public Friend createFriend(ResultSet friend);
	public ArrayList<Friend> createFriends(ResultSet friends);
}
