package com.QuizApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class DatabaseConnection 
{
	public static Connection databaseConnection() 
	{
		String url="jdbc:mysql://localhost:3306/quizdb";
		String username="root";
		String pass="@Lavanya#2220";
		Connection connection=null;
		try {
	
				connection= DriverManager.getConnection(url,username,pass);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   return connection;
	
	}
}
