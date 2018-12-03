package ca.uoit.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ca.uoit.project.database.Restaurant;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private List<Restaurant> restaurants;
    private Restaurant restaurant;
    LinearLayout layout;
    Context con;

    public RestaurantAdapter(Context context, int resource, List<Restaurant> lst)
    {
        super(context, resource);

        restaurants = lst;
        con = context;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.restaurant_template, null, true);

        TextView resName = (TextView)row.findViewById(R.id.restaurant_Name);
        layout = (LinearLayout) row.findViewById(R.id.Linearlayout);

        restaurant = restaurants.get(position);

        resName.setText(restaurant.name);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, restaurantViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("name", restaurant.name);
                intent.putExtra("address", restaurant.location);
                intent.putExtra("price", restaurant.price);
                intent.putExtra("type", restaurant.foodType);
                intent.putExtra("serving", restaurant.servingMethod);
                intent.putExtra("atmosphere", restaurant.atmosphere);

                con.startActivity(intent);
            }
        });

        return row;
    }
}
