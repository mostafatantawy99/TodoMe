package com.proverbio.android.spring.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.RealmObject;

/**
 * @author Juan Pablo Proverbio
 *
 * This is an abstract RecyclerView.ViewHolder. The base class for all recycler view holders.
 */
public abstract class AbstractRecyclerViewHolder<MODEL extends RealmObject> extends RecyclerView.ViewHolder
{
    private final Context context;

    private final View.OnClickListener onClickListener;

    public AbstractRecyclerViewHolder(View itemView, View.OnClickListener onClickListener)
    {
        super( itemView );
        this.context = this.itemView.getContext();
        this.onClickListener = onClickListener;
        this.itemView.setOnClickListener(onClickListener);
    }

    /**
     * Does the binding needed for this view holder
     * @param model
     */
    public abstract void onBindViewHolder(MODEL model);

    public abstract int getViewType();

    public Context getContext()
    {
        return context;
    }

    public View.OnClickListener getOnClickListener()
    {
        return onClickListener;
    }
}
