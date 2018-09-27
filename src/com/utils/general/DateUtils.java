package com.utils.general;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 时间工具自定义标签函数用
 * @author fangll
 *
 */
public class DateUtils {  
	
	private static Log logger=LogFactory.getLog(DateUtils.class);		

	/**
	 * getTime
	 * @return
	 */
	public static Date getTime(){
		Calendar calendar=Calendar.getInstance();
		return calendar.getTime();
	}	

	/**
	 * legal holiday  such as lundar 0101-0107  solar 0501-0507,1001-1007
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean isHoliday(Date date) throws Exception{		
		String str=DateUtils.formatDate(date, "MMdd");
		GregorianCalendar cal = DateUtils.dateConvCalendar(date);
		String lunarStr=LunarCalendar.solarToLundar(
				cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,
				cal.get(Calendar.DATE));	
		logger.debug("\r\n\t "+cal.get(Calendar.YEAR)+(cal.get(Calendar.MONTH)+1)+
				cal.get(Calendar.DATE));
		logger.debug(lunarStr);			
		lunarStr=StringUtils.substring(lunarStr,4);
		ArrayList<String> chunjie=new ArrayList<String>();
		ArrayList<String> list=new ArrayList<String>();
		list.add("0101");list.add("0102");list.add("0103");    //元旦1-3
		for (int i = 1; i <=7; i++) {
			list.add("050"+i);           //五一
			list.add("100"+i);           //十一
			chunjie.add("010"+i);        //农历春节   
		}			
		if(list.indexOf(str)!=-1 ||chunjie.indexOf(lunarStr)!=-1){
			return true;
		}else{
			return false;
		}		
	}
	
	/**
	 * get day of week such as (1,2...7)
	 * @param date
	 * @return
	 */
	public static int dayOfWeek(Date date){		
		Calendar aCalendar=Calendar.getInstance();
		aCalendar.setTime(date);
		int x=aCalendar.get(Calendar.DAY_OF_WEEK);
		switch (x) {
			case Calendar.MONDAY:	x=1;break;
			case Calendar.TUESDAY:	x=2;break;
			case Calendar.WEDNESDAY:x=3;break;
			case Calendar.THURSDAY:	x=4;break;
			case Calendar.FRIDAY:	x=5;break;
			case Calendar.SATURDAY:	x=6;break;		
			case Calendar.SUNDAY:	x=7;break;
		}
		return x;	
	}
	
	/**
	 * 获取一年中的第几周
	 * 作者：池文杉
	 * @param date
	 * @return
	 */
	public static int weekOfYear(Date date){
		Calendar aCalendar=Calendar.getInstance();
		aCalendar.setTime(date);
		return aCalendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * get all date(Date) list between begin date and end date
	 * @param beginDate
	 * @param endDate
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 */
	public static ArrayList getDateList(Object beginDate, Object endDate,
			String dateFormat) throws Exception {
		ArrayList list = new ArrayList();
		int count = DateUtils.getDiffDays(beginDate, endDate, dateFormat);		
		Date date;
		if (count > 0) {
			date = DateUtils.objectConvDate(beginDate, dateFormat);
		} else {
			date = DateUtils.objectConvDate(endDate, dateFormat);
		}
		for (int i = 0; i < count + 1; i++) {
			list.add(date);
			logger.debug(DateUtils.formatDate(date, dateFormat));
			date = DateUtils.addDay(date, 1);
		}
		return list;
	}
	

	public static Date addMinute(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.MINUTE, num);
		return cal.getTime();
	}	
	
