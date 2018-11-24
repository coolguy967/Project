package ca.uoit.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
        //to get data and append to list
        Cursor data = mDataBaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mlistView.setAdapter(adapter);
    }
}
