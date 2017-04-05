package com.driver.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.util.Constants;

public abstract class BaseServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost");  
		resp.addHeader("Access-Control-Allow-Methods",  
	            "GET, POST, PUT, DELETE, OPTIONS");  
		resp.addHeader("Access-Control-Allow-Headers",  
	            "origin, content-type, accept, x-requested-with, sid, mycustom, smuser"); 
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.addHeader("Connection", "keep-alive");

		dispatchTask(req,resp);
		
	}

	protected abstract void dispatchTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException ;
}
