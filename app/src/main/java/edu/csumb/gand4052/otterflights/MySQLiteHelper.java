package edu.csumb.gand4052.otterflights;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by elgandara on 5/11/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Name - ReservationSystem
    private static final String DATABASE_NAME = "ReservationSystem";

    // Table Name - user
    private static final String TABLE_USER = "user";

    // Columns Names of user Table
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ACCOUNT_TYPE = "account_type";
    private static final String KEY_LAST_LOGIN = "last_login";

    // Table Name - reservation
    private static final String TABLE_RESERVATION = "reservation";

    // Columns Names of reservation Table
    private static final String KEY_RESERVATION_ID = "reservation_id";
    //private static final String KEY_USERNAME = "username"; <-- Foreign key
    private static final String KEY_FLIGHT_NUMBER = "flight_number";
    private static final String KEY_DEPARTURE = "departure_location";
    private static final String KEY_ARRIVAL = "arrival_location";
    private static final String KEY_TICKET_QUANTITY = "ticket_quantity";
    private static final String KEY_TOTAL_AMOUNT = "total_amount";

    // Table Name - transaction
    private static final String TABLE_TRANSACTION = "transaction";

    // Columns Names of transaction Table
    private static final String KEY_TRANSACTION_ID = "transaction_id";
    private static final String KEY_TRANSACTION_TYPE = "transaction_type";
    //private static final String KEY_RESERVATION_ID = "reservation_id";
    //private static final String KEY_USERNAME = "username" <-- Foreign key
    //private static final String KEY_FLIGHT_NUMBER = "flight_number"; <-- Foreign key
    //private static final String KEY_DEPARTURE = "departure_location";
    //private static final String KEY_ARRIVAL = "arrival_location";
    //private static final String KEY_TICKET_QUANTITY = "ticket_quantity";
    private static final String KEY_TIMESTAMP = "timestamp";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Log TAG for debugging purpose
    private static final String TAG = "SQLiteAppLog";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create a table called "user"
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user( " +
                "username TEXT PRIMARY KEY, " +
                "password TEXT, "+
                "account_type TEXT, " +
                "last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP );";

        // SQL statement to create a table called "reservation"
        String CREATE_RESERVATION_TABLE = "CREATE TABLE IF NOT EXISTS reservation( " +
                "reservation_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "flight_number TEXT, " +
                "departure_location TEXT, " +
                "arrival_location TEXT, " +
                "ticket_quantity INTEGER, " +
                "total_amount REAL, " +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP );";

        // SQL statement to create a table called "transaction"
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS transaction( " +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transaction_type TEXT, " +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP );";

        // execute an SQL statement to create the user,
        // reservation, and transaction table
        db.execSQL(CREATE_USER_TABLE);

        // execute an SQL statement to create the reservation table
        db.execSQL(CREATE_RESERVATION_TABLE);

        // execute an SQL statement to create the transaction table
        //db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older user, reservation, and transaction table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS transaction");

        // create fresh user, reservation, and transaction table
        this.onCreate(db);
    }

    //-----------------------------------------------------------------

    public boolean isUser(String username) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //Log.d("isUser", "Username: " + username);
        String query = "SELECT username FROM " + TABLE_USER + " WHERE username = ? ";
        String[] whereArgs = {username};
        Cursor cursor = db.rawQuery(query, whereArgs);

        //Log.d("count", ""+ cursor.getCount() );
        return (cursor.getCount() == 1);
    }

    public void addUser(User user) {
        //Log.d(TAG, "addUser() - " + user.toString());

        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create content values
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername() );
        values.put(KEY_PASSWORD, user.getPassword() );
        values.put(KEY_ACCOUNT_TYPE, user.getAccountType() );

        // Insert values into database
        db.insert(TABLE_USER, null, values);
    }

    // Return a user with the matching username
    public User getUser(String username) {
        User user = new User();

        // SQL query
        String query = "SELECT * FROM " + TABLE_USER +
                       " WHERE username = ? ";
        String[] whereArgs = {username};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        // Retrieve content from query
        if (cursor.moveToFirst() ) {
            do {
                user.setUsername(cursor.getString(0) );
                user.setPassword(cursor.getString(1) );
                user.setAccountType(cursor.getString(2) );

            } while (cursor.moveToNext() );
        }

        // Return user
        return user;
    }

    // Return the user info in a HashMap
    public HashMap<String, User> getUsers() {
        HashMap<String, User> users = new HashMap<String, User>();

        // SQL query
        String query = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Retrieve content from query
        if (cursor.moveToFirst() ) {
            do {
                User user = new User();

                user.setUsername(cursor.getString(0) );
                user.setPassword(cursor.getString(1) );
                user.setAccountType(cursor.getString(2) );

                users.put(user.getUsername(), user);
            } while (cursor.moveToNext() );
        }

        // Return user
        return users;
    }

    public void removeUser(String username) {

        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "username = ?";
        String[] whereArgs = {username};


        // Insert values into database
        db.delete(TABLE_USER, whereClause, whereArgs);
    }

    // -----------------------------------------------------------------------------------
    /*
    private Integer id;
    private String username;
    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private Integer ticketQuantity;
    private String time;
     */

    public Reservation getReservation(Integer id) {

        String query = "SELECT * FROM reservation WHERE id = ?";
        String[] whereArgs = {id.toString()};

        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        Reservation reservation = new Reservation();

        // Retrieve content from query
        if (cursor.moveToFirst() ) {
            do {


            } while (cursor.moveToNext() );
        }


        return null;
    }



}
