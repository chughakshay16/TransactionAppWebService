package com.project.manager.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.manager.GroupCreator;
@XmlRootElement(name="collection")
public class GroupCreatorImpl implements GroupCreator {

	private ArrayList<Group> groups;//dont use this in bean injection
	public GroupCreatorImpl(){}
	@Override
	public Group createGroup(Account account, String groupName,
			String groupDescription) {
		// TODO Auto-generated method stub
		return new Group(account,groupName,groupDescription);
	}

	@Override
	public Group createGroup(ResultSet group, ResultSet groupX,
			ResultSet transactionX) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Group> createGroups(ResultSet groupsResultSet,
			ResultSet groupXResultSet, ResultSet transactionXResultSet) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Group createGroup(String groupName,String groupDescription,ArrayList<String> transactionIds,ArrayList<Account> users,Account account){
		Group group = new Group();
		group.setAccount(account);
		group.setGroupDescription(groupDescription);
		group.setGroupName(groupName);
		group.setTransactionIds(transactionIds);
		group.setUsers(users);
		
		return group;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
	@XmlElementWrapper(name="groups")
	@XmlElement(name="group")
	public ArrayList<Group> getGroups() {
		return groups;
	}

}
