package sgsits.cse.dis.academics.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import sgsits.cse.dis.academics.services.QuizService;

@RestController
public class MoodleController {

	@Autowired
	QuizService quizService;
	
	public void startQuizData() {
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				quizService.fetchQuizData();
			}
		}, 0, 1, TimeUnit.DAYS);
	}
	
}