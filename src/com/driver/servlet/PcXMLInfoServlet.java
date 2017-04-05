package com.driver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.util.Convert2Json;
import com.driver.xml.Data;
import com.driver.xml.Task;
import com.driver.xml.XmlParser;

public class PcXMLInfoServlet extends BaseServlet {

	@Override
	protected void dispatchTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		String page = req.getParameter("page");
		if (page == null) {
			return;
		}
		try {
			com.driver.xml.Process process = XmlParser.parseXmlData();
			Task task = process.getTaskMap().get(page);
			String json = null;
			if (task != null) {
				List<Data> dataList = task.getDataList();
				json = Convert2Json.listDataJson(dataList);
			}
			System.out.println("result = " + json);
			writer.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
