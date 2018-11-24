package ca.uoit.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String TABLE_NAME = "Restaurants";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Address";
    private static final String COL4 = "FoodType";
    private static final String COL5 = "Price";
    private static final String COL6 = "Atmosphere";
    private static final String COL7 = "ServingMethod";

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
                COL7 + " INT)";
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
        contentValues.put(COL3, restaurant.address);
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
     * @param address       The address of the restaurant
     * @param foodType      The types of food served at the restaurant (e.g. "Pizza, Sushi, Italian")
     * @param price         The minimum price for a drink and a main course at this restaurant
     * @param atmosphere    The Atmosphere of this restaurant
     * @param servingMethod The serving method of this restaurant
     * @return true if the insert statement succeeded
     */
    public boolean addRestaurant(String name, String address, String foodType, int price, Atmosphere atmosphere, ServingMethod servingMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add passed values to contentValues
        contentValues.put(COL2, name);
        contentValues.put(COL3, address);
        contentValues.put(COL4, foodType);
        contentValues.put(COL5, price);
        contentValues.put(COL6, atmosphere.value);
        contentValues.put(COL7, servingMethod.value);

        // Insert new restaurant
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /*
    public boolean addData(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }*/

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
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
            String _name = c.getString(1);
            String _address = c.getString(2);
            String _foodType = c.getString(3);
            int _price = c.getInt(4);
            int _atmosphere = c.getInt(5);
            int _servingMethod = c.getInt(6);

            // Populate the restaurant list with this database record
            Restaurant restaurant = new Restaurant(_name, _address, _foodType, _price, Atmosphere.get(_atmosphere), servingMethod.get(_servingMethod));
            restaurantLs.add(restaurant);
        }

        // Return the resulting list of restaurants
        return restaurantLs;
    }

}
