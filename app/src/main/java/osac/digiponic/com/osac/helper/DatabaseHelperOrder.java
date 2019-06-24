package osac.digiponic.com.osac.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import osac.digiponic.com.osac.model.DataItemMenu;
import osac.digiponic.com.osac.model.Order;

public class DatabaseHelperOrder extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "osac_db";

    public DatabaseHelperOrder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Order.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Order.TABLE_NAME));
        onCreate(db);
    }

    public void insertData(DataItemMenu dataItemMenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT OR REPLACE INTO " + Order.TABLE_NAME + " VALUES ( " +
                dataItemMenu.get_itemID() + ", '" + dataItemMenu.get_itemName() + "', '" +
                dataItemMenu.get_itemPrice() + "', '" + dataItemMenu.get_itemVehicleType() + "', '" +
                dataItemMenu.get_itemType() + "', '" + dataItemMenu.get_itemImage() + "')";
        db.execSQL(insertQuery);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteAllQuery = "DELETE FROM " + Order.TABLE_NAME;
        db.execSQL(deleteAllQuery);
    }

    public List<DataItemMenu> getAllData() {
        List<DataItemMenu> mDataset = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Order.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
//                mDataset.add(new DataItemMenu(cursor.getString(0), cursor.getString(1),
//                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
//                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        return mDataset;
    }

//    public DataItemMenu getDataByID(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(Order.TABLE_NAME, new String[] {Order.COLUMN_ID, Order.COLUMN_NAME,
//                        Order.COLUMN_PRICE, Order.COLUMN_VEHICLE_TYPE, Order.COLUMN_SERVICE_TYPE, Order.COLUMN_IMAGE}, Order.COLUMN_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return new DataItemMenu(cursor.getString(0), cursor.getString(1),
//                cursor.getString(2), cursor.getString(3), cursor.getString(4),
//                cursor.getString(5));
//    }

    public void deleteDataByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Order.TABLE_NAME, Order.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }



}