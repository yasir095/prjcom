package com.testapps.android.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yasirmahmood on 13/06/2017.
 */

public class Price implements Parcelable
{
    private float price;
    private String type;

    private Price(Parcel in)
    {
        this.price = in.readFloat();
        this.type = in.readString();
    }

    public Price()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeFloat(price);
        parcel.writeString(type);
    }

    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>()
    {
        @Override
        public Price createFromParcel(Parcel in)
        {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size)
        {
            return new Price[size];
        }
    };

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPrice(String price)
    {
        this.price = Float.parseFloat(price);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
