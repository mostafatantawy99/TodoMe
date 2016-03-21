package com.proverbio.android.spring.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * @author Juan Pablo Proverbio
 *
 * 
 */
public abstract class AbstractRecyclerAdapter<MODEL extends RealmObject, VH extends AbstractRecyclerViewHolder<MODEL>> extends RecyclerView.Adapter<VH>
        implements View.OnClickListener
{
    private static final String TAG = AbstractRecyclerAdapter.class.getSimpleName();

    private final Context context;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private RealmResults<MODEL> items;

    private final OnRecyclerViewItemClick<MODEL> itemClickListener;

    public AbstractRecyclerAdapter(Context context, OnRecyclerViewItemClick<MODEL> itemClickListener)
    {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public AbstractRecyclerAdapter(Context context, OnRecyclerViewItemClick<MODEL> itemClickListener, RealmResults<MODEL> items)
    {
        this(context, itemClickListener);

        if (items != null)
        {
            this.items = items;
            this.items.addChangeListener(new RealmChangeListener()
            {
                @Override
                public void onChange()
                {
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * This should be called when the Fragment
     * @param recyclerView
     */
    public void onCreatedView(RecyclerView recyclerView, RecyclerView.OnScrollListener scrollListener)
    {
        if (this.recyclerView != null)
        {
            //This been already setup
            return;
        }

        this.recyclerView = recyclerView;
        this.layoutManager = getLayoutManager();
        this.recyclerView.setLayoutManager(layoutManager);

        if (scrollListener != null)
        {
            this.recyclerView.addOnScrollListener(scrollListener);
        }

        if ( getItemDecoration() != null )
        {
            this.recyclerView.addItemDecoration(getItemDecoration());
        }

        this.recyclerView.setAdapter(this);
    }

    /**
     * This should be called when the Fragment
     * @param recyclerView
     */
    public void onCreatedView(RecyclerView recyclerView)
    {
        onCreatedView(recyclerView, null);
    }

    @Override
    public void onBindViewHolder(VH holder, int position)
    {
        holder.onBindViewHolder(items.get(position));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @Override
    public void onClick(final View view)
    {
        if (recyclerView == null)
        {
            throw new IllegalStateException( "onCreatedView needs to be called and the RecyclerView can not be null" );
        }

        int itemPosition = recyclerView.getChildAdapterPosition(view);
        itemClickListener.onItemClick(itemPosition, items.get(itemPosition));
    }

    /**
     * Returns the LayoutManager that should be used with this RecyclerView
     * Allows to overwrite in order to use a different layout manager
     * @return - Returns a LayoutManager
     */
    public LinearLayoutManager getLayoutManager()
    {
        return new LinearLayoutManager(getContext());
    }

    /**
     * Allows to define an ItemDecoration.
     * @return - Returns the ItemDecoration that this RecyclerView should use
     */
    public RecyclerView.ItemDecoration getItemDecoration()
    {
        return new ItemDividerDecorator(getContext());
    }



    public Context getContext()
    {
        return context;
    }

    public RealmResults<MODEL> getItems()
    {
        return items;
    }

    public void setItems(RealmResults<MODEL> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }

    protected OnRecyclerViewItemClick<MODEL> getItemClickListener()
    {
        return itemClickListener;
    }

    /**
     * Listener to handle onclick even on an item in the RecyclerView
     * @param <MODEL>
     */
    public interface OnRecyclerViewItemClick<MODEL extends RealmObject>
    {
        void onItemClick(int position, MODEL model);
    }

}
