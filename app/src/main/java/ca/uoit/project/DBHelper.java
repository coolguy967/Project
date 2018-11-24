package ca.uoit.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.uoit.project.enumeration.Atmosphere;
import ca.uoit.project.enumeration.ServingMethod;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DBHelper";
    private static final String TABLE_NAME = "Restaurants";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Address";
    private static final String COL4 = "FoodType";
    private static final String COL5 = "Price";
    private static final String COL6 = "Atmosphere";
    private static final String COL7 = "ServingMethod";

    public DBHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " INT," +
                COL6 + " INT," +
                COL7 + " INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

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
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Retrieve a list of restaurants based on the given search criteria
     * @param foodType A type of food that the restaurant must serve
     * @param price The maximum price the user is willing to spend on a meal
     * @param atmosphere The desired atmosphere of the restaurant
     * @param servingMethod The serving method of the restaurant
     * @return A list of restaurants that match the search criteria
     */
    public List<Restaurant> findRestaurants(String foodType, double price, Atmosphere atmosphere, ServingMethod servingMethod) {
        ArrayList<Restaurant> restaurantLs = new ArrayList<Restaurant>();

        // Start with SELECT statement
        String query = "SELECT * FROM " + TABLE_NAME;

        // Keep track of whether the WHERE keyword has been put in the query yet
        boolean where = false;

        if(!foodType.isEmpty()) {
            // Add foodType condition
            query += " WHERE " + COL4 + " LIKE " + "\"%" + foodType + "%\"";

            // The WHERE statement is now in the query
            where = true;
        }

        if(price != 0) {
            if(!where) {
                // WHERE statement is not yet in the query, insert it before price condition
                query += " WHERE ";
            } else {
                // WHERE is already in the query, add AND before price condition
                query += " AND ";
            }

            // Add price condition
            query += COL5 + " <= " + price;
        }

        // Keep track of whether the ORDER BY statement has been put in the query yet
        boolean orderBy = false;

        if(atmosphere != Atmosphere.ANY) {
            // Add the atmosphere condition
            query += " ORDER BY ABS(" + COL6 + " - " + atmosphere.value + ") ASC";

            // The ORDER BY statement is now in the query
            orderBy = true;
        }

        if(servingMethod != ServingMethod.ANY) {
            if(orderBy) {
                // ORDER BY statement is not yet in the query, insert it before servingMethod condition
                query += " ORDER BY ";
            } else {
                // ORDER BY is already in the query, add a comma before servingMethod condition
                query += ", ";
            }

            // Add servingMethod condition
            query += "ABS(" + COL7 + " - " + servingMethod.value + ") ASC";
        }

        // Run the query on the database
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);

        // Return the resulting list of restaurants
        return restaurantLs;
    }

}
