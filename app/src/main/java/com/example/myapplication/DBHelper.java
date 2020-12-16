package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Body";
    public static final String ID = "id";
    public static final String HEIGHT = "height";
    public static final String FAT = "fat";
    public static final String WEIGHT = "weight";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Body ( _id INTEGER PRIMARY KEY" +
                " AUTOINCREMENT,ID text, Height text, Fat text, Weight text);" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion){
        db.execSQL("DROP TABLE IF EXISTS Body");
        onCreate(db);
    }
    public boolean insert(String id, String height, String fat, String weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(HEIGHT, height);//"필드명", 데이터
        contentValues.put(FAT, fat);
        contentValues.put(WEIGHT, weight);
        db.insert("Body",null, contentValues);
        return true;
    }
    public Cursor getData(String id){ // int형이 맞는걸까
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Body where id = " + id + ";", null);//퍽킹
        res.moveToFirst();
        return res;
    }
    public boolean update (String id, String height, String fat, String weight ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("height", height);//"필드명", 데이터
        contentValues.put("fat", fat);
        contentValues.put("weight", weight);
        //db.update("Body",contentValues,"id = ? ", new String[] {Integer.toString(id)});
        db.execSQL("UPDATE Body SET HEIGHT = " + height + ", FAT = " + fat + ", WEIGHT = " + weight+";");
        return true;
    }
}