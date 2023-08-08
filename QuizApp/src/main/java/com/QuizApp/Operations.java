package com.QuizApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Operations 
{
	private static String username;
	private static String email;
	private static long phone;
	private static String password;
	static Connection connection=null;
	static PreparedStatement statement=null;
	static ResultSet rs;
	private static int correctAnsCount=0;
	private static int wrongAnsCount=0;
	
	
	    //method for creating account
		public static void account() throws SQLException 
		{
			Scanner sc=new Scanner(System.in);
			ResultSet rs;
			connection=DatabaseConnection.databaseConnection();
			System.out.println("Enter your name");
			username = sc.next();
			System.out.println("Enter your email");
		    email=sc.next();
			System.out.println("Enter your phone number");
			phone=sc.nextLong();
			System.out.println("Enter your password");
			password=sc.next();
			sc.close();
			String insertQuery="insert into user_details(username,email,phone_number,password) values(?,?,?,?)";
		    statement= connection.prepareStatement(insertQuery);
		    statement.setString(1,username);
		    statement.setString(2,email);
		    statement.setLong(3,phone);
		    statement.setString(4,password);
		    int i=statement.executeUpdate();
			if(i>0) 
			{
				System.out.println("Account is created succesfully");
				System.out.println("*********************************************************");
				System.out.println();
			}else 
			{
				System.err.println("Account is not created");
			}
			
         }
		
		
		
		
		//method for login
	public static void login() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("*********************************************************");
		System.out.println("Enter option to process");
		System.out.println("A. user login");
		System.out.println("B. Admin login");
		System.out.println("*********************************************************");
		char option=sc.next().charAt(0);
		switch(option) {
		case 'A': System.out.println("Enter your name");
		          String userName=sc.next();
		          System.out.println("Enter your email");
		          String userEmail=sc.next();
		          System.out.println("Enter your password");
		          String userPassword=sc.next();
		          if(isValidUser(userEmail,userPassword)) 
		          {
		        	  System.out.println("User login successful!");
		        	  System.out.println("*********************************************************");
		        	  user_task(userName,userEmail);
		          }else 
		          {
		        	  System.err.println("Enter valid email or password");
		          }
		          break;
		case 'B': System.out.println("Enter your name");
		          String name=sc.next();
		          System.out.println("Enter your password");
		          password=sc.next();
		          if(isValidAdmin(name,password)) 
		          {
		        	  System.out.println("Admin login successful");
		        	  System.out.println("*********************************************************");
		        	  admin_task();
		          }else 
		          {
		        	  System.err.println("Enter valid information!");
		        	  System.out.println("*********************************************************");
		          }
		          break;
		  default:
			  System.err.println("Enter valid option (A or B): ");
		}
		sc.close();
	}
	
	//for checking valid user
	private static boolean isValidUser(String userEmail, String password) throws SQLException 
	{
	    connection=DatabaseConnection.databaseConnection();
		String query="select * from login where email=? and password=?";
	    statement=connection.prepareStatement(query);
		statement.setString(1, userEmail);
		statement.setString(2,password);
	    rs=statement.executeQuery();
	    boolean isValid=rs.next();
	    rs.close();
	    statement.close();
	    connection.close();
	    return isValid;
		
	}
	
	//for checking valid admin
	private static boolean isValidAdmin(String name, String password) throws SQLException
	{
	    connection=DatabaseConnection.databaseConnection();
		String query="select * from adminlogin where adminname=? and password=?";
		statement=connection.prepareStatement(query);
		statement.setString(1,name);
		statement.setString(2,password);
	    rs=statement.executeQuery();
	    boolean isValid=rs.next();
	    rs.close();
	    statement.close();
	    connection.close();
	    return isValid;
		
	}

	
	 //meethod for user to perform task
	public static void user_task(String userName,String userEmail) throws SQLException 
	{
		Scanner sc=new Scanner(System.in);
		while(true)
		{
			System.out.println("*********************************************************");
			System.out.println("1. Do you want to give exam");
			System.out.println("2. view result");
			System.out.println("3. update information");
			System.out.println("4. logout");
			System.out.println("*********************************************************");
			System.out.println();
			int option=sc.nextInt();
			switch(option) 
			{
				case 1: 
					showQuestionsToUser(userName,userEmail);
					System.out.println("*********************************************************");
					break;
				case 2:
					view_result();
					System.out.println("*********************************************************");
					break;
				case 3:
					UpdateUser();
					System.out.println("*********************************************************");
					break;
				case 4:
					System.out.println("Thank You For Using My Quiz Application");
					System.out.println("*********************************************************");
					System.exit(0);
					
					break;
				default:
					System.err.println("Enter valid option");
			}
		}
	}
	
	
	
	
	//method for showing questions to user for performing quiz
		public static void showQuestionsToUser(String userName, String userEmail) throws SQLException 
		{
			connection=DatabaseConnection.databaseConnection();
			String query="select * from quiz_questions";
			statement=connection.prepareStatement(query);
			rs=statement.executeQuery();
			  int totalQuestions=0;
			//int questionNumber=1;
			while(rs.next()) {
				int questionNum=rs.getInt("question_number");
				String question=rs.getString("question");
				String option1=rs.getString("option1");
				String option2=rs.getString("option2");
				String option3=rs.getString("option3");
				String option4=rs.getString("option4");
				System.out.println("*********************************************************");
				System.out.println(questionNum +": "+ question);
				System.out.println("A. "+option1);
				System.out.println("B. "+option2);
				System.out.println("C. "+option3);
				System.out.println("D. "+option4);
				Scanner sc=new Scanner(System.in);
				String userAnswer=null;
				boolean validInput=false;
				while(!validInput) 
				{
					System.out.println("Enter your answer (A,B,C,D): ");
					String userInput=sc.next().toUpperCase();
					if(userInput.equals("A"))
					{
						userAnswer="A";
						validInput=true;
					}
					else if(userInput.equals("B")) 
					{
						userAnswer="B";
						validInput=true;
								
					}
					else if(userInput.equals("C"))
					{
						userAnswer="C";
						validInput=true;
					}else if(userInput.equals("D"))
					{
						userAnswer="D";
						validInput=true;
					}else 
					{
						System.err.println("Invalid input.Please enter A,B,C, or D.");
					}
					System.out.println("*********************************************************");
				}
				
				String correctAnswer=rs.getString("correct_answer");
				
				
				if(userAnswer.equals(correctAnswer)) 
				{
					System.out.println("Correct answer!");
					correctAnsCount++;
					
				}else 
				{
					System.err.println("Wrong answer!");
					wrongAnsCount++;
				}
				//questionNumber++;
				
			}

		     String countQuery="select count(*) as total_questions from quiz_questions";
		     statement =connection.prepareStatement(countQuery);
		     rs=statement.executeQuery();
		   
		     if(rs.next()) 
		     {
		    	 totalQuestions=rs.getInt("total_questions");
		    	 
		     }
			float percentage=((float)100*correctAnsCount)/totalQuestions;
			int totalQue = totalQuestions; // Assuming questionNumber holds the total number of questions
			int correctAnswers = correctAnsCount;
			int wrongAnswers = wrongAnsCount;
			//float percentage = ((float) 100 * correctAnswers) / totalQuestions;
			String performance = getPerformance(percentage);
		
			System.out.println("\nQuiz completed");
			System.out.println("**********************************************************");
			System.out.println("Total Questions: "+totalQue);
			System.out.println("Correct answers: "+correctAnsCount);
			System.out.println("Wrong answer: "+wrongAnsCount);
			//System.out.println("Percentage: "+percentage)
			System.out.printf("Percentage: %.2f",percentage);
			System.out.println("%");
			System.out.println("Performance: "+getPerformance(percentage));
			insertUserQuizData(userName,userEmail,totalQuestions,correctAnswers,wrongAnswers,percentage,performance);
		    System.out.println("***********************************************************");
		
	 }
		
		
		
		
		//method for checking performance of user/student 
		public static String getPerformance(float percentage)
		{
			if(percentage>=85 && percentage<=100) 
			{
				return "Excellent";
			}
			else if(percentage>=75 && percentage<=84) 
			{
				return " Very good";
				
			}
			else if(percentage>=50 && percentage<=74) 
			{
				return"Good";
			}
			else if(percentage>=35 && percentage<=49) 
			{
				return "Low";
			}else 
			{
				 return "You have failed";
				
			}
			
			
		}
		

		//method for entering quiz data after performing quiz by user
		public static void insertUserQuizData(String userName, String email2, int totalQuestions, int correctAnswer, int wrongAnswers, float percentage, String performance) throws SQLException
		{
		    // Establish a database connection (ensure you have the correct database URL, username, and password)
		    connection = DatabaseConnection.databaseConnection();
		
		
		    // Create the SQL query to insert the user quiz data
		    String query = "INSERT INTO user_quiz_data (user_name,email, total_questions, correct_answer, wrong_answers, percentage, performance) VALUES (?,?, ?, ?, ?, ?, ?)";
		
		    // Create a PreparedStatement to safely execute the query
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setString(1, userName);
		    preparedStatement.setString(2,email2);
		    preparedStatement.setInt(3, totalQuestions);
		    preparedStatement.setInt(4, correctAnswer);
		    preparedStatement.setInt(5, wrongAnswers);
		    preparedStatement.setFloat(6, percentage);
		    preparedStatement.setString(7, performance);
		
		    // Execute the query
		    preparedStatement.executeUpdate();
		
		    // Close the resources
		    preparedStatement.close();
		    connection.close();
		}
		

	   
		
		//method is for user to view result
		public static void view_result() throws SQLException 
		{
			connection=DatabaseConnection.databaseConnection();
			Scanner sc= new Scanner(System.in);
			System.out.println("Enter your name ");
			String username=sc.nextLine();
			System.out.println("Enter your email to view your quiz record: ");
			String userEmail=sc.nextLine();
			
			String query="select * from user_quiz_data where user_name=? and email=?";
		    statement=connection.prepareStatement(query);
		    statement.setString(1,username);
		    statement.setString(2, userEmail);
		    rs=statement.executeQuery();
		    if(rs.next()) 
		    {
				System.out.println("**********Quiz result for "+username+ "***************");
				System.out.println("Total Questions: "+rs.getInt("total_questions"));
				System.out.println("Correct Answer: "+rs.getInt("correct_answer"));
				System.out.println("Wrong Answer: "+rs.getInt("wrong_answers"));
				System.out.println("Percentage: "+rs.getFloat("percentage"));
				System.out.println("Performance: "+rs.getString("performance"));
				System.out.println("********************************************************");
			}else
			{
				System.err.println("No results found for user: "+userEmail);
			}
			
		
		}
		//For user to update information
		public static void UpdateUser() throws SQLException
		{
			connection=DatabaseConnection.databaseConnection();
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter email");
			email=sc.next();
			String query="select * from user_details where email=?";
			statement=connection.prepareStatement(query);
			statement.setString(1, email);
			rs=statement.executeQuery();
			
			if(rs.next()) 
			{
				System.out.println("Choose option to perform operation");
				System.out.println("1.Name, 2.Email, 3.Phone, 4.Password ");
				int option=sc.nextInt();
				String s;
				int i;
				switch(option) 
				{
					case 1:
						System.out.println("Enter name to change");
						String name=sc.next();
						s="update user_details set username=? where email=?";
						statement= connection.prepareStatement(s);
						statement.setString(1,name);
						statement.setString(2,email);
						i=statement.executeUpdate();
						if(i>0)
						{
							System.out.println("Name is updated");
							System.out.println("****************************************************");
						}
						else
						{
							System.err.println("Name is not updated");
						}
						break;
					case 2:
						System.out.println("Enter email to update");
						String newEmail=sc.next();
						s="update user_details set email=? where email=?";
						statement=connection.prepareStatement(s);
						statement.setString(1,newEmail);
						statement.setString(2,email);
						i= statement.executeUpdate();
						if(i>0) 
						{
							System.out.println("email is updated ");
							System.out.println("****************************************************");
						}
						else 
						{
							System.err.println("email is not updated");
						}
						break;
					case 3:
						System.out.println("Enter Phone number to change");
						long phone=sc.nextLong();
						s="update user_details set phone_number=? where email=?";
						statement=connection.prepareStatement(s);
						statement.setLong(1,phone);
						statement.setString(2,email);
						i=statement.executeUpdate();
						if(i>0)
						{
							System.out.println("phone number is updated");
							System.out.println("**************************************************");
						}
						else 
						{
							System.err.println("phone number is not updated");
						}
						break;
					case 4:
						System.out.println("Enter password to change");
						String password=sc.next();
						s="update user_details set password=? where email=?";
						statement=connection.prepareStatement(s);
						statement.setString(1,password);
						statement.setString(2,email);
						i=statement.executeUpdate();
						if(i>0) 
						{
							System.out.println("password is updated");
							System.out.println("***************************************************");
						}
						else 
						{
							System.err.println("password is not updated");
						}
						break;
					default:
						System.err.println("Invalid option you choosed!... please choose correct option....");
					
				}
			}
			else
			{
				System.err.println(email+" does not exist");
			}
			
		}
		


		//For Admin task
		public static void admin_task() throws SQLException 
		{
			Scanner sc=new Scanner(System.in);
			while(true) 
			{
				System.out.println("*********************************************************");
				System.out.println("1. Do you want to add question");
				System.out.println("2. view user's result");
				System.out.println("3. view details of users");
				System.out.println("4. logout");
				System.out.println("**********************************************************");
				int option=sc.nextInt();
				switch(option) 
				{
					case 1: 
						addQuestions();
						System.out.println("*******************************************************");
						break;
					case 2:
						viewResultOfUser();
						System.out.println("*******************************************************");
						break;
					case 3:
						view_usersDetails();
						System.out.println("*******************************************************");
						break;
					case 4:
						System.out.println("Thank You.....For using quiz application");
						System.exit(0);
						break;
					default:
						System.err.println("Enter valid option");
				}
		    }
		
		}

	
	
	//method for admin to insert questions
	public static void addQuestions() throws SQLException 
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("*********************************************************");
		System.out.println("Enter the question");
		String question=sc.nextLine();
		System.out.println("Enter option A: ");
		String optionA=sc.nextLine();
		System.out.println("Enter option B");
		String optionB=sc.nextLine();
		System.out.println("Enter option C");
		String optionC=sc.nextLine();
		System.out.println("Enter option D");
		String optionD=sc.nextLine();
		System.out.println("Enter correct Answer");
		String correctAnswer=sc.next();
		connection=DatabaseConnection.databaseConnection();
		String insertQuery="insert into quiz_questions(question,option1,option2,option3,option4,correct_answer) values(?,?,?,?,?,?)";
		statement=connection.prepareStatement(insertQuery);
		statement.setString(1,question);
		statement.setString(2,optionA);
		statement.setString(3,optionB);
		statement.setString(4,optionC);
		statement.setString(5,optionD);
		statement.setString(6,correctAnswer);
		int i=statement.executeUpdate();
		if(i>0) 
		{
			System.out.println("Question added successfully");
			System.out.println("*********************************************************");
		}else 
		{
			System.err.println("Failed to add question");
		}
		
	}
	
	
	public static void viewResultOfUser() throws SQLException 
	{
		connection=DatabaseConnection.databaseConnection();
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Student's name ");
		String username=sc.nextLine();
		System.out.println("Enter Student's email to view quiz record: ");
		String userEmail=sc.nextLine();
		
		String query="select * from user_quiz_data where user_name=? and email=?";
	    statement=connection.prepareStatement(query);
	    statement.setString(1,username);
	    statement.setString(2, userEmail);
	    rs=statement.executeQuery();
	    if(rs.next()) 
	    {
			System.out.println("**********Quiz result for "+username+ "***************");
			System.out.println("Total Questions: "+rs.getInt("total_questions"));
			System.out.println("Correct Answer: "+rs.getInt("correct_answer"));
			System.out.println("Wrong Answer: "+rs.getInt("wrong_answers"));
			System.out.println("Percentage: "+rs.getFloat("percentage"));
			System.out.println("Performance: "+rs.getString("performance"));
			//System.out.println("********************************************************");
		}else
		{
			System.err.println("No results found for user: "+userEmail);
		}
		
	
	}
	//for checking valid information for user while login
	
	  public static boolean validateUserLogin(String userName ,String userEmail )
	  throws SQLException { connection=DatabaseConnection.databaseConnection();
	  String query="select email from login where username=? and userEmail=?";
	  statement=connection.prepareStatement(query);
	  statement.setString(1,userName); statement.setString(2, userEmail);
	  rs=statement.executeQuery();
	  boolean isValidUser=rs.next(); 
	  rs.close();
	  statement.close();
	  connection.close(); 
	  return isValidUser;
	 
	  }
	 
		//view userdetails method for admin
	public static void view_usersDetails() throws SQLException 
	{
		connection=DatabaseConnection.databaseConnection();
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter student's name");
		String userName=sc.nextLine();
		System.out.println("Enter student's email to view quiz record: ");
		String userEmail=sc.nextLine();
		
		String query="select * from user_details where username=? and email=?";
	    statement=connection.prepareStatement(query);
	    statement.setString(1,userName);
	    statement.setString(2,userEmail);
	    rs=statement.executeQuery();
	    if(rs.next()) 
	    {
			System.out.println("***********Details of "+userName+ "****************");
			System.out.println("User Id: "+rs.getInt("id"));
			System.out.println("User's name: "+rs.getString("username"));
			System.out.println("User's Email: "+rs.getString("email"));
			System.out.println("User's Phone Number: "+rs.getLong("phone_number"));
			System.out.println("User's password: "+rs.getString("password"));
		}
	    else
	    {
			System.err.println("No results found for user: "+userEmail);
		}
		
	}
	
	
	}

