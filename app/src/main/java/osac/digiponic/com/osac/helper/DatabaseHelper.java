package osac.digiponic.com.osac.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataAbout;
import osac.digiponic.com.osac.model.DataBluetoothDevice;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "osac_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBluetoothDevice.CREATE_TABLE);
        db.execSQL(DataAbout.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataAbout.TABLE_NAME));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataBluetoothDevice.TABLE_NAME));
        onCreate(db);
    }

    public void insertDataDevice(DataBluetoothDevice dataBluetoothDevice) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DataBluetoothDevice.TABLE_NAME);
        String insertQuery = "INSERT OR REPLACE INTO " + DataBluetoothDevice.TABLE_NAME + " VALUES ('" +
                dataBluetoothDevice.getMacAddress() + "', '" + dataBluetoothDevice.getName() + "')";
        db.execSQL(insertQuery);
    }

    public List<DataBluetoothDevice> getAllDataDevice() {
        List<DataBluetoothDevice> mDataset = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBluetoothDevice.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                mDataset.add(new DataBluetoothDevice(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());

        }
        return mDataset;
    }

    public void insertDataAbout(DataAbout dataAbout) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT OR REPLACE INTO " + DataAbout.TABLE_NAME + " VALUES (" +
                dataAbout.getId() + ", '" +dataAbout.getNameShort() + "', '" + dataAbout.getNameLong() + "', '" +dataAbout.getPhone_number() + "', '" +
                dataAbout.getAddress() + "')";
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
