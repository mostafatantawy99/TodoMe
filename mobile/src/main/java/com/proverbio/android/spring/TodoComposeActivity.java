package com.proverbio.android.spring;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proverbio.android.spring.base.AbstractActivity;
import com.proverbio.android.spring.context.repository.TodoRepository;
import com.proverbio.android.spring.util.StringConstants;
import com.proverbio.android.spring.util.TimeManager;
import com.proverbio.android.spring.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;


/**
 * @author Juan Pablo Proverbio
 */
public class TodoComposeActivity extends AbstractActivity implements View.OnClickListener
{
    private static final String TAG = TodoComposeActivity.class.getSimpleName();

    private TodoRepository todoRepository;

    private EditText summaryEditText;
    private TextView dueDateView;
    private ViewGroup statusLayout;
    private TextView statusView;
    private EditText detailsView;

    private TodoModel todoModel;
    private boolean isCompose;

    private final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            todoModel.setDueDate(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
            dueDateView.setText(TimeManager.getHumanFormat(todoModel.getDueDate()));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.todoRepository = new TodoRepository();

        //Show back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null)
        {
            todoModel = getIntent().getParcelableExtra(StringConstants.ITEM_KEY);
            isCompose =  getIntent().getBooleanExtra(StringConstants.IS_COMPOSE_KEY, false);
        }

        if (todoModel == null)
        {
            todoModel = new TodoModel();
        }

        summaryEditText = (EditText)findViewById(R.id.summary);
        detailsView = (EditText)findViewById(R.id.description);
        dueDateView = (TextView)findViewById(R.id.dueDate);
        statusLayout = (ViewGroup)findViewById(R.id.statusLayout);
        statusView = (TextView)findViewById(R.id.status);
        invalidateView();
    }

    private void invalidateView()
    {
        invalidateOptionsMenu();

        summaryEditText.setEnabled(isCompose);
        detailsView.setEnabled(isCompose);
        dueDateView.setOnClickListener(isCompose ? this : null);
        statusLayout.setVisibility(isCompose ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(todoModel.getSummary()))
        {
            summaryEditText.setText(todoModel.getSummary());
        }

        if (!TextUtils.isEmpty(todoModel.getDescription()))
        {
            detailsView.setText(todoModel.getDescription());
        }

        if (todoModel.getDueDate() != null)
        {
            dueDateView.setText(TimeManager.getHumanFormat(todoModel.getDueDate()));
        }

        if (todoModel.getStatus() != null)
        {
            TodoViewHolder.invalidateStatusView(this, todoModel, statusView);
        }

        setTitle(!isCompose ? getString(R.string.view_todo_label) :
                todoModel.getId() == -1 ? getString(R.string.new_todo_label) :
                        getString(R.string.edit_todo_label));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if (isCompose && todoModel.getId() != -1l)
                {
                    isCompose = false;
                    invalidateView();
                }
                else
                {
                    onBackPressed();
                }
                return true;

            case R.id.edit:
                isCompose = true;
                invalidateView();
                return true;

            case R.id.start:
                todoModel.setStatus(TodoModel.Status.IN_PROGRESS.toString());
                todoModel.setInProgressDate(new Date());
                save(false);
                return true;

            case R.id.complete:
                todoModel.setStatus(TodoModel.Status.COMPLETED.toString());
                todoModel.setCompletedDate(new Date());
                save(false);
                break;

            case R.id.send:
                if (Validator.hasText(summaryEditText) & Validator.hasText(dueDateView))
                {
                    todoModel.setSummary(summaryEditText.getText().toString());
                    todoModel.setDescription(detailsView.getText().toString());
                    save(true);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(final boolean finish)
    {
        todoRepository.saveAsync(todoModel, new Realm.Transaction.OnSuccess()
        {
            @Override
            public void onSuccess()
            {
                if (finish)
                {
                    finish();
                } else
                {
                    invalidateView();
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(TodoComposeActivity.this, TodoComposeActivity.this.getString(R.string.could_not_save_changes),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, error.getMessage(), error);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem sendItem = menu.findItem(R.id.send);
        MenuItem editItem = menu.findItem(R.id.edit);
        MenuItem startItem = menu.findItem(R.id.start);
        MenuItem completeItem = menu.findItem(R.id.complete);

        if (sendItem == null || editItem == null || startItem == null || completeItem == null)
        {
            return false;
        }

        if (!isCompose)
        {
            if (TodoModel.Status.IN_PROGRESS.equals(todoModel.getStatus()))
            {
                completeItem.setVisible(true).setEnabled(true);
            }
            else if (TodoModel.Status.PENDING.equals(todoModel.getStatus()))
            {
                startItem.setVisible(true).setEnabled(true);
            }

            editItem.setVisible(true).setEnabled(true);
        }
        else
        {
            sendItem.setVisible(true).setEnabled(true);
        }

        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.dueDate:
                DatePickerDialog datePickerDialog;

                if (todoModel.getDueDate() != null)
                {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(todoModel.getDueDate().getTime());

                    datePickerDialog = new DatePickerDialog(this,
                            onDateSetListener, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                else
                {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(new Date().getTime());

                    datePickerDialog = new DatePickerDialog(this,
                            onDateSetListener, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }

                datePickerDialog.setTitle(getString(R.string.due_label));
                datePickerDialog.show();
                break;

            case R.id.fab:
                break;
        }
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_compose;
    }
}
