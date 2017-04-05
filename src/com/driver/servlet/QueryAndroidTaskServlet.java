package com.driver.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.bean.Task;
import com.driver.dao.TaskDao;
import com.driver.util.Constants;
import com.driver.util.Convert2Json;

public class QueryAndroidTaskServlet extends BaseServlet {

	@Override
	protected void dispatchTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Task> queryTask = TaskDao.queryTask(Constants.TABLE_ANDROID);
		
		String json = Convert2Json.listTaskJson(queryTask);
		
		PrintWriter writer = resp.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}

}
