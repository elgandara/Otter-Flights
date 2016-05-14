package edu.csumb.gand4052.otterflights;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.HashMap;
import java.util.Set;

public class HomeScreenActivity extends AppCompatActivity implements OnClickListener{

    MySQLiteHelper db = new MySQLiteHelper(this);
    HashMap<String, User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Add OnClickListener to buttons
        Button createAccountButton = (Button) findViewById(R.id.create_account_button);
        Button reservationButton = (Button) findViewById(R.id.reservation_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        Button systemManageButton = (Button) findViewById(R.id.system_manage_button);

        createAccountButton.setOnClickListener(this);
        reservationButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        systemManageButton.setOnClickListener(this);

        initializeDB();
        users = db.getUsers();

        Log.d("space", "-------------------------------------------------");
        logUsers(users.keySet(), users);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.create_account_button) {
            Bundle bundle = new Bundle();
            bundle.putString("action", "create");

            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.reservation_button) {

        }
        else if (id == R.id.cancel_button) {
            Bundle bundle = new Bundle();
            bundle.putString("action", "cancel");

            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.system_manage_button) {
            Bundle bundle = new Bundle();
            bundle.putString("action", "manage");

            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void logUsers(Set<String> keys, HashMap<String, User> users) {
        for (String key : keys) {
            Log.d("user", users.get(key).toString() );
        }
    }

    private void initializeDB() {
        // Create users if they does not already exist
        if (!db.isUser("!admiM2") ) {
            db.addUser(new User("!admiM2", "!admiM2", "A"));
        }
        if (!db.isUser("A@lice5") ) {
            db.addUser(new User("A@lice5", "@cSit100", "C") );
        }
        if (!db.isUser("$BriAn7") ) {
            db.addUser(new User("$BriAn7", "123aBc##", "C") );
        }
        if (!db.isUser("!chriS12!") ) {
            db.addUser(new User("!chriS12!", "CHrIS12!!", "C") );
        }
        if (!db.isFlight("Otter101") ) {
            db.addFlight(new Flight("Otter101", "Monterey", "Los Angeles",
                         "10:30(AM)", 10, 150.00) );
        }
        if (!db.isFlight("Otter102") ) {
            db.addFlight(new Flight("Otter102", "Los Angeles", "Monterey",
                    "1:00(PM)", 10, 150.00) );
        }
        if (!db.isFlight("Otter201") ) {
            db.addFlight(new Flight("Otter201", "Monterey", "Seattle",
                    "11:00(AM)", 5, 200.50) );
        }
        if (!db.isFlight("Otter205") ) {
            db.addFlight(new Flight("Otter205", "Monterey", "Seattle",
                    "3:45(PM)", 15, 150.00) );
        }
        if (!db.isFlight("Otter202") ) {
            db.addFlight(new Flight("Otter202", "Seattle", "Monterey",
                    "2:10(PM)", 10, 200.50) );
        }
    }
}
