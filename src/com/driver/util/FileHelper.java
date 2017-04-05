package com.driver.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileHelper {
	public static String txt2String(String filePath){
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String s = null;
			while((s = br.readLine()) != null){
				sb.append(s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
