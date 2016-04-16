package com.halycon.maze.simulation.dialog;

import java.util.Map;

public class Question {

	Map<QuestionNumber, String> questions;
	
	public Question(Map<QuestionNumber, String> questions) {
		this.questions = questions;
	}

	public String getQuestion(QuestionNumber number) {
		return questions.get(number);
	}
}
