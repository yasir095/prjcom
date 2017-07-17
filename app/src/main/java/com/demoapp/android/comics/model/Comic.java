package com.demoapp.android.comics.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

//Dagger can be used to make these modules more reusable and making testing easy.
// still learning dagger so didn't implemented.
public class Comic implements Parcelable
{
    private String id;
    private String title;
    private int pageCount;
    private String description;
    private ArrayList<Price> prices;
    private ArrayList<Author> authors;
    private HashMap<String, String> thumbnail;//contains path, extension

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag)
    {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeInt(pageCount);
        parcel.writeString(description);
        parcel.writeList(prices);
        parcel.writeList(authors);
        parcel.writeMap(thumbnail);
    }

    public Comic()
    {

    }

    private Comic(Parcel in)
    {
        this.id = in.readString();
        this.title = in.readString();
        this.pageCount = in.readInt();
        this.description = in.readString();
        //this.prices = in.readArrayList(Price.class.getClassLoader());
        this.prices = in.readArrayList(Price.class.getClassLoader());

        this.authors = in.readArrayList(Author.class.getClassLoader());
        this.thumbnail = in.readHashMap(String.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>()
    {
        @Override
        public Comic createFromParcel(Parcel in)
        {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size)
        {
            return new Comic[size];
        }
    };

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public void addAuthors(Author author)
    {
        if(this.authors == null)
        {
            this.authors = new ArrayList<>();
        }

        this.authors.add(author);
    }

    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Price> prices)
    {
        this.prices = prices;
    }

    public void addPrice(Price price)
    {
        if(this.prices == null)
        {
            this.prices = new ArrayList<>();
        }

        this.prices.add(price);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail()
    {
        return thumbnail.get("path")+"."+thumbnail.get("extension");
    }

    public void setThumbnail(HashMap<String, String> thumbnail) {
        this.thumbnail = thumbnail;
    }
}
