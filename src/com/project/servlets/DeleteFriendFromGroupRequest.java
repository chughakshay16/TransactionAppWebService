package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.GroupModifier;

public class DeleteFriendFromGroupRequest extends HttpServlet {
	private GroupModifier modifier;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ApplicationContext context = getContext();
		modifier = (GroupModifier) context.getBean("groupModifier");
		String userId = req.getParameter("userId");
		String friendUsername = req.getParameter("username");
		String groupName = req.getParameter("groupName");
		// modifier.deleteFriendFromGroup(userId, friendUsername, groupName);
		PrintWriter writer = null;

		try {
			writer = resp.getWriter();
			writer.println(modifier.deleteFriendFromGroup(userId,
					friendUsername, groupName));
			writer.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			writer.flush();
		} finally {
			writer.close();
		}
	}

	private ApplicationContext getContext() {
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		return context;
	}
}
