package com.proverbio.android.spring;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.proverbio.android.spring.context.repository.TodoRepository;
import com.proverbio.android.spring.util.TimeManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Juan Pablo Proverbio
 *
 * This is a simple reporting fragment. It displays a PieChart about the progress of TODO items TODAY and WEEK
 */
public class ReportsFragment extends Fragment
{
    private static final String TAG = ReportsFragment.class.getSimpleName();

    private TodoRepository todoRepository;

    private ViewGroup fragmentLayout;
    //Today stuff
    private PieChart pieTodayChart;
    private TextView pendingTodayView;
    private TextView inProgressTodayView;
    private TextView completedTodayView;
    //Week stuff
    private PieChart pieWeekChart;
    private TextView pendingWeekView;
    private TextView inProgressWeekView;
    private TextView completedWeekView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        todoRepository = new TodoRepository();
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (fragmentLayout == null)
        {
            fragmentLayout = (ViewGroup)inflater.inflate(R.layout.fragment_report, null);
            //Today stuff
            pieTodayChart = (PieChart)fragmentLayout.findViewById(R.id.pieChart);
            pieTodayChart.setTouchEnabled(false);
            pieTodayChart.setDescription("");
            pieTodayChart.setDrawHoleEnabled(false);
            pieTodayChart.setUsePercentValues(true);
            pieTodayChart.setRotationEnabled(false);
            pieTodayChart.setSelected(false);
            pieTodayChart.getLegend().setEnabled(false);
            pieTodayChart.setNoDataTextDescription(getContext().getString(R.string.no_items_message));
            pendingTodayView = (TextView)fragmentLayout.findViewById(R.id.pending);
            inProgressTodayView = (TextView)fragmentLayout.findViewById(R.id.inprogress);
            completedTodayView = (TextView)fragmentLayout.findViewById(R.id.completed);
            //Week stuff
            pieWeekChart = (PieChart)fragmentLayout.findViewById(R.id.weekChart);
            pieWeekChart.setTouchEnabled(false);
            pieWeekChart.setDescription("");
            pieWeekChart.setDrawHoleEnabled(false);
            pieWeekChart.setUsePercentValues(true);
            pieWeekChart.setRotationEnabled(false);
            pieWeekChart.setSelected(false);
            pieWeekChart.getLegend().setEnabled(false);
            pieWeekChart.setNoDataTextDescription(getContext().getString(R.string.no_items_message));
            pendingWeekView = (TextView)fragmentLayout.findViewById(R.id.pendingWeek);
            inProgressWeekView = (TextView)fragmentLayout.findViewById(R.id.inprogressWeek);
            completedWeekView = (TextView)fragmentLayout.findViewById(R.id.completedWeek);
        }

        invalidateView();

        return fragmentLayout;
    }

    /**
     * This will get data from repository and build the graphs and labels
     */
    private void invalidateView()
    {
        //Today graph stuff
        Pair<Date, Date> today = TimeManager.getTodayDatePair();
        int pendingCount = todoRepository.list(TodoModel.Status.PENDING, today.first, today.second).size();
        int inProgressCount = todoRepository.list(TodoModel.Status.IN_PROGRESS, today.first, today.second).size();
        int completedCount = todoRepository.list(TodoModel.Status.COMPLETED, today.first, today.second).size();

        List<String> labels = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        List<Entry> values = new ArrayList<>();

        if (pendingCount > 0)
        {
            labels.add(getContext().getString(R.string.todo_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorAccent));
            values.add(new Entry(pendingCount, values.size()));
        }

        if (inProgressCount > 0)
        {
            labels.add(getContext().getString(R.string.in_progress_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.lightblue));
            values.add(new Entry(inProgressCount, values.size()));
        }

        if (completedCount > 0)
        {
            labels.add(getContext().getString(R.string.completed_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            values.add(new Entry(completedCount, values.size()));
        }

        if (labels.isEmpty())
        {
            labels.add(getContext().getString(R.string.no_items_message));
            colors.add(ContextCompat.getColor(getContext(), R.color.gray));
            values.add(new Entry(100, 0));
        }

        PieDataSet pieDataSet = new PieDataSet(values, "");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(labels, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieTodayChart.setData(pieData);
        pieTodayChart.invalidate();

        pendingTodayView.setText(String.valueOf(pendingCount) + getContext().getString(R.string.items_label));
        inProgressTodayView.setText(String.valueOf(inProgressCount)  + getContext().getString(R.string.items_label));
        completedTodayView.setText(String.valueOf(completedCount)  + getContext().getString(R.string.items_label));

        //Week graph stuff
        Pair<Date, Date> week = TimeManager.getTodayWeekPair();
        pendingCount = todoRepository.list(TodoModel.Status.PENDING, week.first, week.second).size();
        inProgressCount = todoRepository.list(TodoModel.Status.IN_PROGRESS, week.first, week.second).size();
        completedCount = todoRepository.list(TodoModel.Status.COMPLETED, week.first, week.second).size();

        labels = new ArrayList<>();
        colors = new ArrayList<>();
        values = new ArrayList<>();

        if (pendingCount > 0)
        {
            labels.add(getContext().getString(R.string.todo_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorAccent));
            values.add(new Entry(pendingCount, values.size()));
        }

        if (inProgressCount > 0)
        {
            labels.add(getContext().getString(R.string.in_progress_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.lightblue));
            values.add(new Entry(inProgressCount, values.size()));
        }

        if (completedCount > 0)
        {
            labels.add(getContext().getString(R.string.completed_label));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            values.add(new Entry(completedCount, values.size()));
        }

        if (labels.isEmpty())
        {
            labels.add(getContext().getString(R.string.no_items_message));
            colors.add(ContextCompat.getColor(getContext(), R.color.gray));
            values.add(new Entry(100, 0));
        }

        pieDataSet = new PieDataSet(values, "");
        pieDataSet.setColors(colors);

        pieData = new PieData(labels, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieWeekChart.setData(pieData);
        pieWeekChart.invalidate();

        pendingWeekView.setText(String.valueOf(pendingCount) + getContext().getString(R.string.items_label));
        inProgressWeekView.setText(String.valueOf(inProgressCount)  + getContext().getString(R.string.items_label));
        completedWeekView.setText(String.valueOf(completedCount)  + getContext().getString(R.string.items_label));
    }
}
