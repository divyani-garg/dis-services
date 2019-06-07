package sgsits.cse.dis.academics.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.academics.model.Quiz;
import sgsits.cse.dis.academics.model.Scheme;
import sgsits.cse.dis.academics.repo.QuizRepository;
import sgsits.cse.dis.academics.repo.SchemeRepository;

@Service
public class QuizService {
	
	public static Statement stmt;
	public static Connection con;

	@Autowired
	QuizRepository quizRepository;
	
	@Autowired
	SchemeRepository schemeRepository;

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
			
			List<Scheme> schemes = schemeRepository.findAll();
			List<String> subjects = new ArrayList<>();
			for(Scheme scheme : schemes){
				subjects.add(scheme.getSubjectCode());
			}
			
			if (rs != null)
				while (rs.next()) {
					Quiz quiz = new Quiz();	
					
					String course = rs.getString(3);
					for(String sub : subjects) {
						//if(course.contains(sub)) {
							quiz.setCreatedBy((long) 0);
							quiz.setCreatedDate(formatter.format(new Date()));
							quiz.setCourseName(sub);
							quiz.setQuizName(rs.getString(1));
							quiz.setGrade(rs.getBigDecimal(2));
							quiz.setUsername(rs.getString(4));
							quiz.setTime(rs.getString(5));
					//		break;
						//}
					}
					
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