	public static Date addHour(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.HOUR, num);
		return cal.getTime();
	}
	
	public static Date addDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, num);
		return cal.getTime();
	}
	
	public static Date addMonth(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.MONTH, num);
		return cal.getTime();
	}	
	
	public static Date addYear(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.YEAR, num);
		return cal.getTime();
	}		
	/**
	 * object conver to date 
	 * @param date           can be String,Date,GregorianCalendar
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date objectConvDate(Object date,String format){
		Date dateA = new Date();
		if(date instanceof String){
			SimpleDateFormat sdf = new SimpleDateFormat(format);	
			try{
				dateA = sdf.parse((String) date);		
			}catch(Exception ex){
				ex.printStackTrace();
			}
    	}else if(date instanceof Date){
    		dateA=(Date)date;
		}else if(date instanceof GregorianCalendar){
			dateA=((GregorianCalendar)date).getTime();    	
		}else{
    		throw new java.lang.IllegalArgumentException("无效的参数");
    	}					
		return dateA;
	}
    /**
	 * java.util.Date Convert java.util.GregorianCalendar
	 * @param date java.util.Date
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar dateConvCalendar(Date date) {
		GregorianCalendar cal = new GregorianCalendar(
				date.getYear() + 1900,date.getMonth(), date.getDate(),
				date.getHours(),date.getMinutes(), date.getSeconds() );
		return cal;
	}
	
	/**
	 * get today before or after day
	 * @param count >0 after day,<0 berfor day
	 * @return
	 */
	public static GregorianCalendar getCurAfterDayCal(int count) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, count);
		return cal;
	}	
	  /**
     *  format Calendar
     * @param cal             GregorianCalendar,Date           
     * @param dateFormat      日期格式:缺省为E yyyy年MM月dd日常用格式yyyyMMdd HH:mm:ss
     * @return
	 * @throws Exception 
     */
    public static String formatDate(Object date, String dateFormat){
    	if(date==null || date.toString().equals("")) return "";
    	GregorianCalendar cal=DateUtils.dateConvCalendar(DateUtils.objectConvDate(date, dateFormat));    	
		String str = dateFormat == null ? "E yyyy年MM月dd日" : dateFormat;
		SimpleDateFormat format = new SimpleDateFormat(str);
		String curdate = format.format(cal.getTime());		
		return curdate;
	}
    
    /**
	 * get day between date1 and date2
	 * @param beginDate    can be String,Date,GregorianCalendar type
	 * @param endDate      can be String,Date,GregorianCalendar type
	 * @param format       if String must be set date format
	 * @return
	 * @throws Exception
	 */
    public static int getDiffDays(Object beginDate ,
    		Object endDate,String format) throws Exception {
    	Object[] objs=new Object[2];
    	objs[0]=beginDate;
    	objs[1]=endDate;
    	Date[] tmpDate=new Date[2];    	
    	for (int i = 0; i < objs.length; i++) {
    		tmpDate[i]=DateUtils.objectConvDate(objs[i], format);
    		logger.debug(tmpDate[i]);
		}    	
		long lBeginTime = tmpDate[0].getTime();
		long lEndTime = tmpDate[1].getTime();
		int iDay = (int) ((lEndTime - lBeginTime) / 86400000);
		return iDay;
	}
    
    /**
     * 
     * 功能描述：
     *
     * @author 池文杉
     * <p>创建日期 ：May 30, 2011 3:42:59 PM</p>
     *
     * @param dateStr
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static Date parseDate(String dateStr){
        Date date = null;
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        String tmpStr = dateStr.replaceAll("-", "");
        tmpStr = tmpStr.replaceAll("/", "");
        tmpStr = tmpStr.replaceAll(" ", "");
        String format = "";
        if(StringUtils.isBlank(tmpStr)){
            return null;
        }
        
        if(tmpStr.length()==4){
            format = "yyyy";
        } else if(tmpStr.length()==6){
            format = "yyyyMM";
        } else if(tmpStr.length()==8){
            format = "yyyyMMdd";
        } else if(tmpStr.length()==14){
            format = "yyyyMMddHHmmss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try{
            return formatter.parse(tmpStr);
        } catch(ParseException err){
            err.printStackTrace();
            return null;
        }
        
    }
    
    /**
	 * 功能描述：根据日期字符串创建的Timestamp对象,使用的日期格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @author 池文杉
	 * 
	 * @param dateStr 日期字符串
	 * @return Timestamp对象。
	 */
	public static Timestamp parseTimestamp(String dateStr) {
		return parseTimestamp(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 功能描述：根据指定的日期格式和日期字符串创建的Timestamp对象
	 * 
	 * @author 池文杉
	 * 
	 * @param dateStr 日期字符串
	 * @pramm dateFormat 日期格式
	 * @return Timestamp对象。
	 */
	public static Timestamp parseTimestamp(String dateStr, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date date = sdf.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 
	 * 功能描述：获取毫秒级时间
	 * 
	 * @author 池文杉
	 *         <p>
	 *         创建日期 ：Sep 7, 2011 3:04:49 PM
	 *         </p>
	 * 
	 * @return
	 * 
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static Timestamp getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		return new Timestamp(calendar.getTime().getTime());
	}
	
	/**
	 * 功能描述：根据指定的日期格式和日期字符串创建的Date对象
	 * 
	 * @author 吴宇振 2012-2-27
	 * 
	 * @param dateStr 日期字符串
	 * @param dateFormat 日期格式
	 * @return
	 */
	public static Date parseDate(String dateStr, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 取得数据库时间，解析时间
	 */
	public static String getDateyyyymm(Timestamp t) {
		String str = t.toString();
		str = str.replace("-", "");
		String date = str.substring(0, 6);
		return date;
	}

	public static String getDateddmmmiss(Timestamp t) {
		String str = t.toString();
		str = str.replace("-", "").replace(":", "").replace(" ", "");
		String time = str.substring(6, 14);
		return time;
	}
	
	public static String getDateyyyymmddhhmiss(Timestamp t){
		String str = t.toString();
		str = str.replace("-", "").replace(":", "").replace(" ", "");
		String time = str.substring(0, 14);
		return time;
	}
	
	public static String getDateyyyymmddhhmiss(Long timestamp){
		Timestamp t = new Timestamp(timestamp);
		return getDateyyyymmddhhmiss(t);
	}
	/**
	 * 
	 * 功能描述：获取格式化后的当前时间
	 * @author池文杉
	 * @param formatter
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String getNow(String formatter) {
        SimpleDateFormat format = new SimpleDateFormat(StringUtils.defaultIfEmpty(formatter, "yyyy-MM-dd"));
        String curdate = format.format(getTime());
        return curdate;
    }
	
	 /**
     * 日期运算
     * @param days 和当前日期的差值（单位:天）
     * @param pattern
     * @return
     */
    public static String getDateString(int days,String pattern){
        DateFormat dfmt = new SimpleDateFormat(pattern);
        long days2=(long) days;
        return dfmt.format(new java.util.Date((new Date()).getTime()  +  1000*60*60*24*days2));
    }
    
    /**
	 * 
	 * 功能描述：获取指定区间内的所有时间字符串，以月为单位
	 * @author chenyn3
	 * @createDate 2015-2-28
	 * @param beginDate
	 * @param endDate
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static List<String> getMonthStrList(String beginDate, String endDate, String dateFormat) throws Exception {
		List<String> list = new ArrayList<String>();
		int count = DateUtils.getDateIntervalMonth(beginDate, endDate, dateFormat);
		Date date;
		if (count > 0) {
			date = DateUtils.objectConvDate(beginDate, dateFormat);
		} else {
			date = DateUtils.objectConvDate(endDate, dateFormat);
		}
		for (int i = 0; i < count + 1; i++) {
			list.add(DateUtils.formatDate(date, dateFormat));
			date = DateUtils.addMonth(date, 1);
		}
		return list;
	}
	
	/**
	 * 
	 * 功能描述：获取两个日期间隔月数（可负数）
	 * @author 池文杉
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param dateFormat 日期格式 默认为"yyyy-MM-dd"
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static int getDateIntervalMonth(String startDate, String endDate,String dateFormat) {
		int mon = 0;
		Date d1 = null;
		Date d2 = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat(StringUtils.defaultIfEmpty(dateFormat, "yyyy-MM-dd"));
		try {
			d1 = dateFormatter.parse(startDate);
			d2 = dateFormatter.parse(endDate);
		} catch (ParseException e) {
			//e.printStackTrace();
			return mon;
		}
		mon = new Integer(((d2.getYear() - d1.getYear()) * 12 + (d2.getMonth() - d1.getMonth())));
		return mon;
	}
	
	/**
	 * 
	 * 功能描述：获取两个日期间隔秒数
	 * @author 池文杉
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDateIntervalSeconds(Date startDate, Date endDate) {
		long timeDelta=(endDate.getTime()-startDate.getTime())/1000;//单位是秒  
		int secondsDelta=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);  
		return secondsDelta;  
	}
	
	/**
	 * 
	 * 功能描述：获取当前时间往前/后N月的所有时间字符串，以月为单位
	 * @author chenyn3
	 * @createDate 2015-2-28
	 * @param endDate 当前时间
	 * @param dateFormat 日期格式
	 * @param months 负数：往前 正数：往后
	 * @return
	 * @throws Exception
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static List<String> getMonthStrList(String endDate, String dateFormat,int months) throws Exception {
		List<String> list = new ArrayList<String>();
		Date date = DateUtils.objectConvDate(endDate, dateFormat);
		int count = months>0?months:-months;
		for (int i = 0; i < count + 1; i++) {
			list.add(DateUtils.formatDate(date, dateFormat));
			if (months > 0) {
				date = DateUtils.addMonth(date, 1);
			}else{
				date = DateUtils.addMonth(date, -1);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 功能描述：获取指定区间内的所有时间字符串，以天为单位
	 * @author 池文杉
	 * @param beginDate
	 * @param endDate
	 * @param dateFormat yyyyMMdd
	 * @return
	 * @throws Exception
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static List<String> getDateStrList(String beginDate, String endDate, String dateFormat) throws Exception {
		List<String> list = new ArrayList<String>();
		int count = DateUtils.getDiffDays(beginDate, endDate, dateFormat);
		Date date;
		if (count > 0) {
			date = DateUtils.objectConvDate(beginDate, dateFormat);
		} else {
			date = DateUtils.objectConvDate(endDate, dateFormat);
		}
		for (int i = 0; i < count + 1; i++) {
			list.add(DateUtils.formatDate(date, dateFormat));
			date = DateUtils.addDay(date, 1);
		}
		return list;
	}
	
	/**
	 * 
	 * 功能描述：获取当前时间往前/后N天的所有时间字符串
	 * @author 池文杉
	 * @param endDate 当前时间
	 * @param dateFormat 日期格式
	 * @param days 负数：往前 正数：往后
	 * @return
	 * @throws Exception
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static List<String> getDateStrList(String endDate, String dateFormat,int days) throws Exception {
		List<String> list = new ArrayList<String>();
		Date date = DateUtils.objectConvDate(endDate, dateFormat);
		int count = days>0?days:-days;
		for (int i = 0; i < count + 1; i++) {
			list.add(DateUtils.formatDate(date, dateFormat));
			if (days > 0) {
				date = DateUtils.addDay(date, 1);
			}else{
				date = DateUtils.addDay(date, -1);
			}
		}
		return list;
	}
	
	/**
     * 
     * 功能描述：获取格式化的上N个月份；
     *
     * @author 池文杉
     * <p>创建日期 ：Jul 6, 2012 2:48:23 PM</p>
     *
     * @param formatter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static String getLastMonth(String formatter,int i){
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, i);
        SimpleDateFormat format = new SimpleDateFormat(StringUtils.defaultIfEmpty(formatter, "yyyy-MM-dd"));
        String curdate = format.format(cal.getTime());
        return curdate;
    }
    public static List<String> getMonthBetween(String minDate, String maxDate,String format) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
		 result.add(sdf.format(curr.getTime()));
		 curr.add(Calendar.MONTH, 1);
		}

		return result;
	}
    /**
     * 获取月份最大天数
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDayByYearMonth(int year,int month) {
		int maxDay = 0;
		int day = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1,day);
        maxDay = calendar.getActualMaximum(Calendar.DATE);
		return maxDay;
	}
    
    /**
     * 
     * 功能描述：取当天的最后一刻（23:59:59）
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date){
    	return parseDate(formatDate(date, "yyyy-MM-dd")+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 
     * 功能描述：获取格式化的上N个月份；
     * @param dateStr
     * @param formatter
     * @param i
     * @return
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    public static String getLastMonth(String dateStr,String formatter,int i){
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(dateStr, formatter));
        cal.add(cal.MONTH, i);
        SimpleDateFormat format = new SimpleDateFormat(StringUtils.defaultIfEmpty(formatter, formatter));
        String curdate = format.format(cal.getTime());
        return curdate;
    }
    
    /**
     * 
     * 功能描述：取年份
     * @param date
     * @return
     */
    public static int getYear(Date date) {
		Calendar cl = getCalendar(date);
		return cl.get(Calendar.YEAR);
	}

    public static int getHour(Date date) {
        Calendar cl = getCalendar(date);
        return cl.get(Calendar.HOUR_OF_DAY);
    }
    
    public static Calendar getCalendar(Date date) {
		Calendar cl = Calendar.getInstance();
		if (date != null)
			cl.setTime(date);
		return cl;
	}


}
