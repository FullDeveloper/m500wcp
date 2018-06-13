package com.wcp.m500wcp.util;


import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with Hospital(1)
 * User : 周润斌
 * Date : 2016/12/15
 * Time : 16:47
 * Description :
 */
public class DateUtils {

    public static final String FormatDayToChinaTime24H = "yyyy年MM月dd日";
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATETIME_FORMAT_X = "yyyy/MM/dd HH:mm:ss";
    public static String DATE = "yyyy";
    public static String MONTH = "MM";
    public static String DAY = "dd";
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIME_FORMAT = "HH:mm:ss";


    public static int calLastedTime(Date preDate,Date nowDate) {
        long a = nowDate.getTime();
        long b = preDate.getTime();
        int c = (int)((a - b) / 1000);
        return c;
    }

    /*

    获取当前时间之前或之后几分钟 minute

    */
    public static String getTimeByMinute(int minute,String date) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        try {
            Date date1 = simpleDateFormat.parse(date);
            calendar.setTime(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MINUTE, minute);

        return simpleDateFormat.format(calendar.getTime());

    }


    /*

   获取当前时间之前或之后几分钟 minute

   */
    public static String getTimeByMinute(int minute,Date date) {
        String dateStr = DateUtils.formatDateByFormat(date,DateUtils.TIME_FORMAT);
        return getTimeByMinute(minute,dateStr);
    }


    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }



    /**
     * 返回年份
     *
     * @param date
     *            日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date
     *            日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     *
     * @param date
     *            日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String toDateAddDay(Date date,int day){
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);// 今天+1天
        Date tomorrow = c.getTime();
        return format.format(tomorrow);
    }

    /**
     * Parse date like "yyyy-MM-dd".
     */
    public static Date parseDate(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * Parse date like "yyyy-MM-dd HH:mm:ss".
     */
    public static Date parseDateDateTime(String d) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date parseDateDateTime(String d,String format) {
        try {
            return new SimpleDateFormat(format).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date parseDateOnlyTime(String d) {
        try {
            return new SimpleDateFormat(TIME_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date parseDateOnlyMinute(String d) {
        try {
            return new SimpleDateFormat("HH:mm").parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    public static int getWeekOfDate2(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }
    /**
     * Parse date like "yyyy-MM-dd HH:mm:ss".
     */
    public static Date parseDateTime(String d) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Parse date like "HH:mm:ss".
     */
    public static Date parseTime(String d) {
        try {
            return new SimpleDateFormat(TIME_FORMAT).parse(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 取得指定月份的最后一天
     *
     * @param strdate
     *            String
     * @return String
     */
    public String getMonthEnd(String strdate) {
        Date date = parseDate(getMonthBegin(strdate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 取得指定月份的第一天
     *
     * @param strdate
     *            String
     * @return String
     */
    public String getMonthBegin(String strdate) {
        Date date = parseDate(strdate);
        return formatDateByFormat(date, "yyyy-MM") + "-01";
    }

    /**
     * 以指定的格式来格式化日期
     *
     * @param date
     *            Date
     * @param format
     *            String
     * @return String
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 常用的格式化日期
     *
     * @param date
     *            Date
     * @return String
     */
    public String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * @根据当前日期计算n天后的日期
     * @return String
     */
    public Date afterNDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        Date destDay = c.getTime();
        return destDay;
    }


    /**
     * 两个日期间的天数
     */
    public static int getIntervalDays(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    public static boolean  verifyDate(String birthday){
        String [] array = birthday.split("-");
        if(null == array || array.length != 3 || array[0].length() !=4 || array[1].length() != 2 || array[2].length() != 2)
            return false;
        int curyear = DateUtils.getYear(new Date());
        try {
            int studyear = Integer.parseInt(array[0]);
            if(studyear <= 1900 || studyear > curyear)
                return false;
            String curDateStr = formatDateByFormat(new Date(), DATE_FORMAT);
            if(birthday.compareTo(curDateStr) >= 0)
                return false;
            return checkDate(birthday);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    private static boolean checkDate(String date) {
        // System.out.println(date);
        // 判断年月日的正则表达式，接受输入格式为2010-12-24，可接受平年闰年的日期
        String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherObj = pattern.matcher(date);
        return matcherObj.matches();
    }

//    public static List<Schedule> getAfterWeekDate(){
//        Date date = new Date();
//        String week = getWeekOfDate(date);
//        List<Schedule> scheduleList = new ArrayList<Schedule>();
//        Schedule s1 = new Schedule();
//        s1.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date,"yyyy-MM-dd")));
//        s1.setWeek(week);
//        scheduleList.add(s1);
//        Date date1 = getDateAfter(date,1);
//        week = getWeekOfDate(date1);
//        Schedule s2 = new Schedule();
//        s2.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date1,"yyyy-MM-dd")));
//        s2.setWeek(week);
//        scheduleList.add(s2);
//        Date date2 = getDateAfter(date,2);
//        week = getWeekOfDate(date2);
//        Schedule s3 = new Schedule();
//        s3.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date2,"yyyy-MM-dd")));
//        s3.setWeek(week);
//        scheduleList.add(s3);
//        Date date3 = getDateAfter(date,3);
//        week = getWeekOfDate(date3);
//        Schedule s4 = new Schedule();
//        s4.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date3,"yyyy-MM-dd")));
//        s4.setWeek(week);
//        scheduleList.add(s4);
//        Date date4 = getDateAfter(date,4);
//        week = getWeekOfDate(date4);
//        Schedule s5 = new Schedule();
//        s5.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date4,"yyyy-MM-dd")));
//        s5.setWeek(week);
//        scheduleList.add(s5);
//        Date date5 = getDateAfter(date,5);
//        week = getWeekOfDate(date5);
//        Schedule s6 = new Schedule();
//        s6.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date5,"yyyy-MM-dd")));
//        s6.setWeek(week);
//        scheduleList.add(s6);
//        Date date6 = getDateAfter(date,6);
//        week = getWeekOfDate(date6);
//        Schedule s7 = new Schedule();
//        s7.setScheduleDate(DateUtil.parseDate(DateUtil.formatDateByFormat(date6,"yyyy-MM-dd")));
//        s7.setWeek(week);
//        scheduleList.add(s7);
//        return scheduleList;
//    }
//



    public static List<Map> getAfterWeekDateNoToday(){
        Date date = new Date();
        Date dateT = getDateAfter(date,1);
        String week = getWeekOfDate(dateT);
        List<Map> scheduleList = new ArrayList<Map>();
        for(int i=0;i<7;i++){
            Map map = new HashMap();
            map.put("date",parseDate(formatDateByFormat(dateT,"yyyy-MM-dd")));
            map.put("week",week);
            scheduleList.add(map);
            dateT = getDateAfter(date,2+i);
            week = getWeekOfDate(dateT);
        }
        Map s1 = new HashMap();

        Date date1 = getDateAfter(date,2);
        week = getWeekOfDate(date1);
        Map s2 = new HashMap();
        s2.put("date",parseDate(formatDateByFormat(date1,"yyyy-MM-dd")));
        s2.put("week",week);
        scheduleList.add(s2);
        Date date2 = getDateAfter(date,3);
        week = getWeekOfDate(date2);
        Map s3 = new HashMap();
        s3.put("date",parseDate(formatDateByFormat(date2,"yyyy-MM-dd")));
        s3.put("week",week);
        scheduleList.add(s3);
        Date date3 = getDateAfter(date,4);
        week = getWeekOfDate(date3);
        Map s4 = new HashMap();
        s4.put("date",parseDate(formatDateByFormat(date3,"yyyy-MM-dd")));
        s4.put("week",week);
        scheduleList.add(s4);
        Date date4 = getDateAfter(date,5);
        week = getWeekOfDate(date4);
        Map s5 = new HashMap();
        s5.put("date",parseDate(formatDateByFormat(date4,"yyyy-MM-dd")));
        s5.put("week",week);
        scheduleList.add(s5);
        Date date5 = getDateAfter(date,6);
        week = getWeekOfDate(date5);
        Map s6 = new HashMap();
        s6.put("date",parseDate(formatDateByFormat(date5,"yyyy-MM-dd")));
        s6.put("week",week);
        scheduleList.add(s6);
        Date date6 = getDateAfter(date,7);
        week = getWeekOfDate(date6);
        Map s7 = new HashMap();
        s7.put("date",parseDate(formatDateByFormat(date6,"yyyy-MM-dd")));
        s7.put("week",week);
        scheduleList.add(s7);
        return scheduleList;
    }


    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前日期 所在周 周一和周日 日期   当月第一天及最后一天日期  及 当前第一天及最后一天日期
     * @param date
     * @return
     */
    public static Map getFirstAndEndDay(Date date){
        Map resultMap=new HashMap();
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //周
        cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
        String weekhand = simdf.format(cal.getTime());

        cal.set(Calendar.DATE, cal.get(cal.DATE) + 6);
        String weeklast = simdf.format(cal.getTime());
        //月
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = simdf.format(cal.getTime());

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = simdf.format(cal.getTime());
        //年
        int year =cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.YEAR, year);
        Date currYearFirst = cal.getTime();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.roll(Calendar.DAY_OF_YEAR,-1);
        Date lastYearFirst = cal.getTime();

        resultMap.put("monday",weekhand);
        resultMap.put("friday",weeklast);
        resultMap.put("monthOfFirst",firstday);
        resultMap.put("monthOfLast",lastday);
        resultMap.put("first", simdf.format(currYearFirst));
        resultMap.put("last", simdf.format(lastYearFirst));
        return resultMap;
    }

    public static void main(String [] args) {
        List<Map> maps = getAfterWeekDateNoToday();
        int i = differentDays(new Date(),DateUtils.parseDate("2018-04-22"));
        System.out.println(i);
    }

}

