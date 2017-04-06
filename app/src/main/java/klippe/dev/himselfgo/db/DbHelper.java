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
    public static final String TABLE_TASKS = "tasks";
    public static final String QUEST = "quest";
    public static final String NAME_TASK = "nametask";
    public static final String LONGTITUDE = "longtitude";
    public static final String LATITUDE = "latitude";
    public static final String STEP = "step";
    public static final String SRC = "src";
    public static final String TABLE_QUESTS = "quests";
    public static final String NAME_QUEST = "namequest";
    public static final String COUNT_TASKS = "count";
    public static final String COMPLEXITY = "complexity";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE_TASKS + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + QUEST + " text not null, "
            + NAME_TASK + " text not null, "
            + LONGTITUDE + " real not null, "
            + LATITUDE + " real not null, "
            + STEP + " integer not null, "
            + SRC + " text not null);";

    private static final String DATABASE_CREATE_SCRIPT_2 = "create table "
            + TABLE_QUESTS + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + NAME_QUEST + " text not null, "
            + COMPLEXITY + " text not null, "
            + COUNT_TASKS + " integer);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS " + TABLE_TASKS);
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS " + TABLE_QUESTS);
        onCreate(sqLiteDatabase);
    }
}
