package com.proverbio.android.spring;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.proverbio.android.spring.base.AbstractActivity;
import com.proverbio.android.spring.util.StringConstants;

public class MainActivity extends AbstractActivity implements View.OnClickListener
{
    private ViewPager viewPager;
    private TodoViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new TodoViewPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                    case 1:
                        getFloatingActionButton().setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        getFloatingActionButton().setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getFloatingActionButton().setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fab:
                switch (viewPager.getCurrentItem())
                {
                    case 0:
                    case 1:
                        Intent intent = new Intent(this, TodoComposeActivity.class);
                        intent.putExtra(StringConstants.IS_COMPOSE_KEY, true);
                        startActivity(intent);
                        break;

                    default:
                        //DO nothing - This should not happen unless a developer add another feature from the fab button
                        break;
                }
                break;
        }
    }
}
