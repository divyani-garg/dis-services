package sgsits.cse.dis.academics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import sgsits.cse.dis.academics.services.MoodleQuizService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
public class AcademicsApplication {
	
	private static MoodleQuizService moodleQuizService;
	
	public AcademicsApplication(MoodleQuizService moodleQuizService)
	{
		AcademicsApplication.moodleQuizService = moodleQuizService;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AcademicsApplication.class, args);
        moodleQuizService.startQuizData();	
	}
}