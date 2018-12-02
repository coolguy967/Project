package ca.uoit.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String TABLE_NAME = "Restaurants";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Location";
    private static final String COL4 = "FoodType";
    private static final String COL5 = "Price";
    private static final String COL6 = "Atmosphere";
    private static final String COL7 = "ServingMethod";
    private static final String COL8 = "Favourite";

    private static final Map<String, Integer> foodTypeFavourites = new HashMap<String, Integer>();
    private static final Map<String, Integer> locationfavourites = new HashMap<String, Integer>();
    private static final Map<String, Integer> atmosphereFavourites = new HashMap<String, Integer>();

    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Database
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " INT," +
                COL6 + " INT," +
                COL7 + " INT," +
                COL8 + " INT)";
        db.execSQL(createTable);
    }

    /**
     * Add restaurants to database if the database is empty
     */
    public void initDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

        addRestaurant("Subway", "1769 Western Crescent", "Sandwiches", 10, Atmosphere.FAST_FOOD, ServingMethod.ANY);
        addRestaurant("Bang Bang Burrito", "1769 Western Crescent", "Burgers", 10, Atmosphere.FAST_FOOD, ServingMethod.ANY);
        addRestaurant("Mc Donald's", "1769 Western Crescent", "Burgers", 5, Atmosphere.FAST_FOOD, ServingMethod.ANY);
        addRestaurant("Starbucks", "1769 Western Crescent", "Coffee", 10, Atmosphere.FAST_FOOD, ServingMethod.ANY);
        addRestaurant("Tim Hortons", "1769 Western Crescent", "Coffee", 5, Atmosphere.FAST_FOOD, ServingMethod.ANY);
        addRestaurant("Swiss Chalet", "1769 Western Crescent", "Italian", 20, Atmosphere.CASUAL, ServingMethod.ANY);

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (c.moveToNext()) {
            // Get the data from the cursor
            int _id = c.getInt(0);
            String _name = c.getString(1);
            String _location = c.getString(2);
            String _foodType = c.getString(3);
            int _price = c.getInt(4);
            int _atmosphere = c.getInt(5);
            int _servingMethod = c.getInt(6);
            boolean _favourite = c.getInt(7) == 1;

            // Populate the restaurant
            Restaurant restaurant = new Restaurant(_id, _name, _location, _foodType, _price, Atmosphere.get(_atmosphere), ServingMethod.get(_servingMethod), _favourite);

            // Update the favourites map if the restaurant is a favourite
            if(restaurant.favourite) {
                updateFavourites(restaurant);
            }
        }
    }

    /**
     * Increment or decrement the favourite values based on the passed restaurant
     * @param restaurant The restaurant to get the values of
     */
    private void updateFavourites(Restaurant restaurant) {
        // If the restaurant is a favourite
        if(restaurant.favourite) {
            // Increment the atmosphere
            atmosphereFavourites.put(restaurant.atmosphere.description,
                    atmosphereFavourites.getOrDefault(restaurant.atmosphere.description, 0) + 1);
            // Increment the location
            locationfavourites.put(restaurant.location,
                    locationfavourites.getOrDefault(restaurant.location, 0) + 1);

            // Increment all food types
            String[] foodTypes = restaurant.foodType.split(", ");

            for(String foodType : foodTypes) {
                foodTypeFavourites.put(foodType, foodTypeFavourites.getOrDefault(foodType, 0) + 1);
            }
        } else {
            // Decrement the atmosphere
            atmosphereFavourites.put(restaurant.atmosphere.description,
                    atmosphereFavourites.getOrDefault(restaurant.atmosphere.description, 0) - 1);
            // Decrement the location
            locationfavourites.put(restaurant.location,
                    locationfavourites.getOrDefault(restaurant.location, 0) - 1);

            // Decrement all food types
            String[] foodTypes = restaurant.foodType.split(", ");

            for(String foodType : foodTypes) {
                foodTypeFavourites.put(foodType, foodTypeFavourites.getOrDefault(foodType, 0) - 1);
            }
        }
    }

    /**
     * Toggle the favourite value of this restaurant
     * @param restaurant The restaurant to either favourite or unfavourite
     * @return True if successful
     */
    public boolean toggleFavorite(Restaurant restaurant) {

        // Toggle favourite value
        restaurant.favourite = !restaurant.favourite;

        updateFavourites(restaurant);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add restaurant to contentValues
        contentValues.put(COL1, restaurant.id);
        contentValues.put(COL2, restaurant.name);
        contentValues.put(COL3, restaurant.location);
        contentValues.put(COL4, restaurant.foodType);
        contentValues.put(COL5, restaurant.price);
        contentValues.put(COL6, restaurant.atmosphere.value);
        contentValues.put(COL7, restaurant.servingMethod.value);
        contentValues.put(COL8, restaurant.favourite);

        // Update restaurant
        long result = db.update(TABLE_NAME, contentValues, "WHERE", new String[] { COL1 + " = " + restaurant.id});

        return result != -1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Add a restaurant to the database
     *
     * @param restaurant The restaurant to add to the database
     * @return true if the insert statement succeeded
     */
    public boolean addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add restaurant to contentValues
        contentValues.put(COL2, restaurant.name);
        contentValues.put(COL3, restaurant.location);
        contentValues.put(COL4, restaurant.foodType);
        contentValues.put(COL5, restaurant.price);
        contentValues.put(COL6, restaurant.atmosphere.value);
        contentValues.put(COL7, restaurant.servingMethod.value);

        // Insert new restaurant
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /**
     * Add a restaurant to the database
     *
     * @param name          The name of the restaurant
     * @param location       The location of the restaurant
     * @param foodType      The types of food served at the restaurant (e.g. "Pizza, Sushi, Italian")
     * @param price         The minimum price for a drink and a main course at this restaurant
     * @param atmosphere    The Atmosphere of this restaurant
     * @param servingMethod The serving method of this restaurant
     * @return true if the insert statement succeeded
     */
    public boolean addRestaurant(String name, String location, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add passed values to contentValues
        contentValues.put(COL2, name);
        contentValues.put(COL3, location);
        contentValues.put(COL4, foodType);
        contentValues.put(COL5, price);
        contentValues.put(COL6, atmosphere.value);
        contentValues.put(COL7, servingMethod.value);

        // Insert new restaurant
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns the String key that has the highest value in the Map
     * @param map The map to find the favourite in
     * @return The String key that has the highest value
     */
    private String getFavourite(Map<String, Integer> map) {
        // Get an iterator for all keys
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();

        // Assume default value is "Any"
        String favourite = "Any";
        int max = 0;

        // Iterate through each key and find the largest value
        while(iterator.hasNext()) {
            String key = iterator.next();

            int value = map.get(key);

            if(value > max) {
                favourite = key;
                max = value;
            }
        }

        // Return the favourite option
        return favourite;
    }

    /**
     * Retrieve a list of restaurants based on the given search criteria
     *
     * @param foodType      A type of food that the restaurant must serve
     * @param price         The maximum price the user is willing to spend on a meal
     * @param atmosphere    The desired atmosphere of the restaurant
     * @param servingMethod The serving method of the restaurant
     * @return A list of restaurants that match the search criteria
     */
    public List<Restaurant> findRestaurants(String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        ArrayList<Restaurant> restaurantLs = new ArrayList<Restaurant>();

        // Start with SELECT statement
        String query = "SELECT * FROM " + TABLE_NAME;

        // Keep track of whether the WHERE keyword has been put in the query yet
        boolean where = false;

        if (!foodType.isEmpty() && !foodType.equals("Any")) {
            // Add foodType condition
            query += " WHERE " + COL4 + " LIKE " + "\"%" + foodType + "%\"";

            // The WHERE statement is now in the query
            where = true;
        }

        if (price != 0) {
            if (!where) {
                // WHERE statement is not yet in the query, insert it before price condition
                query += " WHERE ";

                // The WHERE statement is now in the query
                where = true;
            } else {
                // WHERE is already in the query, add AND before price condition
                query += " AND ";
            }

            // Add price condition
            query += COL5 + " <= " + price;
        }

        if (servingMethod != ServingMethod.ANY) {
            if (!where) {
                // WHERE statement is not yet in the query, insert it before servingMethod condition
                query += " WHERE ";
            } else {
                // WHERE is already in the query, add AND before servingMethod condition
                query += " AND ";
            }

            // Add servingMethod condition
            query += COL7 + " = " + servingMethod.value + " OR " + COL7 + " = " + servingMethod.ANY.value;
        }

        if (atmosphere != Atmosphere.ANY) {
            // Add the atmosphere condition
            query += " ORDER BY ABS(" + COL6 + " - " + atmosphere.value + ") ASC";
        }

        System.out.println(query);

        // Run the query on the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            // Get the data from the cursor
            int _id = c.getInt(0);
            String _name = c.getString(1);
            String _location = c.getString(2);
            String _foodType = c.getString(3);
            int _price = c.getInt(4);
            int _atmosphere = c.getInt(5);
            int _servingMethod = c.getInt(6);
            boolean _favourite = c.getInt(7) == 1;

            // Populate the restaurant list with this database record
            Restaurant restaurant = new Restaurant(_id, _name, _location, _foodType, _price, Atmosphere.get(_atmosphere), ServingMethod.get(_servingMethod), _favourite);
            restaurantLs.add(restaurant);
        }

        // Sort by atmosphere based on favourite atmosphere
        final Atmosphere favAtmosphere = Atmosphere.fromString(getFavourite(atmosphereFavourites));
        restaurantLs.sort(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                if(r1.atmosphere == favAtmosphere) {
                    return -1;
                }
                return 0;
            }
        });

        // Sort by food type based on favourite food types
        final String favFoodType = getFavourite(foodTypeFavourites);
        restaurantLs.sort(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                if(r1.foodType.contains(favFoodType)) {
                    return -1;
                }
                return 0;
            }
        });

        // Sort by location based on favourite location
        final String favLocation = getFavourite(locationfavourites);
        restaurantLs.sort(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                if(r1.location.equals(favLocation)) {
                    return -1;
                }
                return 0;
            }
        });

        // Return the resulting list of restaurants
        return restaurantLs;
    }

}
