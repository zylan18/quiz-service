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
		
		quiz.setQuestionsIds(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
		
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
//		Optional<Quiz> quiz=quizDao.findById(id);
//		List<Question> questonFromDB=quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser=new ArrayList<>();
//		
//		for(Question q : questonFromDB) {
//			QuestionWrapper qw=new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
//			questionsForUser.add(qw);
//		}
		
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get(); 
//		List<Question>questions=quiz.getQuestions();
		int right=0;
//		int i=0;
//		for(Response response:responses) {
//			if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
//				right++;
//			}
//			i++;
//		}
		return new ResponseEntity<>(right,HttpStatus.OK);
	}
	
	
}
