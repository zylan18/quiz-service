package com.telusko.quizservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.QuizDto;
import com.telusko.quizservice.model.Response;
import com.telusko.quizservice.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {
	@Autowired
	QuizService quizService;
	
	@PostMapping("create")
	public ResponseEntity<String>createQuiz(@RequestBody QuizDto quizDto){
		return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
		return quizService.getQuizQuestions(id);
	}
	
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer>submitQuiz(@PathVariable Integer id, @RequestBody List<Response>response){
		return quizService.calculateResult(id,response);
	}
	@GetMapping("/getAll")
	public ResponseEntity<List<QuizDto>> getAllQuiz(){
		return quizService.getAllQuiz();
	}
}
