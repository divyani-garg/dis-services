package sgsits.cse.dis.academics.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import sgsits.cse.dis.academics.services.MoodleQuizService;

public class MoodleController {

	@Autowired
	MoodleQuizService moodleQuizService;

	public void getexecute() {
	}
}
