package com.proverbio.android.spring.context.repository;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;

/**
 * @author Juan Pablo Proverbio
 *
 *
 */
public abstract class IDataRepository<MODEL extends RealmObject>
{
    private Class<MODEL> modelClass;

    public IDataRepository(Class<MODEL> modelClass)
    {
        this.modelClass = modelClass;
    }

    public abstract RealmAsyncTask saveAsync(MODEL model,
                                   Realm.Transaction.OnSuccess onSuccess,
                                   Realm.Transaction.OnError onError);

    /**
     * This method produces the next id in the Table
     * @return
     */
    public int getNextId()
    {
        Number id = Realm.getDefaultInstance().where(modelClass).max("id");

        if (id == null)
        {
            return 0;
        }
        else
        {
            return id.intValue() + 1;
        }
    }


}
