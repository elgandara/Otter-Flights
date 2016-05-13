package edu.csumb.gand4052.otterflights;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    MySQLiteHelper db;
    private String action;
    private boolean hasSignInFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // create a database for the app
        db = new MySQLiteHelper(this);

        // Retrieve the purpose/action of the current login screen
        Bundle bundle = this.getIntent().getExtras();
        this.action = bundle.getString("action");

        // Initialize the layout
        setupLayout();

        // Initialize activity variables
        hasSignInFailed = false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.action_button) {

            EditText usernameEditText = (EditText) findViewById(R.id.username_edit_text);
            EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);

            if (this.action.equals("create") ) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Log.d("debug", "Username: " + username);
                Log.d("debug", "Password: " + password);
                Log.d("debug", "isUsernameValid: " + isWordValid(username) );
                Log.d("debug", "isPasswordValid: " + isWordValid(password) );

                if (db.isUser(username) ) {
                    // TODO: Notify the user the chosen username already exists
                }
                else if (isWordValid(username) && isWordValid(password) ) {
                    db.addUser(new User(username, password, "C"));
                    Log.d("debug", "User has been added.");

                    // TODO: Display success dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.create_success_message);

                    // Set the positive and negative buttons
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                            startActivity(intent);
                        }
                    });

                    // Create the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {

                }
                // If this is the second attempt, the user is sent to the main menu
                if (hasSignInFailed) {
                    // TODO: Dsiplay an alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.create_fail_message);

                    // Set the positive and negative buttons
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                            startActivity(intent);
                        }
                    });

                    // Create the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Send user back to main menu
                    Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }
                // The sign in has failed if the activity reaches this point
                hasSignInFailed = true;
            }
            else if (this.action.equals("reserve") ) {

            }
            else if (this.action.equals("cancel") ) {

            }
            else if (this.action.equals("manage") ) {

            }
        }
    }

    private void setupLayout() {
        // Add button's click listener and setup for activity
        Button actionButton = (Button) findViewById(R.id.action_button);
        actionButton.setOnClickListener(this);

        TextView titleTextView = (TextView) findViewById(R.id.login_title);

        // Set the text of the login
        if (this.action.equals("create") ) {
            actionButton.setText("Create Account");
        }
        else {
            actionButton.setText("Sign In");
        }

        // Set the title of the login
        if (this.action.equals("create") ) {
            titleTextView.setText("Create New Account");
        }
        else if (this.action.equals("reserve") || this.action.equals("cancel") ) {
            titleTextView.setText("User Sign In");
        }
        else if (this.action.equals("manage") ) {
            titleTextView.setText("Admin Sign In");
        }
    }

    // Returns true if the word contains at least one special character,
    // one number, one uppercase, and one lowercase letter
    private boolean isWordValid(String word) {
        // Log.d("regex", "isWordValid: " + word.matches(".*[\\w\\$\\@\\!\\#].*") );
        return (word.matches(".*[\\w\\$\\@\\!\\#].*") );
    }
}