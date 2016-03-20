package com.proverbio.android.spring;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Juan Pablo Proverbio
 */
public class TodoModel extends RealmObject implements Parcelable
{
    @PrimaryKey
    private long id;

    private String summary;

    private String description;

    private String status;

    private Date dueDate;

    private Date inProgressDate;

    private Date completedDate;

    public TodoModel()
    {
        super();
        status = Status.PENDING.toString();
        id = -1l;
    }

    public TodoModel(String summary, String description, Status status, Date dueDate)
    {
        this.summary = summary;
        this.description = description;
        this.status = status == null ? Status.PENDING.toString() : status.toString();
        this.dueDate = dueDate;
    }

    protected TodoModel(Parcel in)
    {
        this.id = in.readLong();
        setSummary(in.readString());
        setDescription(in.readString());
        setStatus(in.readString());
        setDueDate((Date) in.readSerializable());
        setInProgressDate((Date)in.readSerializable());
        setCompletedDate((Date)in.readSerializable());
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Status getStatus()
    {
        return Status.valueOf(status);
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getInProgressDate()
    {
        return inProgressDate;
    }

    public void setInProgressDate(Date inProgressDate)
    {
        this.inProgressDate = inProgressDate;
    }

    public Date getCompletedDate()
    {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate)
    {
        this.completedDate = completedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(getId());
        dest.writeString(getSummary());
        dest.writeString(getDescription());
        dest.writeString(getStatus().toString());
        dest.writeSerializable(getDueDate());
        dest.writeSerializable(getInProgressDate());
        dest.writeSerializable(getCompletedDate());
    }

    public static final Creator CREATOR = new Creator()
    {
        public TodoModel createFromParcel(Parcel in)
        {
            return new TodoModel(in);
        }

        public TodoModel[] newArray(int size)
        {
            return new TodoModel[size];
        }
    };

    public static enum Status
    {
        PENDING,
        IN_PROGRESS,
        COMPLETED;
    }

}
