package com.proverbio.android.spring;

import android.content.Context;
import android.view.ViewGroup;

import com.proverbio.android.spring.base.AbstractRecyclerRealmAdapter;
import com.proverbio.android.spring.context.repository.TodoRepository;

import java.util.Date;

import io.realm.RealmResults;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoListAdapter extends AbstractRecyclerRealmAdapter<TodoModel, TodoViewHolder>
{
    /**
     * The TAG used for log messages from this class
     */
    public static final String TAG = TodoListAdapter.class.getSimpleName();

    private final TodoRepository todoRepository;

    private final Date fromDueDate;

    private final Date toDueDate;

    public TodoListAdapter(Context context,
                           OnRecyclerViewItemClick<TodoModel> itemClickListener,
                           Date fromDueDate,
                           Date toDueDate)
    {
        super(context, TodoModel.class, itemClickListener);
        this.fromDueDate = fromDueDate;
        this.toDueDate = toDueDate;
        this.todoRepository = new TodoRepository();
        setRealmResultsList(todoRepository.listOnlyTodo(fromDueDate, toDueDate));
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new TodoViewHolder(getContext(), this);
    }


    /**
     * Executes an async filtering by status
     *
     * @param status - the status to filter items by
     *
     * This method should be use the Filter class as soon as Realm adds support for it.
     *
     * The filtering by status can be enhanced if Realm supports a equalTo or contains method that supports an array of strings.
     *
     */
    public void filter(TodoModel.Status status)
    {
        RealmResults<TodoModel> realmResults;

        if (status == null)
        {
            realmResults = todoRepository.listOnlyTodo(fromDueDate, toDueDate);
        }
        else
        {
            realmResults = todoRepository.list(status, fromDueDate, toDueDate);
        }

        setRealmResultsList(realmResults);
    }
}
