package com.proverbio.android.spring.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proverbio.android.spring.R;

import io.realm.RealmObject;

/**
 * @author Juan Pablo Proverbio
 *
 * This is the base Fragment used for Recycler Fragments.
 *
 */
public abstract class AbstractRecyclerFragment<MODEL extends RealmObject,
        VH extends AbstractRecyclerViewHolder<MODEL>, ADAPTER extends AbstractRecyclerAdapter<MODEL, VH>>
        extends Fragment
        implements AbstractRecyclerAdapter.OnRecyclerViewItemClick<MODEL>, SwipeRefreshLayout.OnRefreshListener
{
    private ViewGroup fragmentLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ADAPTER recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        recyclerAdapter = onCreateAdapter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (fragmentLayout == null)
        {
            fragmentLayout = (ViewGroup)inflater.inflate(R.layout.fragment_recycler, null);
            swipeRefreshLayout = (SwipeRefreshLayout)fragmentLayout.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimary, R.color.colorAccent);
            recyclerView = (RecyclerView)fragmentLayout.findViewById(R.id.recyclerView);
            recyclerAdapter.onCreatedView(recyclerView);
        }

        return fragmentLayout;
    }

    public abstract ADAPTER onCreateAdapter();

    public ADAPTER getRecyclerAdapter()
    {
        return recyclerAdapter;
    }

    public ViewGroup getFragmentLayout()
    {
        return fragmentLayout;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return swipeRefreshLayout;
    }

    public RecyclerView getRecyclerView()
    {
        return recyclerView;
    }
}
