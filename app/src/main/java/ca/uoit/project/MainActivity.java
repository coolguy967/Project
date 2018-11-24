package ca.uoit.project;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity
{
    Button eatAtHome;
    Button eatOutside;
    private static final String TAG = "MainActivity";
    DBHelper myDb;
    private Button btnAddData, btnViewData;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBHelper(this);
        btnAddData = (Button) findViewById(R.id.AddData); //id for button to add data
        btnViewData = (Button) findViewById(R.id.ViewData); // id for button to view data
        editText = (EditText) findViewById(R.id.editText); //id for search text feild
        myDb = new DBHelper(this);

        btnAddData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0)
                {
                    AddData(newEntry);
                    editText.setText("");
                }
                else
                {

                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eatAtHome = (Button) findViewById(R.id.eat_at_home);
        eatOutside = (Button) findViewById(R.id.eat_outside);

        eatAtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EatAtHome.class);
                startActivity(intent);
            }
        });

        eatOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EatOutside.class);
                startActivity(intent);
            }
        });

    }
    private void AddData(String newEntry)
    {
        boolean insertData = myDb.addData(newEntry);
        if (insertData)
        {
            //what to do when data added
        }
        else
        {
            //what to do when data not added successfully
        }
    }
}
