package com.testapps.android.comics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testapps.android.core.Broker;
import com.testapps.android.core.adapter.ComicListAdapter;
import com.testapps.android.core.model.Comic;
import com.testapps.android.core.resultset.ComicResultSet;
import com.testapps.android.core.resultset.ResultSet;
import com.testapps.android.core.resultset.ResultSetDelegate;

import java.util.ArrayList;
import java.util.List;

//Picasso library used to make image loading better.
//Have implemented my own image loader as well but not using it.

public class MainActivity extends AppCompatActivity implements ResultSetDelegate
{
    private ComicResultSet comicResultSet;
    private ArrayList<Comic> arrayListComics = new ArrayList<>();
    private ComicListAdapter listAdapter;
    private  ListView listView;
    private final String EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.comicResultSet = Broker.getDefaultBroker().getComics(this);
        this.comicResultSet.fetch(this);

        this.listAdapter = new ComicListAdapter(this.arrayListComics, this);
        listView = (ListView) findViewById(R.id.list_view);
        this.listView.setAdapter(this.listAdapter);


        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Dart 2 & Henson can be used here along with butterKnife libraries to make
                //this code look better.

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(EXTRAS, (Comic) listAdapter.getItem(i));
                startActivity(intent);
            }
        });

        //you can call cancel on resultSet.cancel if your activity is destroyed.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompleteResult(ResultSet resultSet)
    {
        this.arrayListComics.addAll(((ComicResultSet) resultSet).getParsedData());
        this.listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailResult(ResultSet resultSet, int statusCode)
    {

    }

    @Override
    protected void onDestroy()
    {
        this.comicResultSet.cancel();
        super.onDestroy();
    }
}
