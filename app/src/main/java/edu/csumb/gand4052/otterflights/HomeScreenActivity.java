package edu.csumb.gand4052.otterflights;

import android.content.Intent;
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

        // Create an admin user if it does not already exist
        if (!db.isUser("!admiM2") ) {
            db.addUser(new User("!admiM2", "!admiM2", "admin"));
        }

        HashMap<String, User> users = db.getUsers();
        Set<String> keys = users.keySet();

        logUsers(keys, users);

        db.removeUser("elgandara");
        users = db.getUsers();
        keys = users.keySet();

        logUsers(keys, users);


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

        }
        else if (id == R.id.system_manage_button) {

        }
    }

    public void logUsers(Set<String> keys, HashMap<String, User> users) {
        for (String key : keys) {
            Log.d("user", users.get(key).toString() );
        }
    }
}