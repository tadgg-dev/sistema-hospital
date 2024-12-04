package com.example.sistema_hospitalario.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {
    private static DataBaseManager instance;
    private static final String DATABASE_NAME = "DB_Hospital";
    private static final int DATABASE_VERSION = 1;

    private DataBaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseManager getInstance(Context context) {
        if (instance == null) instance = new DataBaseManager(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (firstName TEXT NOT NULL, lastName TEXT NOT NULL, dni TEXT NOT NULL, userName TEXT NOT NULL, email TEXT UNIQUE NOT NULL, password TEXT NOT NULL, medical_license INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE patient ( firstName TEXT NOT NULL, lastName TEXT NOT NULL, dni TEXT NOT NULL, email TEXT UNIQUE NOT NULL, user_medical_code INTEGER NOT NULL, dateOfBirth TEXT NOT NULL, gender TEXT NOT NULL, phone TEXT NOT NULL, address TEXT NOT NULL, doctor_email INTEGER, FOREIGN KEY (doctor_email) REFERENCES user(email) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS patient");
        onCreate(db);
    }
}
