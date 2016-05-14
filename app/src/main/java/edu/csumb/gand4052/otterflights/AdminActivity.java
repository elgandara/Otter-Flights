package edu.csumb.gand4052.otterflights;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements OnClickListener{

    private MySQLiteHelper db;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // create a database for the app
        db = new MySQLiteHelper(this);

        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        createTransactionViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.continue_button) {
            AlertDialog dialog = createAlertDialog(R.string.create_flight_text);
            dialog.show();
        }
    }

    private void createTransactionViews() {
        ArrayList<Transaction> transactions = db.getTransactions("");

        if (transactions.size() == 0) {

            LinearLayout linearLayout = new LinearLayout(this);

            TextView textView = new TextView(this);
            textView.setText("No transactions.");
            textView.setTextSize(20);
            textView.setTextColor(Color.rgb(0, 0, 0) );
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            linearLayout.addView(textView);
            mainLayout.addView(linearLayout);
        }
        else {
            for (Transaction t : transactions) {
                if (t.getType().equals("New Account") ) {
                    LinearLayout linearLayout = new LinearLayout(this);

                    TextView textView = new TextView(this);
                    textView.setText("");
                    textView.setTextSize(20);
                    textView.setTextColor(Color.rgb(0, 0, 0) );

                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    linearLayout.addView(textView);
                    mainLayout.addView(linearLayout);
                }
                else if (t.getType().equals("Reserve Seat") ) {

                }
            }
        }
    }

    private AlertDialog createAlertDialog(int messageResourseId) {
        // Create the alert dialog builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageResourseId);

        builder.setTitle(R.string.alert_notification_title);

        // Set the positive and negative buttons
        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // If the sign in has failed previously, send the user back to
                Intent intent = new Intent(AdminActivity.this, AddFlightActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // If the sign in has failed previously, send the user back to
                finish();
            }
        });

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
