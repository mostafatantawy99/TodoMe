package com.proverbio.android.spring.base;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Juan Pablo Proverbio
 */
public class AbstractModel extends RealmObject implements Parcelable
{
    public AbstractModel()
    {
        super();
    }

    @PrimaryKey
    private long id;

    protected AbstractModel(Parcel in)
    {
        id = in.readLong();
    }

    public static Creator<AbstractModel> CREATOR = new Creator<AbstractModel>()
    {
        @Override
        public AbstractModel createFromParcel(Parcel in) {
            return new AbstractModel(in);
        }

        @Override
        public AbstractModel[] newArray(int size) {
            return new AbstractModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
    }

    public long getId()
    {
        return id;
    }
}
