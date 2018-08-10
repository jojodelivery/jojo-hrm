package com.pin91.hrm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class JojoHrmUtils {

	private static Calendar calendar = Calendar.getInstance();
	public static final String ACTIVE = "ACTIVE";
	public static final String DATE_FORMAT = "dd-MM-yyyy";

	public static Date currentDate() {
		return calendar.getTime();
	}

	public static Date converStringToDate(String date) {
		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static int currentMonth() {
		return calendar.get(Calendar.MONTH) + 1; // 5
	}

	public static int currentYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static boolean isLate(String shiftStartTime, String inTime, Long toleranceLimit) {

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		try {
			Date start = timeFormat.parse(shiftStartTime);
			Date in = timeFormat.parse(inTime);
			long difference = in.getTime() - start.getTime();
			difference = difference / (1000 * 60);
			if (difference > toleranceLimit)
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Integer noOfDaysInMonth(Integer month, Integer year) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		return yearMonthObject.lengthOfMonth();
	}

	public static Date dbDateFormat(Date date) {
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
