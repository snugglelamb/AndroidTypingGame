package shawnli.firsttry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class MyActivity extends Activity {

    TextView mainTextView;
    TextView msgTextView;
    EditText userInput;
    Button buttonSubmit;
    Button buttonQuit;
    public String inputString;
    public String testString;
    public HashMap<String, User> List  = new HashMap<String, User>();

    RandomSentences sentence;

    // Count Time elapsed
    public long startTime;
    public long stopTime;

    // Initialize Dialog variable
    private static final int READY_DIALOG = 1;
    private static final int CORRECT_DIALOG = 2;
    private static final int INCORRECT_DIALOG = 3;


    // keep track of lowest time
    public double lowestTime = 1000.;
    // keep track of times played.
    public int count;

    // Image switch
    ImageView configIcon;
    // initialize timer
    Timer timer;

    public void setConfigIcon(final double lowestTime){
        configIcon.setImageResource(R.drawable.img0);
        timer = new Timer();
        int msec = (int) lowestTime * 1000;
        final String halfPeriod = String.format("%.1f", lowestTime/2);
        final String outputMessage = "[Notice] " + halfPeriod +" seconds left to be the fastest typist!";

        // full time up
        // schedule a half wait timer task
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // show initial config Icon
                        configIcon.setImageResource(R.drawable.img1);
                        Toast.makeText(getApplicationContext(), outputMessage, Toast.LENGTH_LONG).show();
                        msgTextView.setText(outputMessage);
                    }
                });
            }
        }, msec/2);

        // schedule a full wait timer task
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // show initial config Icon
                            configIcon.setImageResource(R.drawable.img2);
                            msgTextView.setText("Patience is a virtue :)");
                            Toast.makeText(getApplicationContext(), "Time's up ^w^", Toast.LENGTH_LONG).show();
                            timer.cancel();
                        }
                    });
                }
            }, msec);


    }


    @Override
    protected Dialog onCreateDialog(int id){
        if (id == READY_DIALOG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
            builder.setMessage("Are you ready?");
            // this is the button to display
            builder.setPositiveButton("Yes!",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // start counting
                            startTime = System.currentTimeMillis();

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });

            return builder.create();
        }

        else if (id == CORRECT_DIALOG){

            // get stop time
            stopTime = System.currentTimeMillis();

            // calculate time elapsed
            double timePeriod = (stopTime - startTime)/1000.;

            // indicator whether beat the best time so far
            boolean flag; // achieve best performance

            if (lowestTime > timePeriod)
            {
                lowestTime = timePeriod;
                flag = true; // not beat the best time
            }else{
                flag = false;
            }

            // round the result
            final String lowest = String.format("%.1f", lowestTime);
            String result = String.format("%.1f", timePeriod);

            msgTextView.setText("Correct:)   " + result + " seconds passed.");

            String outputMessage;

            // decide whether it's first time playing this game
            if (count == 0){
                // Output String on Dialog when first time play this game
                outputMessage = "Great! It took you " + result + " seconds to finish." + "\n Click Yes! to continue.";
            }
            else {

                if (flag) // beat
                {
                    // Output String on Dialog when achieve best performance
                    outputMessage = "You achieve the best performance ever!\n" + "The new record is " + result + " seconds." + "\nClick Yes! to continue.";
                } else {
                    // Output String on Dialog when not beat
                    outputMessage = "Correct! But not fast enough.\nBest performance: " + lowest + " seconds.\n" + "It took you " + result + " seconds." + "\nClick Yes! to continue.";
                }
            }
            // build new dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // this is the message to display
            builder.setMessage(outputMessage);
            // this is the button to display
            builder.setPositiveButton("Yes!",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // reset counting
                            startTime = System.currentTimeMillis();

                            // reset EditText, msgText
                            userInput.setText("");
                            userInput.setHint("Please input the sentence above. :)");
                            msgTextView.setText("Please input the sentence above. :)");

                            // set next sentence
                            String testSentence = sentence.sentenceGenerator(4);

                            mainTextView.setText(testSentence);

                            // terminate any timer
                            if (timer != null) {
                                timer.cancel();
                            }

                            // set Icon Timer
                            setConfigIcon(lowestTime);
                            System.out.println("SetTimer: "+ lowestTime + "seconds");

                            // this will hide the dialog
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
        else if (id == INCORRECT_DIALOG){

            // Output String on Dialog
            String outputIncorrectMessage = "Your Input is Incorrect.\n Click Yes! to continue playing. :)";

            // build new dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // this is the message to display
            builder.setMessage(outputIncorrectMessage);
            // this is the button to display
            builder.setPositiveButton("Yes!",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // reset counting
                            startTime = System.currentTimeMillis();

                            // reset EditText, msgText
                            userInput.setText("");
                            userInput.setHint("Please input the sentence above. :)");
                            msgTextView.setText("Please input the sentence above. :)");

                            // set next sentence
                            String testSentence = sentence.sentenceGenerator(7);
                            mainTextView.setText(testSentence);

                            // terminate any timer
                            if (timer != null) {
                                timer.cancel();
                            }

                            // set Icon Timer
                            setConfigIcon(lowestTime);
                            System.out.println("SetTimer: "+ lowestTime + "seconds");

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });


            return builder.create();

        }


        else return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // initialize controller for Icon
        configIcon = (ImageView)findViewById(R.id.config_icon);

        // initialize random sentence
        sentence = new RandomSentences();
        String testSentence = sentence.sentenceGenerator(10);



        // initialize count value
        count = 0;

        // Access the TextView defined in layout XML
        // and then set its text
        mainTextView = (TextView)findViewById(R.id.introduction);
        mainTextView.setText(testSentence);

        msgTextView = (TextView)findViewById(R.id.feedback);
        msgTextView.setText("Click the SUBMIT button when finish!");

        // Access user input defined in XML
        userInput =(EditText)findViewById(R.id.user_input);

        buttonSubmit = (Button)findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            //handler for clicking on submit button
            public void onClick(View view) {
                // cancel timer for face icon


                // get user input string
                inputString = userInput.getText().toString();

                // get selected string
                testString = mainTextView.getText().toString();

                // compare above two strings
                if (inputString.equals(testString)){

//                    // show correct response
//                    Toast.makeText(getApplicationContext(), outputMessage, Toast.LENGTH_LONG).show();

                    // enable user to reset timer
                    removeDialog(CORRECT_DIALOG);
                    showDialog(CORRECT_DIALOG);


                }else{
                    // show incorrect response
//                    msgTextView.setText("Incorrect input, plz check");
//                    Toast.makeText(getApplicationContext(), "That's wrong!", Toast.LENGTH_LONG).show();


                    // create dialog to enable user to reset timer
                    removeDialog(INCORRECT_DIALOG);
                    showDialog(INCORRECT_DIALOG);
                }

                // increase count
                count +=1 ;



            }
        });

        buttonQuit = (Button)findViewById(R.id.button_quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            //handler for clicking on quit button
            public void onClick(View view) {

                // finish garbage collection
                finish();
                // exit program
                System.exit(0);
            }
        });

        // show dialog on start of activity
        showDialog(READY_DIALOG);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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


