package klippe.dev.himselfgo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by user on 17.01.2017.
 */

public class DbHelper  extends SQLiteOpenHelper implements BaseColumns{

    public static final String DATABASE_NAME = "himself.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "rebus";
    public static final String LONGTITUDE = "longtitude";
    public static final String LATITUDE = "latitude";
    public static final String STEP = "step";
    public static final String SRC = "src";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE_NAME + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + LONGTITUDE + " real not null, "
            + LATITUDE + " real not null, "
            + STEP + " integer not null, "
            + SRC + " text not null);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
