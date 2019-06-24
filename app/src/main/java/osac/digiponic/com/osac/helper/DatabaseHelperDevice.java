package osac.digiponic.com.osac.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataBluetoothDevice;

public class DatabaseHelperDevice extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "osac_db";

    public DatabaseHelperDevice(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBluetoothDevice.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
}
