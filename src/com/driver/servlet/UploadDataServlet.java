package com.driver.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driver.bean.Task;
import com.driver.dao.TaskDao;
import com.driver.util.Constants;
import com.driver.util.FileHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UploadDataServlet extends BaseServlet {

	@Override
	protected void dispatchTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream inputStream = req.getInputStream();
		PrintWriter writer = resp.getWriter();

		if (inputStream == null) {
			writer.write("false");
			writer.flush();
			writer.close();
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while((s = br.readLine()) != null){
			sb.append(s);
		}
		String data = sb.toString();
		System.out.println("data = " + data);
		Gson gson = new Gson();
		Map<String, Task> tasks = gson.fromJson(data, new TypeToken<Map<String, Task>>(){}.getType());
		
		boolean flag = TaskDao.updateTask(tasks,Constants.TABLE_ANDROID);
		writer.write(String.valueOf(flag));
		writer.flush();
		writer.close();
	}
	
	
}
