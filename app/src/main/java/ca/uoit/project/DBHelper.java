package ca.uoit.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "";
    private static final String COL_1 = "id";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + "Integer PRIMARY KEY AUTOINCREMENT" + ");";

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version)
    {

    }
}
