package com.taekung.nady.el_3yada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/20/2016.
 */
public class Database extends SQLiteOpenHelper {
    // const database name
    private final static String DB_NAME = "hospital.db";
    //const user table and columns
    private final static String DB_TABLE_USER = "users";
    private final static String USER_COL_USERNAME = "username";
    private final static String USER_COL_NAME = "name";
    private final static String USER_COL_AVATAR = "avatar";
    private final static String USER_COL_PASS = "pass";
    //const patient table and columns
    private final static String DB_TABLE_PATIENT = "patient";
    public final static String PATIENT_COL_ID = "id"; //INTEGER
    public final static String PATIENT_COL_NAME = "name"; //TEXT
    public final static String PATIENT_COL_EMAIL = "email"; //TEXT
    public final static String PATIENT_COL_TEL = "tel"; //TEXT
    public final static String PATIENT_COL_DATE_OF_ARRIVAL = "date_of_arrival"; //TEXT
    public final static String PATIENT_COL_TIME_OF_ARRIVAL = "time_of_arrival"; //TEXT
    public final static String PATIENT_COL_DISEASE = "disease"; //TEXT
    public final static String PATIENT_COL_MEDICATION = "medication"; //TEXT
    public final static String PATIENT_COL_COST = "cost"; //REAL

    // SQL Query to create the user table
    private final static String SQL_USER_CREATE = "CREATE TABLE %s (" +
            " %s TEXT NOT NULL PRIMARY KEY," + //username
            " %s TEXT NOT NULL," + //name
            " %s BLOB," + //avatar
            " %s TEXT NOT NULL );"; //pass

    // SQL Query to create the patient table
    private final static String SQL_PATIENT_CREATE = "CREATE TABLE %s (" +
            " %s TEXT NOT NULL," + // username
            " %s INTEGER PRIMARY KEY AUTOINCREMENT," + // id
            " %s TEXT NOT NULL ," + // name
            " %s TEXT UNIQUE," + //email
            " %s TEXT NOT NULL," + //tel
            " %s TEXT," + //date_of_arrival
            " %s TEXT," + //time_of_arrival
            " %s TEXT NOT NULL," + //disease
            " %s TEXT NOT NULL," + //medication
            " %s REAL NOT NULL );"; //cost

    // SQL Query to drop the table
    private final static String SQL_TABLE_DROP = "DROP TABLE %s IF EXISTS ;";


    //db instances for read and write in database
    SQLiteDatabase db_read, db_write;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        db_write = getWritableDatabase();
        db_read = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table users
        db.execSQL(String.format(
                //SQL QUERY
                SQL_USER_CREATE,
                //TABLE NAME
                DB_TABLE_USER,
                //TABLE COLUMNS
                USER_COL_USERNAME,
                USER_COL_NAME,
                USER_COL_AVATAR,
                USER_COL_PASS));

