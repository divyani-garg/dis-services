package sgsits.cse.dis.moodle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import sgsits.cse.dis.moodle.controller.MoodleController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class MoodleApplication {
	
private static MoodleController moodleController;
	
	public MoodleApplication(MoodleController moodleController)
	{
		MoodleApplication.moodleController = moodleController;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MoodleApplication.class, args);
		moodleController.startQuizData();
	}
}