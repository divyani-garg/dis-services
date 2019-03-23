package sgsits.cse.dis.academics.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.academics.model.Quiz;
import sgsits.cse.dis.academics.repo.QuizRepository;

@Service
public class MoodleQuizService {

	public static Statement stmt;
	public static Connection con;

	@Autowired
	QuizRepository quizRepository;

	public void startQuizData() {
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				fetchQuizData();
			}
		}, 0, 1, TimeUnit.HOURS);
	}

	public void fetchQuizData() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moodle", "root", "root");
			stmt = con.createStatement();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date timemodified;
			String query = "SELECT q.name, g.grade, c.shortname, u.username, from_unixtime(g.timemodified)"
					+ "           FROM mdl_quiz as q, mdl_quiz_grades as g, mdl_course as c, mdl_user as u "
					+ "            where q.course = c.id and q.id = g.quiz and g.userid = u.id";
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
					/*
					 * System.out.println("Quiz Name: " + rs.getString(1));
					 * System.out.println("Grade: " + rs.getFloat(2));
					 * System.out.println("Course Name: " + rs.getString(3));
					 * System.out.println("User Name: " + rs.getString(4));
					 * System.out.println("Time: " + formatter.format(rs.getDate(5)) + "\n");
					 */
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
