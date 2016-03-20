package com.proverbio.android.spring.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.proverbio.android.spring.R;

/**
 * @author Juan Pablo Proverbio
 */
public abstract class AbstractActivity extends AppCompatActivity
{
    private AppBarLayout appBarLayout;
    
    private Toolbar toolbar;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);

        //Gets {@see Toolbar} instance from inflated layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            //Sets our Toolbar instance as our application's ActionBar
            setSupportActionBar(toolbar);
        }

        //Gets {@see FloatingActionButton} instance from inflated layout
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    protected abstract int getLayoutResource();

    public AppBarLayout getAppBarLayout()
    {
        return appBarLayout;
    }

    public Toolbar getToolbar()
    {
        return toolbar;
    }

    public FloatingActionButton getFloatingActionButton()
    {
        return floatingActionButton;
    }
}
