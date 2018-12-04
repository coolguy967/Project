package ca.uoit.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import ca.uoit.project.database.DBHelper;
import ca.uoit.project.database.Restaurant;
import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBHelper(this);
        myDb.initDatabase();

        Button res_search = findViewById(R.id.res_search);

        final Spinner food_in = findViewById(R.id.food_in);
        final Spinner price_in = findViewById(R.id.price_in);
        final Spinner atm_in = findViewById(R.id.atm_in);
        final Spinner serv_in = findViewById(R.id.serv_in);
        final Spinner dist_in = findViewById(R.id.dist_in);

        res_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from screen
                String          foodType        = (String) food_in .getItemAtPosition(food_in .getSelectedItemPosition());
                String          priceStr        = (String) price_in.getItemAtPosition(price_in.getSelectedItemPosition());
                String          distance        = (String) dist_in .getItemAtPosition(dist_in .getSelectedItemPosition());

                Atmosphere      atmosphere      = Atmosphere   .fromString((String) atm_in .getItemAtPosition(atm_in .getSelectedItemPosition()));
                ServingMethod   servingMethod   = ServingMethod.fromString((String) serv_in.getItemAtPosition(serv_in.getSelectedItemPosition()));

                int price = 0;

                // Parse price as integer
                if(!priceStr.equals("Any")) {
                    // Find the dollar sign
                    int index = priceStr.indexOf('$');

                    // Parse the number after the dollar sign as an integer
                    price = Integer.parseInt(priceStr.substring(index + 1));
                }

                //Intent to start next activity
                Intent intent = new Intent(getBaseContext(), ListDataActivity.class);

                //Passing relevant information to the next activity
                intent.putExtra("foodtype", foodType);
                intent.putExtra("price", price);
                intent.putExtra("atmosphere", atmosphere);
                intent.putExtra("serving", servingMethod);

                //Beginning the next activity
                startActivity(intent);
            }
        });
    }
}
