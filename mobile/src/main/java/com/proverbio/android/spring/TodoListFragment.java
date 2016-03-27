package com.proverbio.android.spring;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.proverbio.android.spring.base.AbstractRecyclerFragment;
import com.proverbio.android.spring.util.StringConstants;

import java.util.Date;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoListFragment extends AbstractRecyclerFragment<TodoModel, TodoViewHolder, TodoListAdapter>
{
    private static final String TAG = TodoListFragment.class.getSimpleName();

    private Date fromDueDate;

    private Date toDueDate;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if (getArguments() != null)
        {
            fromDueDate = (Date)getArguments().getSerializable(StringConstants.FROM_DATE_KEY);
            toDueDate = (Date)getArguments().getSerializable(StringConstants.TO_DATE_KEY);
        }

        super.onCreate( savedInstanceState );
    }

    @Override
    public TodoListAdapter onCreateAdapter()
    {
        return new TodoListAdapter(getContext(), this, fromDueDate, toDueDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.by_default:
                item.setChecked(!item.isChecked());
                getRecyclerAdapter().filter(null);
                return true;

            case R.id.todo:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(null);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.PENDING);
                }
                return true;

            case R.id.inprogress:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(null);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.IN_PROGRESS);
                }
                return true;

            case R.id.completed:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(null);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.COMPLETED);
                }
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onItemClick(int position, TodoModel model)
    {
        Intent intent = new Intent(getContext(), TodoComposeActivity.class);
        intent.putExtra(StringConstants.ITEM_KEY, model);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRefresh()
    {
        //Mocks going to server and grabbing fresh data
        //Only for demo purposes
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 1000);
    }
}
