package com.gonder.pregnancyhealthcare.util.util;
import java.util.Date;

import static com.gonder.pregnancyhealthcare.util.util.Constants.dayMilliSec;
import static com.gonder.pregnancyhealthcare.util.util.Constants.dayNumbers;
import static com.gonder.pregnancyhealthcare.util.util.Constants.ethiopicEpoch;
import static com.gonder.pregnancyhealthcare.util.util.Constants.hourMilliSec;
import static com.gonder.pregnancyhealthcare.util.util.Constants.minMilliSec;
import static com.gonder.pregnancyhealthcare.util.util.Constants.months;
import static com.gonder.pregnancyhealthcare.util.util.Constants.secMilliSec;
import static com.gonder.pregnancyhealthcare.util.util.Constants.unixEpoch;

public class Etc {

    public  int fixed;
    public  double moment;
    private String dayGeez;
    private String monthGeez;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    public Etc() {
        Date date1 = new Date(new Date().getTime());
        String s = date1.toString();
//        this.year = Integer.parseInt(LocalDateTime.now().toString().substring(0,4));
        this.year = Integer.parseInt(s.substring(s.length()-4,s.length()));
        this.month = 1;
        this.day = 1;
        this.hour = 0 ;
        this.minute = 0;
        this.second = 0;
        this.millisecond = 0;
        this.monthGeez = months[0];
        this.dayGeez = dayNumbers[0];
        this.fixed = fixedFromEthiopic(year,month,day);
        this.moment = _dateToEpoch(year,month,day,hour,minute,second,millisecond);
    }

    public Etc(int year,int month,int day,int hour,int minute,int second,int millisecond) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
        this.monthGeez = months[this.month - 1];
        this.dayGeez = dayNumbers[this.day - 1];
        this.fixed = fixedFromEthiopic(year,month,day);
        this.moment = _dateToEpoch(year,month,day,hour,minute,second,millisecond);
    }

//    public Etc() {
//
//    }

    public Etc fromMillisecondsSinceEpoch(long millisecondsSinceEpoch){
        moment = millisecondsSinceEpoch;
        fixed = fixedFromUnix(moment);
        return withValue(fixed,moment);
    }

    public int fixedFromUnix(double ms){
        return (unixEpoch + (int)(ms / 86400000));
    }

    public Etc withValue(int fixed,double moment){
        int year = (4 * (fixed - ethiopicEpoch) + 1463) / 1461;
        int month = (((fixed - fixedFromEthiopic(year, 1, 1)) / 30) + 1);
        int day = fixed + 1 - fixedFromEthiopic(year, month, 1);
        int hour = (int)(moment / hourMilliSec) % 24;
        int minute = (int)(moment / minMilliSec) % 60;
        int second = (int)(moment / secMilliSec) % 60;
        int millisecond = (int) ((double)moment % 1000);
        return new Etc(year,month,day,hour,minute,second,millisecond);
    }

    public static int fixedFromEthiopic(int year, int month, int day){
        return (ethiopicEpoch - 1) + (365 * (year - 1) )+ (year / 4) + (30 * (month - 1)) + day;
    }

    public static double _dateToEpoch(int year, int month, int date, int hour, int minute, int second, int millisecond) {
        return ((fixedFromEthiopic(year, month, date) - unixEpoch) * dayMilliSec) +
                (hour * hourMilliSec) +
                (minute * minMilliSec) +
                (second * secMilliSec) +
                millisecond;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public String getDayGeez() {return dayGeez;}

    public String getMonthGeez() {return monthGeez;}

//    TODO: test toEtc in adapters

}
