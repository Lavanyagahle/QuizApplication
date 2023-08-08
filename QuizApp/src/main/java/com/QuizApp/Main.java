package com.QuizApp;
import java.sql.SQLException;
import java.util.*;
public class Main 
{
		public static void main(String[] args) throws SQLException
		{
			Scanner sc=new Scanner(System.in);
	 
	
				System.out.println("Enter your choice to perform operation");
				System.out.println("1. Create your student account");
				System.out.println("2. Login");
				System.out.println("3. Exit from application");
			 
				int choice=sc.nextInt();
	
				switch(choice) 
				{
					case 1: Operations.account();
						break;
					case 2:
						 Operations.login();
						 break;
					case 3:
						System.out.println("Thanks for using our application");
						System.exit(0);
				    default:
				    	System.err.println("Enter valid choice");
				}
	
		
		}	
}