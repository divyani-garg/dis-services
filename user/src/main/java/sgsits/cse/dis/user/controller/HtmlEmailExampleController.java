package sgsits.cse.dis.user.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HtmlEmailExampleController {

	@Autowired
	public JavaMailSender emailSender;

	@ResponseBody
	@RequestMapping("/sendHtmlEmail")
	public String sendHtmlEmail() throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		boolean multipart = true;

		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

		String htmlMsg = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"   <head>\r\n" + 
				"      <title>Title of the document</title>\r\n" + 
				"      <style>\r\n" + 
				"         .button {\r\n" + 
				"         background-color: #1c87c9;\r\n" + 
				"         border: none;\r\n" + 
				"         color: white;\r\n" + 
				"         padding: 20px 34px;\r\n" + 
				"         text-align: center;\r\n" + 
				"         text-decoration: none;\r\n" + 
				"         display: inline-block;\r\n" + 
				"         font-size: 20px;\r\n" + 
				"         margin: 4px 2px;\r\n" + 
				"         cursor: pointer;\r\n" + 
				"         }\r\n" + 
				"		 .container {\r\n" + 
				"		position: relative;\r\n" + 
				"		text-align: center;\r\n" + 
				"		color: white;\r\n" + 
				"		}\r\n" + 
				"		.centered {\r\n" + 
				"		position: absolute;\r\n" + 
				"		top: 25%;\r\n" + 
				"		left: 47%;\r\n" + 
				"		transform: translate(-50%, -50%);\r\n" + 
				"		}\r\n" + 
				"      </style>\r\n" + 
				"   </head>\r\n" + 
				"   <body>\r\n" + 
				"	<center>\r\n" + 
				"	<div class=\"container\">\r\n" + 
				"		<img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0ZgyCA6trA0FZ1r6dNw9OWQeqXlCPaGsh6Ib-FBlgXac8x_lqbQ\" height=\"500\" width=\"700\">\r\n" + 
				"		<div class=\"centered\">\r\n" + 
				"			<h3>RESET PASSWORD</h3>\r\n" + 
				"			<h4>Click the button below to reset your password</h4>\r\n" + 
				"			<a href=\"https://www.w3docs.com/\" class=\"button\">Click Here</a>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"	</center>\r\n" + 
				"   </body>\r\n" + 
				"</html>";

		message.setContent(htmlMsg, "text/html");

		helper.setTo("divyanigarg09@gmail.com");

		helper.setSubject("Test send HTML email");

		this.emailSender.send(message);

		return "Email Sent!";
	}

}