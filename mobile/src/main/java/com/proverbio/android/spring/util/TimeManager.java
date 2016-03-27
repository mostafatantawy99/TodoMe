package com.proverbio.android.spring.util;

import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Juan Pablo Proverbio
 */
public class TimeManager
{
    public static final String TAG = TimeManager.class.getSimpleName();

    public static final String HUMAN_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "H:mm a";

    private TimeManager()
    {
        super();
    }

    public static String getTimeFormat(Date date)
    {
        return new SimpleDateFormat(TIME_FORMAT).format(date);
    }

    public static String getHumanFormat(Date date)
    {
        return new SimpleDateFormat(HUMAN_FORMAT).format(date);
    }

    public static Pair<Date, Date> getTodayDatePair()
    {
        Calendar fromDueDate = Calendar.getInstance();
        fromDueDate.set(fromDueDate.get(Calendar.YEAR), fromDueDate.get(Calendar.MONTH), fromDueDate.get(Calendar.DATE), 0, 0, 0);

        Calendar toDueDate = Calendar.getInstance();
        toDueDate.set(toDueDate.get(Calendar.YEAR), toDueDate.get(Calendar.MONTH), toDueDate.get(Calendar.DATE), 24, 0, 0);

        return new Pair<>(fromDueDate.getTime(), toDueDate.getTime());
    }

    public static Pair<Date, Date> getTodayWeekPair()
    {
        Calendar fromDueDate = Calendar.getInstance();
        fromDueDate.setTime(new Date());
        fromDueDate.set(Calendar.DAY_OF_MONTH, fromDueDate.get(Calendar.DAY_OF_MONTH) - 6);
        fromDueDate.getTime();

        Calendar toDueDate = Calendar.getInstance();
        toDueDate.set(toDueDate.get(Calendar.YEAR), toDueDate.get(Calendar.MONTH), toDueDate.get(Calendar.DATE), 24, 0, 0);

        return new Pair<>(fromDueDate.getTime(), toDueDate.getTime());
    }

}
