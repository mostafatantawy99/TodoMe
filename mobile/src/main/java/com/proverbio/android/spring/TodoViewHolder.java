package com.proverbio.android.spring;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proverbio.android.spring.base.AbstractRecyclerViewHolder;
import com.proverbio.android.spring.util.TimeManager;

/**
 * @author Juan Pablo Proverbio
 *
 * This represents a todo item view holder for a RecyclerView
 */
public class TodoViewHolder extends AbstractRecyclerViewHolder<TodoModel>
{
    /**
     * The todo description
     */
    private final TextView nameView;

    /**
     * The status of the item
     */
    private final TextView statusView;

    /**
     * The due date of the item
     */
    private final TextView dueView;

    public TodoViewHolder(Context context, View.OnClickListener onClickListener)
    {
        super(LayoutInflater.from(context).inflate(R.layout.list_item_todo, null, false), onClickListener);
        this.nameView = (TextView)itemView.findViewById(R.id.name);
        this.statusView = (TextView)itemView.findViewById(R.id.status);
        this.dueView = (TextView)itemView.findViewById(R.id.dueDate);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);
    }


    @Override
    public void onBindViewHolder(TodoModel model)
    {
        nameView.setText(model.getSummary());
        dueView.setText(TimeManager.getHumanFormat(model.getDueDate()));
        invalidateStatusView(getContext(), model, statusView);
    }

    @Override
    public int getViewType()
    {
        return 0;
    }

    /**
     * Styl
     * @param context
     * @param todoModel
     * @param statusView
     */
    public static void invalidateStatusView(Context context, TodoModel todoModel, TextView statusView)
    {
        switch (todoModel.getStatus())
        {
            case PENDING:
                statusView.setText(context.getString(R.string.todo_label));
                statusView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                break;

            case IN_PROGRESS:
                statusView.setText(context.getString(R.string.in_progress_label));
                statusView.setTextColor(ContextCompat.getColor(context, R.color.lightblue));
                break;

            case COMPLETED:
                statusView.setText(context.getString(R.string.completed_label));
                statusView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
        }
    }
}
