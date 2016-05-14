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

    // Table Name - transaction
    private static final String TABLE_TRANSACTION = "_transaction";

    // Columns Names of transaction Table
    private static final String KEY_TRANSACTION_ID = "transaction_id";
    private static final String KEY_TRANSACTION_TYPE = "type";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_TICKET_QUANTITY = "ticket_quantity";
    private static final String KEY_TOTAL_AMOUNT = "total_amount";

    // Table Name - flight
    private static final String TABLE_FLIGHT = "flight";

    // Column Names of transaction Table
    private static final String KEY_FLIGHT_NUMBER = "flight_number";
    private static final String KEY_DEPARTURE = "departure_location";
    private static final String KEY_ARRIVAL = "arrival_location";
    private static final String KEY_DEPARTURE_TIME = "departure_time";
    private static final String KEY_CAPACITY = "flight_capacity";
    private static final String KEY_TICKET_PRICE = "price";

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

        /*
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
        */

        // SQL statement to create a table called "transaction"
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE IF NOT EXISTS _transaction( " +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT," +
                "username TEXT, " +
                "flight_number TEXT, " +
                "departure_location TEXT, " +
                "arrival_location TEXT, " +
                "ticket_quantity INTEGER, " +
                "total_amount REAL, " +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP );";

        // SQL statement to create a table called "flight"
        String CREATE_FLIGHT_TABLE = "CREATE TABLE IF NOT EXISTS flight( " +
                "flight_number TEXT PRIMARY KEY, " +
                "arrival_location TEXT, " +
                "departure_location TEXT, " +
                "flight_capacity INTEGER, " +
                "departure_time TEXT, " +
                "price REAL);";

        // execute an SQL statement to create the user,
        // reservation, and transaction table
        db.execSQL(CREATE_USER_TABLE);

        // execute an SQL statement to create the reservation table
        //db.execSQL(CREATE_RESERVATION_TABLE);

        // execute an SQL statement to create the transaction table
        db.execSQL(CREATE_TRANSACTION_TABLE);

        // execute an SQL statement to create the transaction table
        db.execSQL(CREATE_FLIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older user, reservation, and transaction table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS transaction");
        db.execSQL("DROP TABLE IF EXISTS flight");

        // create fresh user, reservation, and transaction table
        this.onCreate(db);
    }

    //-----------------------------------------------------------------

    public boolean isUser(String username) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //Log.d("isUser", "Username: " + username);
        String query = "SELECT username FROM " + TABLE_USER + " WHERE username = ?;";
        String[] whereArgs = {username};
        Cursor cursor = db.rawQuery(query, whereArgs);

        //Log.d("count", ""+ cursor.getCount() );
        return (cursor.getCount() == 1);
    }

    public void addUser(User user) {
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

    public void deleteUser(String username) {

        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "username = ?";
        String[] whereArgs = {username};


        // Insert values into database
        db.delete(TABLE_USER, whereClause, whereArgs);
    }

    public void deleteAllUsers() {
        HashMap<String, User> users = getUsers();
        for (String key : users.keySet() ) {
            deleteUser(users.get(key).getUsername() );
        }
    }

    // -----------------------------------------------------------------------------------

    public Transaction getTransaction(Integer id) {

        String query = "SELECT * FROM _transaction WHERE id = ?";
        String[] whereArgs = {id.toString()};

        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        Transaction transaction = new Transaction();

        // Retrieve content from query
        if (cursor.moveToFirst() ) {
            do {
                // Get the information from the database
                transaction.setId(cursor.getInt(0) );
                transaction.setUsername(cursor.getString(1) );
                transaction.setFlightNumber(cursor.getString(2) );
                transaction.setDepartureLocation(cursor.getString(3) );
                transaction.setArrivalLocation(cursor.getString(4) );
                transaction.setTicketQuantity(cursor.getInt(5) );

            } while (cursor.moveToNext() );
        }

        Log.d("transaction", "Id" + transaction.getId().toString() );
        Log.d("transaction", "Username: " + transaction.getUsername() );
        Log.d("transaction", "Flight Number: " + transaction.getFlightNumber() );
        Log.d("transaction", "Departure Location: " + transaction.getDepartureLocation() );
        Log.d("transaction", "Arrival Location: " + transaction.getArrivalLocation() );
        Log.d("transaction", "Ticket Quantity: " + transaction.getTicketQuantity().toString() );

        return null;
    }

    public ArrayList<Transaction> getTransactions(String type) {
       ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        // Get writeable database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM " + TABLE_TRANSACTION + " ";

        if (type.isEmpty() ) {
            cursor = db.rawQuery(query, null);
        }
        else {
            query += " WHERE type = ? ";
            String[] whereArgs = {type};
            cursor = db.rawQuery(query, whereArgs);
        }

        query += " ORDER BY time desc;";

        // Retrieve content from query
        if (cursor.moveToFirst() ) {
            do {
                Transaction transaction = new Transaction();

                // Get the information from the database
                transaction.setId(cursor.getInt(0));
                transaction.setUsername(cursor.getString(1));
                transaction.setFlightNumber(cursor.getString(2));
                transaction.setDepartureLocation(cursor.getString(3));
                transaction.setArrivalLocation(cursor.getString(4));
                transaction.setTicketQuantity(cursor.getInt(5));

                transaction.setTime(cursor.getString(6) );

                transactions.add(transaction);

            } while (cursor.moveToNext() );
        }

        // Return reservations
        return transactions;
    }

    public void addTransaction(Transaction transaction) {

        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create content values
        ContentValues values = new ContentValues();

        // Set the content values based on the type of transaction
        if (transaction.getType().equals("New Account") ) {
            values.put(KEY_TRANSACTION_TYPE, transaction.getType());
            values.put(KEY_USERNAME, transaction.getUsername());
        }
        else {
            values.put(KEY_TRANSACTION_TYPE, transaction.getType());
            values.put(KEY_USERNAME, transaction.getUsername());
            values.put(KEY_FLIGHT_NUMBER, transaction.getFlightNumber());
            values.put(KEY_DEPARTURE, transaction.getDepartureLocation());
            values.put(KEY_ARRIVAL, transaction.getArrivalLocation());
            values.put(KEY_TICKET_QUANTITY, transaction.getTicketQuantity());
            values.put(KEY_TOTAL_AMOUNT, transaction.getTotalAmount());
        }

        // Insert the values into the transaction table
        db.insert(TABLE_TRANSACTION, null, values);
    }

    public void updateTransaction(Transaction transaction) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create content values
        ContentValues values = new ContentValues();

        // Set the content values based on the type of transaction
        if (transaction.getType().equals("New Account") ) {
            values.put(KEY_TRANSACTION_TYPE, transaction.getType());
            values.put(KEY_USERNAME, transaction.getUsername());
        }
        else {
            values.put(KEY_TRANSACTION_TYPE, transaction.getType());
            values.put(KEY_USERNAME, transaction.getUsername());
            values.put(KEY_FLIGHT_NUMBER, transaction.getFlightNumber());
            values.put(KEY_DEPARTURE, transaction.getDepartureLocation());
            values.put(KEY_ARRIVAL, transaction.getArrivalLocation());
            values.put(KEY_TICKET_QUANTITY, transaction.getTicketQuantity());
            values.put(KEY_TOTAL_AMOUNT, transaction.getTotalAmount());
        }

        String whereClause = "WHERE " + KEY_TRANSACTION_ID + " = ?";
        String[] whereArgs = {transaction.getId().toString()};

        // Insert the values into the transaction table
        db.update(TABLE_TRANSACTION,values, whereClause, whereArgs);
    }

    // -----------------------------------------------------------
    /*
    private Integer flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private Integer flightCapacity;
    private Double price;
     */
    public void addFlight(Flight flight) {
        // Get writeable database
        SQLiteDatabase db = getWritableDatabase();

        // Create content values
        ContentValues values = new ContentValues();
        values.put(KEY_FLIGHT_NUMBER, flight.getFlightNumber() );
        values.put(KEY_DEPARTURE, flight.getDepartureLocation() );
        values.put(KEY_ARRIVAL, flight.getArrivalLocation() );
        values.put(KEY_DEPARTURE_TIME, flight.getDepartureTime() );
        values.put(KEY_CAPACITY, flight.getFlightCapacity() );
        values.put(KEY_TICKET_PRICE, flight.getPrice() );

        db.insert(TABLE_FLIGHT, null, values);
    }

    public boolean isFlight(String flightNumber) {
        // Get writeable database
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT flight_number FROM " + TABLE_FLIGHT +
                       " WHERE flight_number = ? ";
        String[] whereArgs = {flightNumber};
        Cursor cursor = db.rawQuery(query, whereArgs);

        return (cursor.getCount() == 1);
    }

    public ArrayList<Flight> getFlights() {

        return null;
    }
}
