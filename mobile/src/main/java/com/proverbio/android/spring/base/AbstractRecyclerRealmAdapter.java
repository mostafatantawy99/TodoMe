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
public abstract class AbstractRecyclerRealmAdapter<MODEL extends RealmObject,
        VH extends AbstractRecyclerViewHolder<MODEL>> extends RecyclerView.Adapter<VH>
        implements View.OnClickListener
{
    private static final String TAG = AbstractRecyclerRealmAdapter.class.getSimpleName();

    private final Context context;

    private final Class<MODEL> modelType;

    private RealmResults<MODEL> realmResultsList;

    private final OnRecyclerViewItemClick<MODEL> itemClickListener;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    public AbstractRecyclerRealmAdapter(Context context,
                                        Class<MODEL> modelType,
                                        OnRecyclerViewItemClick<MODEL> itemClickListener)
    {
        this.context = context;
        this.modelType = modelType;
        this.itemClickListener = itemClickListener;
}

    public AbstractRecyclerRealmAdapter(Context context, Class<MODEL> modelType,
                                        OnRecyclerViewItemClick<MODEL> itemClickListener,
                                        RealmResults<MODEL> realmResultsList)
    {
        this(context, modelType, itemClickListener);
        setRealmResultsList(realmResultsList);
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
        holder.onBindViewHolder(realmResultsList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return realmResultsList.size();
    }

    @Override
    public void onClick(final View view)
    {
        if (recyclerView == null)
        {
            throw new IllegalStateException( "onCreatedView needs to be called and the RecyclerView can not be null" );
        }

        int itemPosition = recyclerView.getChildAdapterPosition(view);
        itemClickListener.onItemClick(itemPosition, realmResultsList.get(itemPosition));
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

    public RealmResults<MODEL> getRealmResultsList()
    {
        return realmResultsList;
    }

    public void setRealmResultsList(RealmResults<MODEL> realmResultsList)
    {
        this.realmResultsList = realmResultsList;

        if (this.realmResultsList != null)
        {
            this.realmResultsList.addChangeListener(new RealmChangeListener()
            {
                @Override
                public void onChange()
                {
                    notifyDataSetChanged();
                }
            });
        }

        notifyDataSetChanged();
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
