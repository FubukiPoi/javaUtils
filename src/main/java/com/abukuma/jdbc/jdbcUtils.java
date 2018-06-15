package com.abukuma.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcUtils {
	private static String url = jdbcPropUtil.getValue("connUrl");
	private static String username = jdbcPropUtil.getValue("username");
	private static String password = jdbcPropUtil.getValue("password");
	private static String driver = jdbcPropUtil.getValue("driverClassName");
	// 线程本地变量,多线程环境下使用
	private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<Connection>();

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
	      for(int i=0; i<100; i++){
	         new Thread("" + i){
	            public void run(){
	            	try {
	            		getConnection();
						Thread.sleep(1000);
						closeConnection();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	         }.start();
	      }
	}

	public static Connection getConnection() {
		Connection connection = connThreadLocal.get();
		if (connection == null) {
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
				connThreadLocal.set(connection);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(connection);
		return connection;
	}

	public static void closeConnection() {
		Connection connection = connThreadLocal.get();
		if (connection != null) {
			try {
				connection.close();
				connThreadLocal.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
