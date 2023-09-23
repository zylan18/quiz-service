package com.telusko.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quizservice.dao.QuizDao;
import com.telusko.quizservice.feign.QuizInterface;
import com.telusko.quizservice.model.Question;
import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.Quiz;
import com.telusko.quizservice.model.QuizDto;
import com.telusko.quizservice.model.Response;

@Service
public class QuizService {
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuizInterface quizInterface;
	

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

//		
		List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();
		
		Quiz quiz=new Quiz();
		quiz.setTitle(title);
		quiz.setCategory(category);
		quiz.setQuestionsIds(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz=quizDao.findById(id).get();
		List<Integer> questionIds=quiz.getQuestionsIds();
		ResponseEntity<List<QuestionWrapper>>questions=quizInterface.getQuestionsFromId(questionIds);
		return (questions);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		ResponseEntity<Integer>score= quizInterface.getScore(responses);
		return score;
		
	}

	public ResponseEntity<List<QuizDto>> getAllQuiz() {
		List<Quiz> quizList=quizDao.findAll();
		List<QuizDto> responseList=new ArrayList<>();
		for(Quiz quiz:quizList) {
			QuizDto quizDto=new QuizDto();
			quizDto.setCategoryName(quiz.getCategory());
			quizDto.setTitle(quiz.getTitle());
			quizDto.setNumQuestions(quiz.getQuestionsIds().size());
			quizDto.setId(quiz.getId());
			responseList.add(quizDto);
		}
		return new ResponseEntity<List<QuizDto>>(responseList,HttpStatus.OK);
	}
	
	
}