        //creating table patient
        db.execSQL(String.format(
                //SQL QUERY
                SQL_PATIENT_CREATE,
                //TABLE NAME
                DB_TABLE_PATIENT,
                //TABLE COLUMNS
                USER_COL_USERNAME,
                PATIENT_COL_ID,
                PATIENT_COL_NAME,
                PATIENT_COL_EMAIL,
                PATIENT_COL_TEL,
                PATIENT_COL_DATE_OF_ARRIVAL,
                PATIENT_COL_TIME_OF_ARRIVAL,
                PATIENT_COL_DISEASE,
                PATIENT_COL_MEDICATION,
                PATIENT_COL_COST));
    }

    public boolean insertUser(String username, String pass, String name, Bitmap avatar) {
        // filling new user data
        ContentValues data = new ContentValues();
        data.put(USER_COL_USERNAME, username.trim());
        data.put(USER_COL_NAME, name.trim());
        data.put(USER_COL_AVATAR, DB_Image.getBytes(avatar));
        data.put(USER_COL_PASS, pass.trim());
        //inserting new user in the table
        if (db_write.insert(DB_TABLE_USER, null, data) != -1) {
            return true;
        }
        return false;
    }

    public boolean updateUser(String old_username, String username, String pass, String name, Bitmap avatar) {
        // filling new user data
        ContentValues data = new ContentValues();
        data.put(USER_COL_USERNAME, username.trim());
        data.put(USER_COL_NAME, name.trim());
        data.put(USER_COL_AVATAR, DB_Image.getBytes(avatar));
        data.put(USER_COL_PASS, pass.trim());
        //updating new user in the table
        if (db_write.update(DB_TABLE_USER, data, String.format("%s = ?", USER_COL_USERNAME), new String[]{old_username.trim()}) > 0) {
            return true;
        }
        return false;
    }

    public boolean insertPatient(String username ,String name, String email, String tel, String date_of_arrival, String time_of_arrival, String disease, String medication, String cost) {
        // filling new patient data
        ContentValues data = new ContentValues();
        data.put(USER_COL_USERNAME, username.trim());
        data.put(PATIENT_COL_NAME, name.trim());
        data.put(PATIENT_COL_EMAIL, email.trim());
        data.put(PATIENT_COL_TEL, tel.trim());
        data.put(PATIENT_COL_DATE_OF_ARRIVAL, date_of_arrival.trim());
        data.put(PATIENT_COL_TIME_OF_ARRIVAL, time_of_arrival.trim());
        data.put(PATIENT_COL_DISEASE, disease.trim());
        data.put(PATIENT_COL_MEDICATION, medication.trim());
        data.put(PATIENT_COL_COST, Double.parseDouble(cost.trim()));

        //inserting new patient in the table
        if (db_write.insert(DB_TABLE_PATIENT, null, data) != -1) {
            return true;
        }
        return false;
    }

    public boolean updatePatient(String username ,int id, String name, String email, String tel, String date_of_arrival, String time_of_arrival, String disease, String medication, String cost) {
        // filling new patient data
        ContentValues data = new ContentValues();
        data.put(PATIENT_COL_NAME, name.trim());
        data.put(PATIENT_COL_EMAIL, email.trim());
        data.put(PATIENT_COL_TEL, tel.trim());
        data.put(PATIENT_COL_DATE_OF_ARRIVAL, date_of_arrival.trim());
        data.put(PATIENT_COL_TIME_OF_ARRIVAL, time_of_arrival.trim());
        data.put(PATIENT_COL_DISEASE, disease.trim());
        data.put(PATIENT_COL_MEDICATION, medication.trim());
        data.put(PATIENT_COL_COST, Double.parseDouble(cost.trim()));

        //updatinging new patient in the table
        if (db_write.update(DB_TABLE_PATIENT, data, String.format("%s = ? AND %s = ?", PATIENT_COL_ID , USER_COL_USERNAME), new String[]{id + "" , username.trim()}) > 0) {
            return true;
        }
        return false;
    }

    public Patient getPatient(String username, int id) {
        Cursor result = db_read.query(DB_TABLE_PATIENT,
                null,
                String.format("%s = ? AND %s = ?", PATIENT_COL_ID , USER_COL_USERNAME), new String[]{id + "" , username},
                null, null, null);
        while (result.moveToNext()) {
            return new Patient(
                    result.getInt(result.getColumnIndex(PATIENT_COL_ID))       //id
                    , result.getString(result.getColumnIndex(PATIENT_COL_NAME))    //name
                    , result.getString(result.getColumnIndex(PATIENT_COL_EMAIL))    //email
                    , result.getString(result.getColumnIndex(PATIENT_COL_TEL))    //tel
                    , result.getString(result.getColumnIndex(PATIENT_COL_DATE_OF_ARRIVAL))    //date_of_arrival
                    , result.getString(result.getColumnIndex(PATIENT_COL_TIME_OF_ARRIVAL))    //time_of_arrival
                    , result.getString(result.getColumnIndex(PATIENT_COL_DISEASE))    //disease
                    , result.getString(result.getColumnIndex(PATIENT_COL_MEDICATION))    //medication
                    , result.getDouble(result.getColumnIndex(PATIENT_COL_COST))    //cost
            );
        }
        return null;
    }
    public boolean checkPatientExists(String username, String email) {
        Cursor result = db_read.query(DB_TABLE_PATIENT,
                null,
                String.format("%s = ? AND %s = ?", PATIENT_COL_EMAIL , USER_COL_USERNAME), new String[]{email, username},
                null, null, null);
        while (result.moveToNext()) {
            return true;
        }
        return false;
    }
    public ArrayList<Patient> getALlPatient(String username) {
        ArrayList<Patient> list = new ArrayList<>();
            Cursor result = db_read.query(DB_TABLE_PATIENT,
                    null,
                    String.format("%s = ?",USER_COL_USERNAME), new String[]{username},
                    null, null, null);
            while (result.moveToNext()) {
                list.add( new Patient(
                        result.getInt(result.getColumnIndex(PATIENT_COL_ID))       //id
                        , result.getString(result.getColumnIndex(PATIENT_COL_NAME))    //name
                        , result.getString(result.getColumnIndex(PATIENT_COL_EMAIL))    //email
                        , result.getString(result.getColumnIndex(PATIENT_COL_TEL))    //tel
                        , result.getString(result.getColumnIndex(PATIENT_COL_DATE_OF_ARRIVAL))    //date_of_arrival
                        , result.getString(result.getColumnIndex(PATIENT_COL_TIME_OF_ARRIVAL))    //time_of_arrival
                        , result.getString(result.getColumnIndex(PATIENT_COL_DISEASE))    //disease
                        , result.getString(result.getColumnIndex(PATIENT_COL_MEDICATION))    //medication
                        , result.getDouble(result.getColumnIndex(PATIENT_COL_COST))    //cost
                ));
            }
            return list;
        }
 public ArrayList<Patient> searchALlPatientBy(String username , String col , String data) {
        ArrayList<Patient> list = new ArrayList<>();
            Cursor result = db_read.query(DB_TABLE_PATIENT,
                    null,
                    USER_COL_USERNAME +" = ? AND "+col+" LIKE '%"+data+"%'", new String[]{username},
                    null, null, null);
            while (result.moveToNext()) {
                list.add( new Patient(
                        result.getInt(result.getColumnIndex(PATIENT_COL_ID))       //id
                        , result.getString(result.getColumnIndex(PATIENT_COL_NAME))    //name
                        , result.getString(result.getColumnIndex(PATIENT_COL_EMAIL))    //email
                        , result.getString(result.getColumnIndex(PATIENT_COL_TEL))    //tel
                        , result.getString(result.getColumnIndex(PATIENT_COL_DATE_OF_ARRIVAL))    //date_of_arrival
                        , result.getString(result.getColumnIndex(PATIENT_COL_TIME_OF_ARRIVAL))    //time_of_arrival
                        , result.getString(result.getColumnIndex(PATIENT_COL_DISEASE))    //disease
                        , result.getString(result.getColumnIndex(PATIENT_COL_MEDICATION))    //medication
                        , result.getDouble(result.getColumnIndex(PATIENT_COL_COST))    //cost
                ));
            }
            return list;
        }

    public User getUser(String username) {
        Cursor result = db_read.query(DB_TABLE_USER,
                null,
                String.format("%s = ?",
                        USER_COL_USERNAME),
                new String[]{username.trim()},
                null, null, null);
        while (result.moveToNext()) {
            return new User(
                    result.getString(result.getColumnIndex(USER_COL_USERNAME))                       //username
                    , result.getString(result.getColumnIndex(USER_COL_NAME))                     //name
                    , DB_Image.getImage(result.getBlob(result.getColumnIndex(USER_COL_AVATAR)))    //avatar
                    , result.getString(result.getColumnIndex(USER_COL_PASS))                     //pass
            );
        }
        return null;
    }

    public boolean checkUserLogin(String username, String pass) {
        Cursor result = db_read.query(DB_TABLE_USER,
                null,
                String.format("%s = ? AND %s = ?",
                        USER_COL_USERNAME,
                        USER_COL_PASS),
                new String[]{username.trim(), pass.trim()},
                null, null, null);
        while (result.moveToNext()) {
            return true;
        }
        return false;
    }

    public boolean checkUserExists(String username) {
        Cursor result = db_read.query(DB_TABLE_USER,
                null,
                String.format("%s = ?",
                        USER_COL_USERNAME),
                new String[]{username.trim()},
                null, null, null);
        while (result.moveToNext()) {
            return true;
        }
        return false;
    }

    public boolean deletePatient(int id) {
        if (db_write.delete(DB_TABLE_PATIENT, String.format("%s = ?", PATIENT_COL_ID), new String[]{id + ""}) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dropping the tables for upgrading
        db.execSQL(String.format(SQL_TABLE_DROP, DB_TABLE_USER));
        db.execSQL(String.format(SQL_TABLE_DROP, DB_TABLE_PATIENT));
    }
}
