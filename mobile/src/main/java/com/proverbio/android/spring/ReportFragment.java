package com.proverbio.android.spring;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.proverbio.android.spring.context.GenericDao;

import java.util.Arrays;
import java.util.List;

/**
 * @author Juan Pablo Proverbio
 */
public class ReportFragment extends Fragment
{
    private ViewGroup fragmentLayout;
    private PieChart pieChart;
    private TextView pendingView;
    private TextView inProgressView;
    private TextView completedView;

    private int pendingCount;
    private int inProgressCount;
    private int completedCount;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (fragmentLayout == null)
        {
            fragmentLayout = (ViewGroup)inflater.inflate(R.layout.fragment_report, null);
            pieChart = (PieChart)fragmentLayout.findViewById(R.id.pieChart);
            pieChart.setTouchEnabled(false);
            pieChart.setDescription("");
            pieChart.setDrawHoleEnabled(false);
            pieChart.setUsePercentValues(true);
            pieChart.setRotationEnabled(false);
            pieChart.setSelected(false);
            pieChart.getLegend().setEnabled(false);
            pendingView = (TextView)fragmentLayout.findViewById(R.id.pending);
            inProgressView = (TextView)fragmentLayout.findViewById(R.id.inprogress);
            completedView = (TextView)fragmentLayout.findViewById(R.id.completed);
            //TODO pieChart.setNoDataText( getContext().getString( R.string.no_activity_registered ) );
        }

        invalidateView();

        return fragmentLayout;
    }

    private void invalidateView()
    {
        //Query for building PieChart
        pendingCount = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.PENDING.toString()).size();
        inProgressCount = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.IN_PROGRESS.toString()).size();
        completedCount = GenericDao.listByStatus(TodoModel.class, TodoModel.Status.COMPLETED.toString()).size();

        List<String> labels = Arrays.asList(getContext().getString(R.string.todo_label),
                getContext().getString(R.string.in_progress_label),
                getContext().getString(R.string.completed_label));

        List<Entry> values = Arrays.asList(new Entry(pendingCount, 0), new Entry(inProgressCount, 1),
                new Entry(completedCount, 2));

        List<Integer> colors = Arrays.asList(ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.lightblue),
                ContextCompat.getColor(getContext(), R.color.colorPrimary));

        PieDataSet pieDataSet = new PieDataSet(values, "");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(labels, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pendingView.setText(String.valueOf(pendingCount) + " items");
        inProgressView.setText(String.valueOf(inProgressCount) + " items");
        completedView.setText(String.valueOf(completedCount) + " items");
    }
}
