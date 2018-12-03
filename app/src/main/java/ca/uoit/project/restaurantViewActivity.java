package ca.uoit.project;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ca.uoit.project.database.DBHelper;
import ca.uoit.project.database.Restaurant;
import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;


public class restaurantViewActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_view);


        Button like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Pass the restaurant that's used to populate this page to this function in place of resName
                myDb.toggleFavorite(resName);
            }
        }
    }
}
