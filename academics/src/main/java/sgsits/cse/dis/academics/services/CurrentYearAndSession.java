package sgsits.cse.dis.academics.services;

import java.util.Calendar;

public class CurrentYearAndSession {

	public String getCurrentSession(int adYear) {
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = (cal.get(Calendar.MONTH)) + 1;
		String session = null;
		if (curMonth >= 1 && curMonth <= 6)
			session = (curYear - 1) + " - " + curYear;
		else if (curMonth >= 7 && curMonth <= 12)
			session = curYear + " - " + (curYear + 1);
		return session;
	}

	public String getCurrentYear(int adYear) {
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = (cal.get(Calendar.MONTH)) + 1;
		int syear = curYear - adYear;
		String year = null;
		if (curMonth >= 1 && curMonth <= 6) {
			if (syear == 1)
				year = "I";
			if (syear == 2)
				year = "II";
			if (syear == 3)
				year = "III";
			if (syear == 4)
				year = "IV";
		} else if (curMonth >= 7 && curMonth <= 12) {
			if (syear == 0)
				year = "I";
			if (syear == 1)
				year = "II";
			if (syear == 2)
				year = "III";
			if (syear == 3)
				year = "IV";
		}
		return year;
	}

	public String getCurrentSemester(int adYear) {
		Calendar cal = Calendar.getInstance();
		int curMonth = (cal.get(Calendar.MONTH)) + 1;
		String semester = null;
		if (curMonth >= 1 && curMonth <= 6)
			semester = "B";
		else if (curMonth >= 7 && curMonth <= 12)
			semester = "A";
		return semester;
	}
}
