package com.proverbio.android.spring.context;


import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author Juan Pablo Proverbio
 *
 * This is a very basic Realm dao
 */
public class GenericDao
{
    private GenericDao()
    {
        super();
    }

    public static int getNextId(Class<? extends RealmObject> classObject)
    {
        Number id = Realm.getDefaultInstance().where(classObject).max("id");

        if (id == null)
        {
            return 0;
        }
        else
        {
            return id.intValue() + 1;
        }
    }

    public static <MODEL extends RealmObject> RealmResults<MODEL> list(Class<MODEL> modelClass)
    {
        RealmResults<MODEL> results = Realm.getDefaultInstance().where(modelClass)
                .findAllSorted("id", Sort.DESCENDING);

        return results;
    }

    public static <MODEL extends RealmObject> RealmResults<MODEL> listByStatus(Class<MODEL> modelClass, String status)
    {
        RealmResults<MODEL> results = Realm.getDefaultInstance().where(modelClass)
                .equalTo("status", status)
                .findAll();

        return results;
    }

    public static <MODEL extends RealmObject> void save(final MODEL model)
    {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
}
