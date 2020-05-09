package com.holdbetter.dbperfectproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class SearchableActivity extends ListActivity
{
    ArrayAdapter<String> adapter;
    ArrayList<String> initialData = new ArrayList<>();
    ArrayList<String> resultData;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setInitialData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, initialData);
        setListAdapter(adapter);

        if (getIntent().getAction().equals(Intent.ACTION_SEARCH))
        {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            makeQuery(query);
        }
    }

    private void makeQuery(String query)
    {
        for (int i = initialData.size() - 1; i >= 0; i--)
        {
            String name = initialData.get(i);

            if (!query.trim().isEmpty() && !name.equals(query))
            {
                initialData.remove(name);
            }
        }
    }

    private void setInitialData()
    {
        initialData.add("Вилен");
        initialData.add("Владимир");
        initialData.add("Тимур");
        initialData.add("Юля");
        initialData.add("Оля");
    }
}
