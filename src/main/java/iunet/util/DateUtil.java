package iunet.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {

	// 现在时间
	public static Date now() {
		return new Date();
	}
	
	public static Timestamp Timestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static String nowStr() {
		return formatDateString(now(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String nowStr(Long date) {
		return formatDateString(new Date(date), "yyyy-MM-dd HH:mm:ss");
	}

	// 转换成日期
	public static Date parseDate(Object objDate) {
		if (objDate.getClass() == Date.class
				|| objDate.getClass() == Timestamp.class)
			return (java.util.Date) objDate;

		Date date = new Date();
		String strTime = String.valueOf(objDate).trim();
		if (strTime == null || strTime.length() == 0) {
			return date;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					strTime.length() > 10 ? "yyyy-MM-dd HH:mm:ss"
							: (strTime.length() > 8 ?"yyyy-MM-dd":"yyyyMMdd"));
			date = dateFormat.parse(strTime);
		} catch (Exception ex) {
		}
		return date;
	}
	// 
	/**是否是合法的日期格式
	 * @param str
	 * @return
	 */
	public static Boolean isDateStr(String str) {
		Boolean isDate = false;
		String reg = "(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29)))";
		Pattern p = Pattern.compile(reg);
		str=str.length()==8?str.substring(0,4)+"-"+str.substring(4, 6)+"-"+str.substring(6,8):str;
		str = str.replace(".", "-").replace("/", "-").replace(" ", "-");
		isDate = p.matcher(str).matches();
		return isDate;
	}
	
	/**日期相距时长
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static TimeSpan getTimeSpan(Date startTime, Date endTime) {
		try {
			Calendar startData = Calendar.getInstance();
			Calendar endData = Calendar.getInstance();
			startData.setTime(startTime);
			endData.setTime(endTime);
			//endData.set(2010, 1, 2, 16, 00, 00);
			long s = startData.getTime().getTime();
			long e = endData.getTime().getTime();
			return new TimeSpan(e - s);
		} catch (Exception ex) {
			return new TimeSpan(0);
		}
	}

	/**日期相加减,amount可以为负数
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}
	
	/**日期加减小时,amount可以为负数
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}	
	/**日期加减分钟
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}
	
	/**日期加减秒
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	// 日期相加减,amount可以为负数
	public static Date add(Date date, int type, int amount) {
		Calendar _date = Calendar.getInstance();
		_date.setTime(date);
		_date.add(type, amount);
		return _date.getTime();
	}

	public static long frameStringToLong(String frames) {
		return frameStringToLong(frames, 25);
	}

	
	/**转换时间帧
	 * @param frames
	 * @param frameRate
	 * @return
	 */
	public static long frameStringToLong(String frames, int frameRate) {
		if (StringUtil.isNullOrEmpty(frames))
			return 0;
		
		if(frames.indexOf(":")<0)
			return Long.parseLong(frames);
		
		if(frames.length()<8)
			return 0;
		
		String[] aTime = frames.split(":");
		int hour, minute, second, frame;
		hour = Integer.parseInt(aTime[0]);
		minute = Integer.parseInt(aTime[1]);
		second = Integer.parseInt(aTime[2]);		
		frame = aTime.length>3?Integer.parseInt(aTime[3]):0;
		
		long result = (hour * 3600 + minute * 60 + second) * frameRate + frame;

		return result;
	}

	public static String frameLongToString(long frames) {
		return frameLongToString(frames, 25);
	}

	
	/**转换时间帧
	 * @param frames
	 * @param frameRate
	 * @return
	 */
	public static String frameLongToString(long frames, int frameRate) {
		String m = String.valueOf(frames / (3600 * frameRate));
		m = StringUtil.padLeft(m, '0', 2);

		String n = String.valueOf((frames / (60 * frameRate)) % 60);
		n = StringUtil.padLeft(n, '0', 2);

		String x = String.valueOf((frames / frameRate) % 60);
		x = StringUtil.padLeft(x, '0', 2);

		String y = String.valueOf(frames % frameRate);
		y = StringUtil.padLeft(y, '0', frameRate > 30 ? 3 : 2);

		String result = m + ":" + n + ":" + x + ":" + y;
		return result;
	}

	public static String formatDateString(Date timeDate, String format) {
		
		if(timeDate==null)
			return "";
		
		DateFormat df = new SimpleDateFormat(format);
		return df.format(timeDate);
	}
	
	/**取得一周某天的名称
	 * @param week
	 * @return
	 */
	public static String getNameOfWeek(int week) {
		switch (week) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 0:
			return "星期日";
		}
		return null;
	}
}