package com.telusko.quizservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telusko.quizservice.model.Quiz;



public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
}
