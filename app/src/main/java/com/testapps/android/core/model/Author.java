package com.testapps.android.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yasirmahmood on 13/06/2017.
 */

public class Author implements Parcelable
{
    private String url;
    private String role;
    private String name;

    public Author()
    {

    }

    private Author(Parcel in)
    {
        this.url = in.readString();
        this.role = in.readString();
        this.name = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(url);
        parcel.writeString(role);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>()
    {
        @Override
        public Author createFromParcel(Parcel in)
        {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size)
        {
            return new Author[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
