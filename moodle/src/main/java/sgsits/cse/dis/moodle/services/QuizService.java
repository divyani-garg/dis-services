package sgsits.cse.dis.moodle.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.moodle.model.Quiz;
import sgsits.cse.dis.moodle.repo.QuizRepository;

@Service
public class QuizService {
	
	public static Statement stmt;
	public static Connection con;

	@Autowired
	QuizRepository quizRepository;

	public void fetchQuizData() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moodle", "root", "root");
			stmt = con.createStatement();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date timemodified; 
			
			String query = "SELECT q.name, g.grade, c.shortname, u.username, from_unixtime(g.timemodified) \r\n" + 
					"FROM mdl_quiz as q, mdl_quiz_grades as g, mdl_course as c, mdl_user AS u\r\n" + 
					"where q.course = c.id and q.id = g.quiz and g.userid = u.id and u.username LIKE \"0801%\" ";
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs != null)
				while (rs.next()) {
					Quiz quiz = new Quiz();
					quiz.setCreatedBy((long) 0);
					quiz.setCreatedDate(formatter.format(new Date()));				
					quiz.setCourseName(rs.getString(3));	
					quiz.setQuizName(rs.getString(1));
					quiz.setGrade(rs.getBigDecimal(2));
					quiz.setUsername(rs.getString(4));
					quiz.setTime(rs.getString(5));
					if (quiz != null)
						quizRepository.save(quiz);
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
