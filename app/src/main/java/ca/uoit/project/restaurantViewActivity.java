package ca.uoit.project;


import android.content.Intent;
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

    TextView resName, resAddress, resPrice, resType, resServing, resAtmosphere, resFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_view);
        myDb = new DBHelper(this);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        int price = intent.getIntExtra("price", 0);
        String type = intent.getStringExtra("type");
        ServingMethod serving = (ServingMethod) intent.getSerializableExtra("serving");
        Atmosphere atmosphere = (Atmosphere) intent.getSerializableExtra("atmosphere");
        boolean favourite = intent.getBooleanExtra("favourite", false);

        final Restaurant restaurant = new Restaurant(id, name, address, type, price, atmosphere, serving, favourite);

        resName = (TextView) findViewById(R.id.resName);
        resAddress = (TextView) findViewById(R.id.resAddress);
        resPrice = (TextView) findViewById(R.id.resPrice);
        resType = (TextView) findViewById(R.id.resType);
        resServing = (TextView) findViewById(R.id.resServing);
        resAtmosphere = (TextView) findViewById(R.id.resAtmosphere);
        resFavourite = (TextView) findViewById(R.id.resFavourite);

        resName.setText(name);
        resAddress.setText(address);
        resPrice.setText(Integer.toString(price));
        resType.setText(type);
        resServing.setText(serving.description);
        resAtmosphere.setText(atmosphere.description);
        resFavourite.setText(Boolean.toString(favourite));

        Button like = findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the restaurant that's used to populate this page to this function in place of resName
                myDb.toggleFavorite(restaurant);

                resFavourite.setText(Boolean.toString(restaurant.favourite));
            }
        });
    }
}
