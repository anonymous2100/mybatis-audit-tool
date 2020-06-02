package com.ctgu.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils
{
	/**
	 * 获取系统当前
	 * 
	 * @return String
	 */
	public static String currentTime()
	{
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}

	// 获取当天的开始时间
	public static Date getDayBegin()
	{
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取当天的结束时间
	public static Date getDayEnd()
	{
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 获取昨天的开始时间
	public static Date getBeginDayOfYesterday()
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取昨天的结束时间
	public static Date getEndDayOfYesterDay()
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取明天的开始时间
	public static Date getBeginDayOfTomorrow()
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// 获取明天的结束时间
	public static Date getEndDayOfTomorrow()
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// 获取本周的开始时间
	@SuppressWarnings("unused")
	public static Date getBeginDayOfWeek()
	{
		Date date = new Date();
		if (date == null)
		{
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1)
		{
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	// 获取本周的结束时间
	public static Date getEndDayOfWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);

		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	// 获取上周的开始时间
	@SuppressWarnings("unused")
	public static Date getBeginDayOfLastWeek()
	{
		Date date = new Date();
		if (date == null)
		{
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1)
		{
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek - 7);
		return getDayStartTime(cal.getTime());
	}

	// 获取上周的结束时间
	public static Date getEndDayOfLastWeek()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfLastWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取上月的开始时间
	public static Date getBeginDayOfLastMonth()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 2, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取上月的结束时间
	public static Date getEndDayOfLastMonth()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 2, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 2, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取本年的开始时间
	public static Date getBeginDayOfYear()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		return getDayStartTime(cal.getTime());
	}

	// 获取本年的结束时间
	public static java.util.Date getEndDayOfYear()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d)
	{
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d)
	{
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取今年是哪一年
	public static Integer getNowYear()
	{
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth()
	{
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}
	
	// 获取本周是哪一周
	public static int getNowWeek()
	{
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setFirstDayOfWeek(Calendar.MONDAY);
		gc.setTime(date);

		int weekOfYear = gc.get(GregorianCalendar.WEEK_OF_YEAR);

		return weekOfYear;
	}

	// 两个日期相减得到的天数
	public static int getDiffDays(Date beginDate, Date endDate)
	{
		if (beginDate == null || endDate == null)
		{
			throw new IllegalArgumentException("getDiffDays param is null!");
		}
		long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
		int days = new Long(diff).intValue();
		return days;
	}

	// 两个日期相减得到的毫秒数
	public static long dateDiff(Date beginDate, Date endDate)
	{
		long date1ms = beginDate.getTime();
		long date2ms = endDate.getTime();
		return date2ms - date1ms;
	}

	// 获取两个日期中的最大日期
	public static Date max(Date beginDate, Date endDate)
	{
		if (beginDate == null)
		{
			return endDate;
		}
		if (endDate == null)
		{
			return beginDate;
		}
		if (beginDate.after(endDate))
		{
			return beginDate;
		}
		return endDate;
	}

	// 获取两个日期中的最小日期
	public static Date min(Date beginDate, Date endDate)
	{
		if (beginDate == null)
		{
			return endDate;
		}
		if (endDate == null)
		{
			return beginDate;
		}
		if (beginDate.after(endDate))
		{
			return endDate;
		}
		return beginDate;
	}

	// 返回某月该季度的第一个月
	public static Date getFirstSeasonDate(Date date)
	{
		final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = SEASON[cal.get(Calendar.MONTH)];
		cal.set(Calendar.MONTH, sean * 3 - 3);
		return cal.getTime();
	}

	// 返回某个日期下几天的日期
	public static Date getNextDay(Date date, int i)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
		return cal.getTime();
	}

	// 返回某个日期前几天的日期
	public static Date getFrontDay(Date date, int i)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
		return cal.getTime();
	}

	// 获取某年某月到某年某月按天的切片日期集合(间隔天数的集合)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getTimeList(int beginYear, int beginMonth, int endYear, int endMonth, int k)
	{
		List list = new ArrayList();
		if (beginYear == endYear)
		{
			for (int j = beginMonth; j <= endMonth; j++)
			{
				list.add(getTimeList(beginYear, j, k));
			}
		}
		else
		{
			{
				for (int j = beginMonth; j < 12; j++)
				{
					list.add(getTimeList(beginYear, j, k));
				}
				for (int i = beginYear + 1; i < endYear; i++)
				{
					for (int j = 0; j < 12; j++)
					{
						list.add(getTimeList(i, j, k));
					}
				}
				for (int j = 0; j <= endMonth; j++)
				{
					list.add(getTimeList(endYear, j, k));
				}
			}
		}
		return list;
	}

	// 获取某年某月按天切片日期集合(某个月间隔多少天的日期集合)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getTimeList(int beginYear, int beginMonth, int k)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<String> list = new ArrayList();
		Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
		int max = begincal.getActualMaximum(Calendar.DATE);
		for (int i = 1; i < max; i = i + k)
		{
			// list.add(begincal.getTime());
			list.add(sdf.format(begincal.getTime()));

			begincal.add(Calendar.DATE, k);
		}
		begincal = new GregorianCalendar(beginYear, beginMonth, max);
		// list.add(begincal.getTime());
		list.add(sdf.format(begincal.getTime()));

		return list;
	}

	// public static void main(String[] args)
	// {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//// // 获取本周的开始时间
	//// Date beginDate = getBeginDayOfWeek();
	//// System.out.println(sdf.format(beginDate));
	//// // 获取本周的结束时间
	//// Date endDate = getEndDayOfWeek();
	//// System.out.println(sdf.format(endDate));
	// // List<String> list = getTimeList(2019, 1, 7);
	// // System.out.println(list);
	//
	//
	// Date date=getBeginDayOfYear();
	// System.out.println(sdf.format(date));
	//
	//
	// }

//	public static void main(String[] args)
//	{
//		System.out.println(getNowMonth());
//		System.out.println(getNowWeek());
//	}
	public static Date getWeekDay(Integer weekDay){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, weekDay);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();
		return date;
	}
	
	
	/**
	 * 根据周计算当前周第一天的日期 get start date of given week no of a year
	 * 
	 * @param year
	 * @param weekNo
	 * @return 出参返回形式 mm-dd
	 */
	public static String getStartDayOfWeekNo(int weekNo) {
		Calendar cal = getCalendarFormYear();
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
	}

	private static Calendar getCalendarFormYear() {
		Calendar cal = Calendar.getInstance();
		int year = Integer.valueOf(cal.get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);
		return cal;
	}

}
