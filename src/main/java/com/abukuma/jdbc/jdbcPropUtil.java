package com.abukuma.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class jdbcPropUtil {

	private static String JDBC_PROP_URL = "cfg/properties/jdbc.properties";

	public static String getValue(String key) {
		
		Properties prop = null;
		InputStream in = null;
		String value = null;

		try {
			prop = new Properties();
			in = new FileInputStream(JDBC_PROP_URL);
			prop.load(in);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
}
