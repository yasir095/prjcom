package com.testapps.android.comics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testapps.android.core.model.Author;
import com.testapps.android.core.model.Comic;
import com.testapps.android.core.model.Price;

/**
 * Created by yasirmahmood on 13/06/2017.
 */

public class DetailActivity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.detail_activity);


        //Layout can be improved here with better design and heading. due to lack of time its just a demonstration of functionality.



        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.detail_activity, null);

        Bundle bundle = getIntent().getExtras();
        Comic comic = bundle.getParcelable("extras");

        addText("Title: "+comic.getTitle(), layout);
        addText("Description: "+comic.getDescription(), layout);
        addText("Pages: "+comic.getPageCount()+"", layout);

        for (Price price: comic.getPrices())
        {
            TextView priceView = new TextView(this);
            priceView.setText(price.getType() +"  "+price.getPrice());
            layout.addView(priceView);
        }

        for (Author author: comic.getAuthors())
        {
            TextView priceView = new TextView(this);
            priceView.setText(author.getName() +"  "+author.getRole());
            layout.addView(priceView);
        }

        setContentView(layout);

    }

    private void addText(String text, LinearLayout layout)
    {
        TextView tv = new TextView(this);
        tv.setText(text);
        layout.addView(tv);
    }

}
