package sgsits.cse.dis.moodle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class Test {

	public static Statement stmt;
	public static Connection con;

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/moodle", "root", "root");
			stmt = con.createStatement();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

			String query = "SELECT q.name, g.grade, c.shortname, u.username, from_unixtime(g.timemodified)"
					+ "           FROM mdl_quiz as q, mdl_quiz_grades as g, mdl_course as c, mdl_user as u "
					+ "            where q.course = c.id and q.id = g.quiz and g.userid = u.id";
			ResultSet rs = stmt.executeQuery(query);

			if (rs != null)
				while (rs.next()) {
					System.out.println("Quiz Name: " + rs.getString(1));
					System.out.println("Grade: " + rs.getFloat(2));
					System.out.println("Course Name: " + rs.getString(3));
					System.out.println("User Name: " + rs.getString(4));
					System.out.println("Time: " + formatter.format(rs.getDate(5)) + "\n");
				}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
