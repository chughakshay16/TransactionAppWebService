package com.project.business;

import com.project.manager.User;

public interface Synchronizer 
{
	public User synchronize(String userId);
	public Object asynchronousProcess(String userId,String contentType);
}
