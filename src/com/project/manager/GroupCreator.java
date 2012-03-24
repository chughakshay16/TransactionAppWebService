package com.project.manager;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Group;

public interface GroupCreator 
{
	public Group createGroup(Account account,String groupName,String groupDescription);
	public Group createGroup(ResultSet group,ResultSet groupX,ResultSet transactionX);
	public ArrayList<Group> createGroups(ResultSet groupsResultSet,ResultSet groupXResultSet,ResultSet transactionXResultSet);
}
