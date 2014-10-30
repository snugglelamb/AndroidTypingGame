package shawnli.firsttry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    EditText userInput;
    Button login;
    // store username
    public String userName;

    // Initialize Dialog variable
    private static final int READY_DIALOG = 1;
    public String level;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new User();

        // initialize controller
        userInput = (EditText)findViewById(R.id.username);
        login = (Button)findViewById(R.id.Login);

        // on click login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read user input
                userName = userInput.getText().toString();
                System.out.println("Read name: "+ userName);

                //record username
                user.addUser(userName);

                // trigger dialog to choose level
                removeDialog(READY_DIALOG);
                showDialog(READY_DIALOG);


                // pass variable to MyActivity
                // pass user object

                // close screen
//                finish();


            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (id == READY_DIALOG) {
            // this is the message to display
            builder.setMessage("Please pick a level to start :)");
            // this is the button to display
            builder.setPositiveButton("Easy",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose easy level
                            user.addLevel("easy");

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });
            builder.setNeutralButton("Medium",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose medium
                            user.addLevel("medium");

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });

            builder.setNegativeButton("Hard",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose hard
                            user.addLevel("hard");
                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });

            return builder.create();
        }
        else return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
