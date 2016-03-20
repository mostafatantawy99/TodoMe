package com.proverbio.android.spring.util;

import java.text.SimpleDateFormat;
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

}
