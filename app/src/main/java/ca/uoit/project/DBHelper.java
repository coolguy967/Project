package ca.uoit.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "d";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "d";
    private static final String COL_1 = "id";
    private static final String COL_2 = "";
    private static final String COL_3 = "";
    private static final String COL_4 = "";
    private static final String COL_5 = "";
    private static final String COL_6 = "";

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
        db.execSQL("Drop Table " + TABLE_NAME + ";");
        this.onCreate(db);
    }
}
