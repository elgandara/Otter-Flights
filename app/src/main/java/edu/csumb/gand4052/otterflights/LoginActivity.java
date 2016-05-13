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
                Log.d("create", "Account possibly created...");
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean isValidAccount = false;



                if (!db.isUser(username) ) {
                    db.addUser(new User(username, password, "C"));

                    // TODO: Display success dialog
                }
                else {
                    if (hasSignInFailed) {
                        // TODO: Dsiplay and alert


                        // Send user back to main menu
                        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                    }
                    else {
                        hasSignInFailed = true;
                    }
                }
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
}
