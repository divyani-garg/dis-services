package sgsits.cse.dis.academics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AcademicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicsApplication.class, args);
	}
}
