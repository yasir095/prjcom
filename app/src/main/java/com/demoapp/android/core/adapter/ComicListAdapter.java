package com.demoapp.android.core.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.demoapp.android.comics.R;
import com.demoapp.android.core.extras.Logger;
import com.demoapp.android.comics.model.Comic;

import java.util.ArrayList;

/**
 * Created by yasirmahmood on 12/06/2017.
 * should display title and image.
 * details page should display title, description, page count, price and authors of the comic
 */

public class ComicListAdapter extends ListAdapter<Comic>
{
    private ArrayList<Comic> arrayListComic;
    private Activity context;
    private ViewHolder viewHolder;

    public ComicListAdapter(ArrayList<Comic> arrayList, Activity context) {
        super(arrayList);
        this.arrayListComic = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.arrayListComic.size();
    }


    @Override
    public Object getItem(int index)
    {
        try
        {
            return this.arrayListComic.get(index);
        }
        catch(Exception e)
        {
            //@todo log error
        }

        return null;
    }

    @Override
    public View getAdapterView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_cell, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.txtComicTitle);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imgThumbnail);


            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(this.arrayListComic.get(position).getTitle());

        if(this.arrayListComic.get(position).getThumbnail() != null)
        {
            Logger.logSuccess("| "+this.arrayListComic.get(position).getThumbnail());
            Picasso.with(context).load(this.arrayListComic.get(position).getThumbnail()).into(viewHolder.image);
            //Broker.getDefaultImageCache(0).loadBitmap(context, this.arrayListComic.get(position).getThumbnail() , viewHolder.image);

        }

        return convertView;
    }

    static class ViewHolder {

        public TextView title;
        public ImageView image;
    }
}
