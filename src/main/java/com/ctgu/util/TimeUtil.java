package com.ctgu.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public final class TimeUtil
{
	public static String startDateSuffix = " 00:00:00";

	public static String endDateSuffix = " 23:59:59";

	public static Timestamp currentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp stringToTimestamp(String str)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(str);
			str = dateToString(date);
		}
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try
		{
			ts = Timestamp.valueOf(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ts;
	}

	public static Timestamp stringToTimestamp(String str, String pattern)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(str);
			str = dateToString(date);
		}
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try
		{
			ts = Timestamp.valueOf(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ts;
	}

	public static String timestampToString(Timestamp ts, DateFormat sdf)
	{
		if (ts == null)
			return "";
		String result = "";
		try
		{
			result = sdf.format(ts);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static String dateToString(Date date)
	{
		String dateStr = "";
		DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			dateStr = sdf2.format(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dateStr;
	}

	public static Timestamp timestampToAddDay(Timestamp ts, Integer day)
	{
		if (ts == null)
			return null;
		Date d = new Date(ts.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(5, day.intValue());
		Date sDate = c.getTime();
		Timestamp resultTs = new Timestamp(sDate.getTime());
		return resultTs;
	}

	public static Long countYearTimestamp(Timestamp ts1, Timestamp ts2)
	{
		long year = ChronoUnit.YEARS.between(ts2.toLocalDateTime(), ts1.toLocalDateTime());
		return Long.valueOf(year);
	}

	public static Long countMonthTimestamp(Timestamp ts1, Timestamp ts2)
	{
		long totalMonth = ChronoUnit.MONTHS.between(ts2.toLocalDateTime(), ts1.toLocalDateTime());
		long month = totalMonth % 12L;
		return Long.valueOf(month);
	}
}
