package com.proverbio.android.spring;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * @author Juan Pablo Proverbio
 */
public class TodoViewPagerAdapter extends FragmentPagerAdapter
{
    private final Context context;

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
                TodoListFragment todoListFragment = new TodoListFragment();
                return todoListFragment;

            case 1:
                todoListFragment = new TodoListFragment();
                return todoListFragment;

            default:
                return new ReportFragment();
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
