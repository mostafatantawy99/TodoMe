package com.proverbio.android.spring;

import android.content.Context;
import android.view.ViewGroup;

import com.proverbio.android.spring.base.AbstractRecyclerAdapter;
import com.proverbio.android.spring.context.GenericDao;
import com.proverbio.android.spring.util.StringConstants;

import java.util.LinkedHashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoListAdapter extends AbstractRecyclerAdapter<TodoModel, TodoViewHolder>
{
    /**
     * The TAG used for log messages from this class
     */
    public static final String TAG = TodoListAdapter.class.getSimpleName();

    /**
     * A set containing the statuses to filter items by
     */
    private Set<TodoModel.Status> filterStatuses;

    public TodoListAdapter(Context context,
                           OnRecyclerViewItemClick<TodoModel> itemClickListener,
                           RealmResults<TodoModel> items)
    {
        super(context, itemClickListener, items);

    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new TodoViewHolder(getContext(), this);
    }

    /**
     * Adds or removes a status from the filterStatuses array and then executes the filtering by status
     *
     * @param status - a status to be added or removed from filterStatuses array
     * @param isAdd - a flag to decide to add or remove status from filterStatuses array
     *
     * This method should be use the Filter class as soon as Realm adds support for it.
     *
     * The filtering by status can be enhanced if Realm supports a equalTo or contains method that supports an array of strings.
     *
     * Real life app this would also be async
     */
    public void filter(TodoModel.Status status, boolean isAdd)
    {
        if (isAdd)
        {
            getFilterStatuses().add(status);
        }
        else
        {
            getFilterStatuses().remove(status);
        }

        if (getFilterStatuses().isEmpty() || getFilterStatuses().size() == 3)
        {
            RealmResults<TodoModel> results = GenericDao.list(TodoModel.class);
            setItems(results);
        }
        else
        {
            RealmResults<TodoModel> results;

            if (getFilterStatuses().contains(TodoModel.Status.PENDING) &&
                    getFilterStatuses().contains(TodoModel.Status.IN_PROGRESS) &&
                    !getFilterStatuses().contains(TodoModel.Status.COMPLETED))

            {
                results = Realm.getDefaultInstance().where(TodoModel.class)
                        .equalTo(StringConstants.STATUS, TodoModel.Status.PENDING.toString())
                        .or()
                        .equalTo(StringConstants.STATUS, TodoModel.Status.IN_PROGRESS.toString())
                        .findAll();
            }
            else if (getFilterStatuses().contains(TodoModel.Status.PENDING) &&
                    !getFilterStatuses().contains(TodoModel.Status.IN_PROGRESS) &&
                    getFilterStatuses().contains(TodoModel.Status.COMPLETED))

            {
                results = Realm.getDefaultInstance().where(TodoModel.class)
                        .equalTo(StringConstants.STATUS, TodoModel.Status.PENDING.toString())
                        .or()
                        .equalTo(StringConstants.STATUS, TodoModel.Status.COMPLETED.toString())
                        .findAll();
            }
            else if (!getFilterStatuses().contains(TodoModel.Status.PENDING) &&
                    getFilterStatuses().contains(TodoModel.Status.IN_PROGRESS) &&
                    getFilterStatuses().contains(TodoModel.Status.COMPLETED))

            {
                results = Realm.getDefaultInstance().where(TodoModel.class)
                        .equalTo(StringConstants.STATUS, TodoModel.Status.IN_PROGRESS.toString())
                        .or()
                        .equalTo(StringConstants.STATUS, TodoModel.Status.COMPLETED.toString())
                        .findAll();
            }
            else if (getFilterStatuses().contains(TodoModel.Status.PENDING))

            {
                results = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.PENDING.toString());
            }
            else if (getFilterStatuses().contains(TodoModel.Status.IN_PROGRESS))
            {
                results = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.IN_PROGRESS.toString());
            }
            else

            {
                results = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.COMPLETED.toString());
            }

            setItems(results);
        }
    }

    /**
     * @return - Returns the FilterStatuses array - This would be used latter on to filter by statuses
     */
    private Set<TodoModel.Status> getFilterStatuses()
    {
        if (filterStatuses == null)
        {
            filterStatuses = new LinkedHashSet<>();
        }

        return filterStatuses;
    }

}
