package com.project.manager.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.project.dom.Friend;
import com.project.manager.FriendCreator;
@XmlRootElement(name="collection")
public class FriendCreatorImpl implements FriendCreator {

	private String userId;
	private ArrayList<Friend> friends;
	//private String userId;
	public FriendCreatorImpl()
	{}
	@Override
	public Friend createFriend(String firstName, String lastName, String phone1) {
		// TODO Auto-generated method stub
		return new Friend(firstName,lastName,phone1);
	}

	@Override
	public Friend createFriend(ResultSet friend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Friend> createFriends(ResultSet friends) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setFriends(ArrayList<Friend> friends) {
		this.friends = friends;
	}
	
	@XmlElementWrapper(name="friends")
	@XmlElement(name="friend")
	public ArrayList<Friend> getFriends() {
		return friends;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@XmlElement(name="userId")
	public String getUserId() {
		return userId;
	}

}
