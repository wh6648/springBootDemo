package com.rijia.workPlatform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	private static Map<String, DateFormat> formaters = null;

	private static DateFormat getDateFormater(String format) {
		if (formaters == null) {
			formaters = new HashMap<String, DateFormat>();
		}
		DateFormat formater = formaters.get(format);
		if (formater == null) {
			formater = new SimpleDateFormat(format);
			formaters.put(format, formater);
		}
		return formater;
	}

	public static Date parse(String strDate, String format) {
		try {
			return getDateFormater(format).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		return getDateFormater(format).format(date);
	}

	public static String format(String strDate, String srcFormat, String destFormat) {
		Date date = parse(strDate, srcFormat);
		return format(date, destFormat);
	}

	public static java.sql.Date createSqlDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static Date createSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static Date toStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date toEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static boolean compareDate(Date date1, Date date2) {
		int n = date1.compareTo(date2);
		if (n < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	/**
	 * 计算两个时间相差的月份
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static double calculateMonthIn(Date start, Date end) {
		double result = 0.0d;
		if (start.after(end)) {
			return 0;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		// 如果计息时间不是发标第二天，天数需要修改
		int day = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
		// 月间隔计算
		result = year * 12 + month - 1;
		// 月精算
		if (day > 0) {
			// 如：结束月2014.10.11 开始月2014.8.8 间隔2.1个月
			result = NumberUtils.add(result, 1);
			double resultMonthIn = NumberUtils.div(day, 30);
			result = NumberUtils.add(result, resultMonthIn);
		} else {
			// 如：结束月2014.10.8 开始月2014.8.11 间隔1.9个月
			double dayOfMonth = NumberUtils.add(30, day);
			double resultMonthIn = NumberUtils.div(dayOfMonth, 30);
			result = NumberUtils.add(result, resultMonthIn);
		}
		return result;
	}

	/**
	 * 计算两个时间相差的月份，根据天数>=15天，四舍五入
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static double getMonth(Date start, Date end) {
		if (start == null) {
			start = new Date();
		}
		if (end == null) {
			end = new Date();
		}
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		int day = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);

		double result = 0.0d;
		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			result = year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			result = year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			result = year * 12 + month;
		} else {
			result = (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}

		if (day >= 15) {
			result = result + 1;
		} else if (day > 0 && day < 15) {
			result = result + 0.5;
		}
		return result;
	}

	/**
	 * 计算两个时间相差的年，
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static int dateDiffYear(Date start, Date end) {
		if (start == null) {
			start = new Date();
		}
		if (end == null) {
			end = new Date();
		}
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

		return year + 1;
	}

	/**
	 * 计算日期相差天数，小时，分
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static String dateDiffDay(String startTime, String endTime, String format) {
		String result = "";
		if (StringUtils.isEmpty(endTime)) {
			return "截至日期待定";
		}
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		// long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff % nd / nh;// 计算差多少小时
			long min = diff % nd % nh / nm;// 计算差多少分钟
			// long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			result = day + "天" + hour + "小时" + min + "分";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算日期相差秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @param date_format
	 * @return
	 */
	public static long dateDiffSec(Date startTime, Date endTime) {
		long ret = 0;
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			diff = endTime.getTime() - startTime.getTime();
			long min = diff % nd % nh / nm;// 计算差多少分钟
			long sec = diff % nd % nh % nm / ns;// 计算差多少秒
			ret = min * 60 + sec;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 计算日期相差小时
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static Long dateDiffDayOfHour(String startTime, String endTime, String format) {
		long result = 0;

		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		// long nm = 1000 * 60;// 一分钟的毫秒数
		// long ns = 1000;// 一秒钟的毫秒数
		long diff;
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long hour = diff % nd / nh;// 计算差多少小时
			result = hour;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date createDate(int year) {
		return createDate(year, 1);
	}

	public static Date createDate(int year, int month) {
		return createDate(year, month, 1);
	}

	public static Date createDate(int year, int month, int day) {
		return createDate(year, month, day, 0);
	}

	public static Date createDate(int year, int month, int day, int hour) {
		return createDate(year, month, day, hour, 0);
	}

	public static Date createDate(int year, int month, int day, int hour, int minute) {
		return createDate(year, month, day, hour, minute, 0);
	}

	public static Date createDate(int year, int month, int day, int hour, int minute, int second) {
		return createDate(year, month, day, hour, minute, second, 0);
	}

	public static Date createDate(int year, int month, int day, int hour, int minute, int second, int milliSecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, milliSecond);

		return calendar.getTime();
	}

	/**
	 * 取得当前月的开始时间
	 * 
	 * @return 当前月的开始时间
	 */
	public static Date getBeginDateOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 取得当前月的结束时间
	 * 
	 * @return 当前月的结束时间
	 */
	public static Date getEndDateOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	/**
	 * 取得下一个月的开始时间
	 * 
	 * @return 下一个月的开始时间
	 */
	public static Date getBeginDateOfNextMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 取得下一个月的结束时间
	 * 
	 * @return 下一个月的结束时间
	 */
	public static Date getEndDateOfNextMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	public static Date getBeginDateOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date getEndDateOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	public static List<String> getYearMonthByParam(int count) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat matter = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		list.add(matter.format(calendar.getTime()));
		for (int i = 1; i < count; i++) {
			calendar.add(Calendar.MONTH, -1);
			list.add(matter.format(calendar.getTime()));
		}
		Collections.reverse(list);
		return list;
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek() {

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, -day_of_week + 1);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek() {

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, -day_of_week + 7);
		return calendar.getTime();
	}

	public static boolean isOverEndTime(Date date) {

		Calendar loginTimeOutCal = Calendar.getInstance();
		loginTimeOutCal.setTime(DateUtils.toEndOfDay(date));

		Calendar newDayCal = Calendar.getInstance();
		newDayCal.setTime(new Date());

		return newDayCal.compareTo(loginTimeOutCal) > 0;
	}

	public static void main(String[] args) {
		// System.out.println(format(getBeginDateOfCurrentMonth(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		// System.out.println(format(getEndDateOfCurrentMonth(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		// System.out.println(format(getBeginDateOfNextMonth(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		// System.out.println(format(getEndDateOfNextMonth(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		// System.out.println(format(getBeginDateOfCurrentYear(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		// System.out.println(format(getEndDateOfCurrentYear(), "yyyy-MM-dd
		// HH:mm:ss SSS"));
		System.out.println(format(getFirstDayOfWeek(), "yyyy-MM-dd HH:mm:ss SSS"));
		System.out.println(format(getLastDayOfWeek(), "yyyy-MM-dd HH:mm:ss SSS"));
	}
}