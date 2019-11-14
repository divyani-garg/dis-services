package sgsits.cse.dis.academics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import sgsits.cse.dis.academics.controller.MoodleController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
public class AcademicsApplication {
	
	private static MoodleController moodleController;
	
	public AcademicsApplication(MoodleController moodleController)
	{
		AcademicsApplication.moodleController = moodleController;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AcademicsApplication.class, args);
		//moodleController.startQuizData();
	}
}