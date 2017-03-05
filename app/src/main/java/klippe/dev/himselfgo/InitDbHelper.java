package klippe.dev.himselfgo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import klippe.dev.himselfgo.db.DbHelper;

/**
 * Created by user on 05.03.2017.
 */

public class InitDbHelper {

    public static DbHelper mDbHelper;
    public static SQLiteDatabase sdb;
    public Context context;
    public String name_db;
    public int version;

    public InitDbHelper(Context context, String name_db, int version) {
        this.context = context;
        this.name_db = name_db;
        this.version = version;
        mDbHelper = new DbHelper(context, name_db, null, version);
        sdb = mDbHelper.getWritableDatabase();
    }
}
