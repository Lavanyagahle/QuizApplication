package com.QuizApp;

import java.util.List;

public class Questions {
	private int question_number;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private char correctAnswer;
	//private Object questionNumber;
	//private int questionNumber;

	public Questions(  String question, String option1, String option2, String option3, String option4, char correctAnswer) {
		super();
		//this.question_number=question_number;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
	}
	public int getQuestion_number() {
		return question_number;
	}
	public void setQuestion_number(int question_number) {
		this.question_number = question_number;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public char getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(char correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	@Override
	public String toString() {
		return "Questions [question_number=" + question_number + ", question=" + question + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", correctAnswer="
				+ correctAnswer + "]";
	}

	

	
}
