package sgsits.cse.dis.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;

import sgsits.cse.dis.user.model.Email;
import sgsits.cse.dis.user.service.EmailService;

@Controller
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	public void sendSimpleEmail(String to, String subject, String text) 
	{
		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Email.DIS_EMAIL);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailService.sendEmail(message);	
	}
	
	
	
}
