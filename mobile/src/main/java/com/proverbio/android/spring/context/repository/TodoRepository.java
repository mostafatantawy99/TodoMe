package com.proverbio.android.spring.context.repository;

import com.proverbio.android.spring.TodoModel;
import com.proverbio.android.spring.util.StringConstants;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoRepository extends IDataRepository<TodoModel>
{
    public TodoRepository()
    {
        super(TodoModel.class);
    }

    @Override
    public RealmAsyncTask saveAsync(final TodoModel todoModel,
                     Realm.Transaction.OnSuccess onSuccess,
                     Realm.Transaction.OnError onError)
    {
        Realm realm = Realm.getDefaultInstance();
        return realm.executeTransactionAsync(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                if (todoModel.getId() == -1)
                {
                    todoModel.setId(getNextId());
                }

                realm.copyToRealmOrUpdate(todoModel);
            }
        }, onSuccess, onError );
    }

    public RealmResults<TodoModel> list(TodoModel.Status status, Date fromDueDate, Date toDueDate)
    {
        Realm realm = Realm.getDefaultInstance();

        if (status == null)
        {
            return realm.where(TodoModel.class)
                    .between(TodoModel.DUE_DATE_COLUMN_NAME, fromDueDate, toDueDate)
                    .findAllSorted(StringConstants.ID_FIELD_NAME, Sort.DESCENDING);
        }
        else
        {
            return realm.where(TodoModel.class)
                    .between(TodoModel.DUE_DATE_COLUMN_NAME, fromDueDate, toDueDate)
                    .equalTo(StringConstants.STATUS_FIELD_NAME, status.toString())
                    .findAllSorted(StringConstants.ID_FIELD_NAME, Sort.DESCENDING);
        }
    }

    public RealmResults<TodoModel> listByStatus(TodoModel.Status status)
    {
        if (status != null)
        {
            Realm realm = Realm.getDefaultInstance();

            return realm.where(TodoModel.class)
                    .equalTo(StringConstants.STATUS_FIELD_NAME, status.toString())
                    .findAllSorted(StringConstants.ID_FIELD_NAME, Sort.DESCENDING);
        }

        throw new IllegalArgumentException("Status should not be null");
    }

    public RealmResults<TodoModel> listOnlyTodo(Date fromDueDate, Date toDueDate)
    {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(TodoModel.class)
                .between(TodoModel.DUE_DATE_COLUMN_NAME, fromDueDate, toDueDate)
                .equalTo(StringConstants.STATUS_FIELD_NAME, TodoModel.Status.PENDING.toString()).or()
                .equalTo(StringConstants.STATUS_FIELD_NAME, TodoModel.Status.IN_PROGRESS.toString())
                .findAllSorted(StringConstants.ID_FIELD_NAME, Sort.DESCENDING);
    }

}

