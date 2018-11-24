package ca.uoit.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.uoit.project.database.DBHelper;
import ca.uoit.project.database.Restaurant;
import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class ListDataActivity extends AppCompatActivity
{
    private static final String TAG = "ListDataActivity";
    DBHelper mDataBaseHelper;
    private ListView mlistView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstancedState)
    {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.list_layout);
        mlistView = (ListView) findViewById(R.id.listView);
        mDataBaseHelper = new DBHelper(this);
        populateListView();
    }

    private void populateListView()
    {
        Intent intent = getIntent();

        int price = intent.getIntExtra("price", 0);
        String foodType = intent.getStringExtra("foodtype");
        Atmosphere atmosphere = (Atmosphere) intent.getSerializableExtra("atmosphere");
        ServingMethod servingMethod = (ServingMethod) intent.getSerializableExtra("serving");

        //to get data and append to list
        Cursor data = mDataBaseHelper.getData();
        List<Restaurant> listData = mDataBaseHelper.findRestaurants(foodType, price, atmosphere, servingMethod);

        /*while(data.moveToNext())
        {
            listData.add(data.getString(1));
        }*/

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mlistView.setAdapter(adapter);
    }
}
