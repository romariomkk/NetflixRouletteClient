package com.romariomkk.netflixrouletteclient.dbhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by romariomkk on 29.10.2016.
 */
public class DBHandler extends SQLiteOpenHelper {
    final static int DB_VERSION = 3;
    final static String DB_NAME = "MovieDB";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS MovieTable" +
                "(ID INTEGER,title TEXT UNIQUE,'year' TEXT,director TEXT,rating varchar(4),summary TEXT,image BLOB, PRIMARY KEY(ID ASC, title));");
        Log.i("dbOK", "MovieTable successfully processed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

}
