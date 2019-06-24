package osac.digiponic.com.osac.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataAbout;

public class DatabaseHelperAbout extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "osac_db";

    public DatabaseHelperAbout(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataAbout.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataAbout.TABLE_NAME));
        onCreate(db);
    }

    public void insertDataAbout(DataAbout dataAbout) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT OR REPLACE INTO " + DataAbout.TABLE_NAME + " VALUES (" +
                dataAbout.getId() + ", '" +dataAbout.getNameShort() + "', '" + dataAbout.getNameLong() + "', '" +dataAbout.getAddress() + "', '" +
                dataAbout.getPhone_number() + "')";
        db.execSQL(insertQuery);
    }

    public List<DataAbout> getAllDataAbout() {
        List<DataAbout> mDataSet = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataAbout.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                mDataSet.add(new DataAbout(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return mDataSet;
    }


}
