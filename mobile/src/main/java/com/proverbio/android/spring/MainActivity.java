package com.proverbio.android.spring;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.proverbio.android.spring.base.AbstractActivity;
import com.proverbio.android.spring.util.StringConstants;

/**
 * @author Juan Pablo Proverbio
 * @since 1.0
 *
 * This is the Todo MainActivity. You will be able to create, view, update and filter TODO items.
 */
public class MainActivity extends AbstractActivity implements View.OnClickListener
{
    /**
     * A ViewPager where we place the adapter fragments and is connected to the TabLayout
     */
    private ViewPager viewPager;

    /**
     * The FragmentPagerAdapter used to manage the available Fragments for this ViewPager
     */
    private TodoViewPagerAdapter viewPagerAdapter;

    /**
     * The TabLayout used with TODAY | 7 DAYS | REPORTS
     */
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewPagerAdapter = new TodoViewPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the Todo App adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                //Show and Hide Fab button as needed
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

        //Get reference of TabLayout and setup with ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Set the listener Fab button should use
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
                    //start TodoComposeActivity for composing a TODO item
                    case 0:
                    case 1:
                        Intent intent = new Intent(this, TodoComposeActivity.class);
                        intent.putExtra(StringConstants.IS_COMPOSE_KEY, true);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
                break;
        }
    }
}
