package ca.uoit.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

import ca.uoit.project.database.Restaurant;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private List<Restaurant> restaurants;
    private Restaurant restaurant;
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

        TextView resName = (TextView)row.findViewById(R.id.resName);
        TextView resAddress = (TextView)row.findViewById(R.id.resAddress);
        TextView resType = (TextView)row.findViewById(R.id.resType);
        TextView resPrice = (TextView)row.findViewById(R.id.resPrice);
        TextView resAtmosphere = (TextView)row.findViewById(R.id.resAtmosphere);
        TextView resServing = (TextView)row.findViewById(R.id.resServing);

        restaurant = restaurants.get(position);

        resName.setText("Name: " + restaurant.name);
        resAddress.setText("Address: " + restaurant.location);
        resType.setText("Food type: " + restaurant.foodType);
        resPrice.setText("Average price: $" + restaurant.price);
        resAtmosphere.setText("Atmosphere: " + restaurant.atmosphere.description);
        resServing.setText("Serving Method: " + restaurant.servingMethod.description);

        return row;

        //return super.getView(position, convertView, parent);
    }
}
