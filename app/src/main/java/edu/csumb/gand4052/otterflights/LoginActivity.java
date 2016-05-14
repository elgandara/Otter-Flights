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

    private MySQLiteHelper db;
    private String action;
    private int failedAttempts;
    private boolean isLoginSuccess;

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
        failedAttempts = 0;
        isLoginSuccess = false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.action_button) {

            // Get instance of the input edit texts
            EditText usernameEditText = (EditText) findViewById(R.id.username_edit_text);
            EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);


            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (this.action.equals("create") ) {

                int messageId = 0;

                if (db.isUser(username) ) {
                    // Notify the user the chosen username already exists
                    messageId = R.string.existing_user_message;
                    failedAttempts++;
                }
                // When the user info is valid and the username is available
                else if (isWordValid(username) && isWordValid(password) ) {
                    db.addUser(new User(username, password, "C"));
                    //Log.d("debug", "User has been added.");

                    // Display a success dialog and send back to home activity
                    messageId = R.string.create_success_message;
                    isLoginSuccess = true;

                    // Create a new transaction and enter it into the database
                    Transaction transaction = new Transaction();
                    transaction.setType("New Account");
                    transaction.setUsername(username);

                    // Insert the transaction into the database
                    db.addTransaction(transaction);
                }
                // The sign in has failed if the activity reaches this point
                else {
                    messageId = R.string.create_failure_message;
                    failedAttempts++;
                }

                // If this is the second attempt, the user is sent to the main menu
                if (failedAttempts > 1 || isLoginSuccess) {
                    // Display the appropriate failure message
                    AlertDialog dialog = createAlertDialog(messageId, true);
                    dialog.show();
                }
                // Otherwise, display the error and allow for more input
                else {
                    AlertDialog dialog = createAlertDialog(messageId, false);
                    dialog.show();
                }
            }
            else if (this.action.equals("reserve") ) {

            }
            else if (this.action.equals("cancel") ) {

            }
            else if (this.action.equals("manage") ) {

                int messageId = 0;

                if (db.isUser(username) ) {

                    // Get the user info
                    User user = db.getUser(username);

                    if (password.equals(user.getPassword() ) && user.getAccountType().equals("A") ) {
                        messageId = R.string.admin_success_message;
                        isLoginSuccess = true;

                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else {
                        failedAttempts++;
                        messageId = R.string.invalid_login_message;
                    }
                }
                else {
                    failedAttempts++;
                    messageId = R.string.invalid_login_message;
                }


                // If this is the second attempt, the user is sent to the main menu
                if (failedAttempts > 1 || isLoginSuccess) {
                    // Display the appropriate failure message
                    AlertDialog dialog = createAlertDialog(messageId, true);
                    dialog.show();
                }
                // Otherwise, display the error and allow for more input
                else {
                    AlertDialog dialog = createAlertDialog(messageId, false);
                    dialog.show();
                }
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

        // Make sure there is at leas one capital letter, lowercase letter,
        // spacial character and number
        return (word.matches(".*[a-z]+.*") && word.matches(".*[A-Z]+.*") &&
            word.matches(".*\\d+.*") && word.matches(".*[\\!\\@\\#\\$]+.*") );
    }

    private AlertDialog createAlertDialog(int messageResourseId, final boolean sendBackToHome) {
        // Create the alert dialog builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageResourseId);

        builder.setTitle(R.string.alert_notification_title);

        // Set the positive and negative buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // If the sign in has failed previously, send the user back to
                if (sendBackToHome) {
                    //Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                    //startActivity(intent);
                    finish();
                }
            }
        });

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        return dialog;
    }
}