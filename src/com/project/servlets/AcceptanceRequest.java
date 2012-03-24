package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.business.SuggestionRouter;
import com.project.dom.Notification;
import com.project.xml.XMLNotificationObject;

public class AcceptanceRequest extends HttpServlet {
	private SuggestionRouter router;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// String receiverId=request.getParameter("receiverId");
		ApplicationContext context = getContext();
		router = (SuggestionRouter) context.getBean("suggestionRouter");
		ServletInputStream stream = request.getInputStream();
		// read the XML notification file
		PrintWriter writer = null;
		try {
			Notification tempN = XMLNotificationObject
					.createNotificationObject(stream);

			writer = response.getWriter();
			writer.println(router.response(tempN));
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
