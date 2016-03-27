package com.proverbio.android.spring;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import com.proverbio.android.spring.util.StringConstants;
import com.proverbio.android.spring.util.TimeManager;

import java.util.Date;


/**
 * @author Juan Pablo Proverbio
 */
public class TodoViewPagerAdapter extends FragmentPagerAdapter
{
    private static final String TAG = TodoViewPagerAdapter.class.getSimpleName();

    private final Context context;

    private TodoListFragment todayTodoListFragment;

    private TodoListFragment weeklyTodoListFragment;

    private ReportsFragment todoReportsFragment;

    public TodoViewPagerAdapter(Context context, FragmentManager fragmentManager)
    {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position)
        {
            case 0:
                if (todayTodoListFragment == null)
                {
                    todayTodoListFragment = new TodoListFragment();

                    Bundle params = new Bundle();
                    Pair<Date, Date> today = TimeManager.getTodayDatePair();
                    params.putSerializable(StringConstants.FROM_DATE_KEY, today.first);
                    params.putSerializable(StringConstants.TO_DATE_KEY, today.second);

                    todayTodoListFragment.setArguments(params);
                }

                return todayTodoListFragment;

            case 1:
                if (weeklyTodoListFragment == null)
                {
                    weeklyTodoListFragment = new TodoListFragment();

                    Bundle params = new Bundle();
                    Pair<Date, Date> today = TimeManager.getTodayWeekPair();
                    params.putSerializable(StringConstants.FROM_DATE_KEY, today.first);
                    params.putSerializable(StringConstants.TO_DATE_KEY, today.second);

                    weeklyTodoListFragment.setArguments(params);
                }

                return weeklyTodoListFragment;

            case 2:
                if ( todoReportsFragment == null)
                {
                    todoReportsFragment = new ReportsFragment();
                }

                return todoReportsFragment;

            default:
                throw new UnsupportedOperationException("This case is not implemented. Add case for: " + position);
        }
    }

    @Override
    public int getCount()
    {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return context.getString(R.string.today_label);
            case 1:
                return context.getString(R.string.week_label);
            case 2:
                return context.getString(R.string.reports_label);
        }
        return null;
    }
}
