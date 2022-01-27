package edu.ktu.savedatedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String WORK_TABLE = "WORK_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_DATE = "DATE";

    public static final String CREATE_TABLE_WORK = "CREATE TABLE IF NOT EXISTS " + WORK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DATE + " TEXT )";

    public Database(@Nullable Context context) {
        super(context, "trainingDate.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORK);
    }


    public boolean addWork(WorkModel workModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, workModel.getTitle());
        contentValues.put(COLUMN_DATE, workModel.getDate().toString());

        long insert = db.insert(WORK_TABLE, null, contentValues);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + WORK_TABLE);
        onCreate(db);
    }
}
