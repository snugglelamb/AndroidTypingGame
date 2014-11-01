package shawnli.firsttry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import java.util.HashMap;

public class Login extends Activity {

    EditText userInput;
    Button login;
    // store username
    private String userName;

    // Initialize Dialog variable
    private static final int READY_DIALOG = 1;

    // Store basic info in List and send to Login.java
    User user;
    public HashMap<String, User> List  = new HashMap<String, User>();


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
                user.setName(userName);

                //store in hash
                List.put(user.getName(),user);

                // trigger dialog to choose level
                removeDialog(READY_DIALOG);
                showDialog(READY_DIALOG);



                // close screen
//                finish();


            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == READY_DIALOG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
            builder.setTitle("Select a Level");
            builder.setMessage("Please select a level to start :)");
            // this is the button to display
            builder.setNegativeButton("Easy",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose easy level
                            user.setLevel("easy");

                            clickHandler();

                            // this will hide the dialog
                            dialog.cancel();

                            // close activity
                            finish();

                        }
                    });
            builder.setNeutralButton("Medium",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose medium
                            user.setLevel("medium");

                                // pass variable to MyActivity
                                // pass user object
                            clickHandler();

                            // this will hide the dialog
                            dialog.cancel();

                            // close activity
                            finish();

                        }
                    });

            builder.setPositiveButton("Hard",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose hard
                            user.setLevel("hard");

                            // clicked
                            clickHandler();

                            // this will hide the dialog
                            dialog.cancel();

                            // close activity
                            finish();


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


    public void clickHandler(){
        // pass variable to MyActivity
        // pass user object
        Intent next = new Intent(Login.this, MyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",user.getName());
        bundle.putString("Level",user.getLevel());

        next.putExtras(bundle);
        startActivity(next);

    }

}
