package com.driver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.bean.Task;
import com.driver.dao.TaskDao;
import com.driver.util.Constants;
import com.driver.util.Convert2Json;

public class QueryPcTaskServlet extends BaseServlet {

	@Override
	protected void dispatchTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String packageName = req.getParameter("packageName");
		String page = req.getParameter("page");
		
		List<Task> queryTask = TaskDao.queryTask(Constants.TABLE_PC);
		List<Task> tempList = new ArrayList<Task>();
		for(Task task : queryTask){
			if (task.getPackageName().equals(packageName) && task.getIndex().equals(page)) {
				tempList.add(task);
			}
		}
		String json = Convert2Json.listTaskJson(tempList);
		
		System.out.println("pc = " + json);
		PrintWriter writer = resp.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}

}
