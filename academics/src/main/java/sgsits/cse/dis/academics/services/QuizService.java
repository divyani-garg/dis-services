package sgsits.cse.dis.academics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.academics.model.Quiz;
import sgsits.cse.dis.academics.repo.QuizRepository;

@Service
public class QuizService {
	
	@Autowired
	QuizRepository quizRepository;
	
	public void save(Quiz quiz) {
	    quizRepository.save(quiz);
	} 
}
