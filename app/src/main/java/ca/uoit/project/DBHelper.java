package ca.uoit.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

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
                COL2 + "TEXT" +
                COL3 + "TEXT" +
                COL4 + "TEXT" +
                COL5 + "TEXT" +
                COL6 + "TEXT" +
                COL7 + "TEXT)";
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

}
