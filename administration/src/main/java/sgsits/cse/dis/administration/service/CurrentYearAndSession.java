package sgsits.cse.dis.administration.service;

import java.util.Calendar;

public class CurrentYearAndSession {

	public String getCurrentSession() {
		Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = (cal.get(Calendar.MONTH)) + 1;
		String session = null;
		if (curMonth >= 1 && curMonth <= 6)
			session = (curYear - 1) + "-" + curYear;
		else if (curMonth >= 7 && curMonth <= 12)
			session = curYear + "-" + (curYear + 1);
		return session;
	}

}
