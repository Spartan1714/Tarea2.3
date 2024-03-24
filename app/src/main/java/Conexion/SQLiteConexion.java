package Conexion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteConexion extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "image_db";
    private static final String TABLE_NAME = "images";
    private static final String COLUMN_IMAGE = "image_data";

    public SQLiteConexion(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_IMAGE + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertImage(byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE, imageBytes);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
    public List<byte[]> getAllImages() {
        List<byte[]> imageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT image_data FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                byte[] imageData = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                imageList.add(imageData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return imageList;
    }


}
