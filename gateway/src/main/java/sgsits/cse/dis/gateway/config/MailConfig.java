package sgsits.cse.dis.gateway.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import sgsits.cse.dis.gateway.model.Email;

@Configuration
public class MailConfig {

	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername(Email.DIS_EMAIL);
		mailSender.setPassword(Email.DIS_PASSWORD);

		Properties props = mailSender.getJavaMailProperties();
		
		props.setProperty("proxySet","true");
        props.setProperty("socksProxyHost","10.25.0.42");
        props.setProperty("socksProxyPort","3128");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}