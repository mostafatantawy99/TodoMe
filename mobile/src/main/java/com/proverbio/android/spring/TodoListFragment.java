package com.proverbio.android.spring;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.proverbio.android.spring.base.AbstractRecyclerFragment;
import com.proverbio.android.spring.context.GenericDao;
import com.proverbio.android.spring.util.StringConstants;

import java.util.Date;

import io.realm.RealmResults;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoListFragment extends AbstractRecyclerFragment<TodoModel, TodoViewHolder, TodoListAdapter>
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public TodoListAdapter onCreateAdapter()
    {
        RealmResults<TodoModel> results = GenericDao.list(TodoModel.class);

        //Dummy data -  in real life this code would only exist on test cases
        if (results.isEmpty())
        {
            for (int i = 0; i < 50; i++)
            {
                TodoModel todoModel = new TodoModel();
                todoModel.setId(i);
                todoModel.setSummary("Get bathroom window replaced of room " + i);
                todoModel.setDescription("A new model came up and it's in special. See www.trademe.co.nz");
                todoModel.setDueDate(new Date());

                if (i > 20 && i < 35)
                {
                    todoModel.setStatus(TodoModel.Status.IN_PROGRESS.toString());
                }
                else if (i >= 40)
                {
                    todoModel.setStatus(TodoModel.Status.COMPLETED.toString());
                }

                GenericDao.save(todoModel);
            }

            results = GenericDao.list(TodoModel.class);
        }

        return new TodoListAdapter(getContext(), this, results);
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
            case R.id.todo:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(TodoModel.Status.PENDING, false);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.PENDING, true);
                }
                return true;

            case R.id.inprogress:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(TodoModel.Status.IN_PROGRESS, false);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.IN_PROGRESS, true);
                }
                return true;

            case R.id.completed:
                if (item.isChecked())
                {
                    item.setChecked(false);
                    getRecyclerAdapter().filter(TodoModel.Status.COMPLETED, false);
                }
                else
                {
                    item.setChecked(true);
                    getRecyclerAdapter().filter(TodoModel.Status.COMPLETED, true);
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
        getRecyclerAdapter().setItems(GenericDao.list(TodoModel.class));
        getSwipeRefreshLayout().setRefreshing(false);
    }
}
