package Utils;

import Elements.BrewSimulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static javaSimulation.Process.time;

public class Time {

    public static String weekDay () {
        String weekday = weekDay((int) time());
        return weekday;
    }

    public static String weekDay (int time) {
        int wd = (time+7) % 7;
        String weekday = "";
        int wdo = 0;
        wdo = wd;

        if (BrewSimulation.backwards) {
            wd = BrewSimulation.bDays[0];
        }

        switch (wd) {
            case 1: weekday = "Montag";
                break;
            case 2: weekday = "Dienstag";
                break;
            case 3: weekday = "Mittwoch";
                break;
            case 4: weekday = "Donnerstag";
                break;
            case 5: weekday = "Freitag";
                break;
            case 6: weekday = "Samstag";
                break;
            case 0: weekday = "Sonntag";
                break;
        }

        //Determine the weekdays based on the backward-Timeline, therefore take the difference between the startdate forward and backwards - as that one always has the same number. Thats the factor to add to the time to get the correct weekday backwards
        if (BrewSimulation.backwards) {
            int weekdayO = weeknrbackwards(weekday);
            weekday = weekdayBackwards(wdo, weekdayO - BrewSimulation.bDays[0]);
        }


        return weekday;
    }

    public static int weeknrbackwards (String oWeekday) {
        int wdbackwards= 0;

        switch (oWeekday) {
            case "Montag": wdbackwards = 0;
                break;
            case "Dienstag": wdbackwards = 6;
                break;
            case "Mittwoch": wdbackwards = 5;
                break;
            case "Donnerstag": wdbackwards = 4;
                break;
            case "Freitag": wdbackwards = 3;
                break;
            case "Samstag": wdbackwards = 2;
                break;
            case "Sonntag": wdbackwards = 1;
                break;
        }

        return wdbackwards;
    }

    public static String weekdayBackwards (int time, int factor) {
        String wdBackwards = "";
        int wd = (time+7+factor) % 7;
        switch (wd) {
            case 1: wdBackwards = "Sonntag";
                break;
            case 2: wdBackwards = "Samstag";
                break;
            case 3: wdBackwards = "Freitag";
                break;
            case 4: wdBackwards = "Donnerstag";
                break;
            case 5: wdBackwards = "Mittwoch";
                break;
            case 6: wdBackwards = "Dienstag";
                break;
            case 0: wdBackwards = "Montag";
                break;
        }
        return wdBackwards;
    }

    public static int[] parseDates(String[] dateStr) throws ParseException {
        int[] datenr = new int[dateStr.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        Date date = new Date(sdf.parse(dateStr[0]).getTime());
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.setFirstDayOfWeek(Calendar.MONDAY);
        startDate.setTime(date);
        datenr[0] = (startDate.get(Calendar.DAY_OF_WEEK) - (startDate.getFirstDayOfWeek() + 7) % 7 + 1);
        for (int i = 1; i < dateStr.length; i++) {
            long difference_in_time;
            Date ndate = new Date(sdf.parse(dateStr[i]).getTime());
            if (!BrewSimulation.backwards) {
                difference_in_time = ndate.getTime() - date.getTime();
            } else {
                difference_in_time = date.getTime() - ndate.getTime();
            }
            long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_in_time) % 365;
            datenr[i] = (int) difference_In_Days + datenr[0];
        }
        return datenr;
    }

    public static String returnDates(String startDate, int days) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        Date date = new Date(sdf.parse(startDate).getTime());
        Calendar newDate = GregorianCalendar.getInstance();
        newDate.setTime(date);
        newDate.setFirstDayOfWeek(Calendar.MONDAY);
        int startpoint = (newDate.get(Calendar.DAY_OF_WEEK) - (newDate.getFirstDayOfWeek() + 7) % 7 + 1);
        if (!BrewSimulation.backwards) {
            newDate.add(Calendar.DATE, (days-startpoint));
        }
        else {
            newDate.add(Calendar.DATE, -(days-startpoint));
        }
        SimpleDateFormat sdfcsv = new SimpleDateFormat("dd.MM.YYYY");
        String nextDate = sdfcsv.format(newDate.getTime());
        return nextDate;
    }

}
