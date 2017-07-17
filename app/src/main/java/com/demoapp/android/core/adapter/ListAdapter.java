package com.demoapp.android.core.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by yasirmahmood.
 */

public abstract class ListAdapter<T> extends BaseAdapter
{
    private ArrayList<T> arrayList;

    public ListAdapter(ArrayList<T> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public Object getItem(int index)
    {
        return arrayList.get(index);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return this.getAdapterView(position, convertView, parent);
    }

    public abstract View getAdapterView(int position, View convertView, ViewGroup parent);
}
