package shawnli.firsttry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;



public class MyActivity extends Activity{

    TextView mainTextView;
    TextView msgTextView;
    EditText userInput;
    Button buttonSubmit;
    Button buttonQuit;
    Button buttonScore;
    Button buttonLevel;
    Button buttonLogout;

    private String inputString;
    private String testString;

    public HashMap<String, User> userList  = new HashMap<String, User>();
    User user;
    private int level;
    private int userID = 1;
    // switch level in runtime, to reset track of lowest time
    private int score;
    private boolean played = false; //make sure user play at least one time

    sentenceScore finalScore;
    RandomSentences sentence;

    // Count Time elapsed
    private long startTime;
    private long stopTime;

    // Initialize Dialog variable
    private static final int READY_DIALOG = 1;
    private static final int CORRECT_DIALOG = 2;
    private static final int INCORRECT_DIALOG = 3;
    private static final int CHANGE_DIALOG = 4;
    private static final int LOGOUT_DIALOG = 5;
    private static final int WARNING_DIALOG = 6;


    // keep track of lowest time
    private double lowestTime = 1000.;
    // keep track of slowest time
    private double slowestTime = 0.;

    // keep track of times played.
    private int[] count = new int[] {0,0,0,0,0,0,0,0,0,0,0};

    // Image switch
    ImageView configIcon;
    // initialize timer
    Timer timer;
    private int new_flag = 0;

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
            // can't click outside
//            setCanceledOnTouchOutside(false);
//            builder.setCanceledOnTouchOutside(false);
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
            // at least played once
            played = true;

            // get stop time
            stopTime = System.currentTimeMillis();

            // calculate time elapsed
            double timePeriod = (stopTime - startTime)/1000.;

            // indicator whether beat the best time so far
            boolean flag; // achieve best performance

            if (lowestTime > timePeriod)
            {
                lowestTime = timePeriod;
                user.updateBestTime(score, lowestTime);

                flag = true; // not beat the best time
            }else{
                flag = false;
            }

            // record slowest time
            if (timePeriod > slowestTime)
            {
                slowestTime = timePeriod;
                user.updateWorstTime(slowestTime);
            }

            // record both time value to hashmap userList
            userList.put(user.getName(), user);
            System.out.println(userList);



            // round the result
            final String lowest = String.format("%.1f", lowestTime);
            String result = String.format("%.1f", timePeriod);

            msgTextView.setText("Correct:)   " + result + " seconds passed.");

            String outputMessage;

            // decide whether it's first time playing this game
            if (count[level] == 0){
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
                            String testSentence = sentence.sentenceGenerator(level);
                            // calculate sentence score
                            finalScore = new sentenceScore();
                            score = finalScore.finalScore(testSentence);
                            System.out.println("SENTENCE SCORE: "+ score);


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
                            String testSentence = sentence.sentenceGenerator(level);
                            mainTextView.setText(testSentence);

                            // calculate sentence score
                            finalScore = new sentenceScore();
                            score = finalScore.finalScore(testSentence);
                            System.out.println("SENTENCE SCORE: "+ score);


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

        else if (id == CHANGE_DIALOG){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select a Level");

            // this is the message to display
            builder.setMessage("Which level would u like to switch to? :)");
            // this is the button to display
            builder.setNegativeButton("Easy",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose easy level
                            user.setLevel("easy");
                            buttonLevel.setText(user.getLevel());

                            // reset counting
                            startTime = System.currentTimeMillis();

                            // reset EditText, msgText
                            userInput.setText("");
                            userInput.setHint("Please input the sentence above. :)");
                            msgTextView.setText("Please input the sentence above. :)");

                            // set next sentence
                            level = 4;

                            // load existing lowestTime, if null set 1000/0
                            if (user.getBestTimeHash().get("easy") != null){
                                lowestTime = user.getBestTimeHash().get("easy");
                                slowestTime = user.getWorstTimeHash().get("easy");
                                Toast.makeText(getApplicationContext(), "LEVEL CHANGE", Toast.LENGTH_LONG).show();
                            }
                            else{
                                lowestTime = 1000.;
                                slowestTime = 0.;
                            }

                            String testSentence = sentence.sentenceGenerator(level);

                            // calculate sentence score
                            finalScore = new sentenceScore();
                            score = finalScore.finalScore(testSentence);
                            System.out.println("SENTENCE SCORE: "+ score);

                            mainTextView.setText(testSentence);

                            // terminate any timer
                            if (timer != null) {
                                timer.cancel();
                            }

                            // set Icon Timer
                            setConfigIcon(lowestTime);

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });
            builder.setNeutralButton("Medium",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose medium
                            user.setLevel("medium");
                            buttonLevel.setText(user.getLevel());

                            // reset counting
                            startTime = System.currentTimeMillis();

                            // reset EditText, msgText
                            userInput.setText("");
                            userInput.setHint("Please input the sentence above. :)");
                            msgTextView.setText("Please input the sentence above. :)");

                            // set next sentence
                            level = 7;
                            // load existing lowestTime, if null set 1000/0
                            if (user.getBestTimeHash().get("medium") != null){
                                lowestTime = user.getBestTimeHash().get("medium");
                                slowestTime = user.getWorstTimeHash().get("medium");
                                Toast.makeText(getApplicationContext(), "LEVEL CHANGE", Toast.LENGTH_LONG).show();
                            }
                            else{
                                lowestTime = 1000.;
                                slowestTime = 0.;
                            }

                            String testSentence = sentence.sentenceGenerator(level);

                            // calculate sentence score
                            finalScore = new sentenceScore();
                            score = finalScore.finalScore(testSentence);
                            System.out.println("SENTENCE SCORE: "+ score);


                            mainTextView.setText(testSentence);

                            // terminate any timer
                            if (timer != null) {
                                timer.cancel();
                            }

                            // set Icon Timer
                            setConfigIcon(lowestTime);

                            // this will hide the dialog
                            dialog.cancel();
                        }
                    });

            builder.setPositiveButton("Hard",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // choose hard
                            user.setLevel("hard");
                            buttonLevel.setText(user.getLevel());

                            // reset counting
                            startTime = System.currentTimeMillis();

                            // reset EditText, msgText
                            userInput.setText("");
                            userInput.setHint("Please input the sentence above. :)");
                            msgTextView.setText("Please input the sentence above. :)");

                            // set next sentence
                            level = 10;

                            // load existing lowestTime, if null set 1000/0
                            if (user.getBestTimeHash().get("hard") != null){
                                lowestTime = user.getBestTimeHash().get("hard");
                                slowestTime = user.getWorstTimeHash().get("hard");
                                Toast.makeText(getApplicationContext(), "LEVEL CHANGE", Toast.LENGTH_LONG).show();
                            }
                            else{
                                lowestTime = 1000.;
                                slowestTime = 0.;
                            }

                            String testSentence = sentence.sentenceGenerator(level);

                            // calculate sentence score
                            finalScore = new sentenceScore();
                            score = finalScore.finalScore(testSentence);
                            System.out.println("SENTENCE SCORE: "+ score);


                            mainTextView.setText(testSentence);

                            // terminate any timer
                            if (timer != null) {
                                timer.cancel();
                            }

                            // set Icon Timer
                            setConfigIcon(lowestTime);

                            // this will hide the dialog
                            dialog.cancel();

                        }
                    });

            return builder.create();
        }

        else if (id == LOGOUT_DIALOG){
            // otherwise, show a dialog to ask for their name
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hi!");
            alert.setMessage("You name please?");

            // Create EditText for entry
            final EditText input = new EditText(this);
            alert.setView(input);

            // Make an "OK" button to save the name
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    // Grab the EditText's input
                    String inputName = input.getText().toString();

                    // Initialize new user
                    user = new User();
                    user.setName(inputName);
                    // update userID
                    if (played) {
                        userID += 1;
                    }
                    user.setId(userID);

                    System.out.println("Name: "+ user.getName() + "  ID: "+ user.getId());

                    // update level info
                    switch (level){
                        case 4:
                            user.setLevel("easy");
                            break;
                        case 7:
                            user.setLevel("medium");
                            break;
                        case 10:
                            user.setLevel("hard");
                            break;
                    }

                    // re-initialize the factors
                    lowestTime = 1000.;
                    slowestTime = 0.;

                    // reset count
                    count[4] = 0;
                    count[7] = 0;
                    count[10] = 0;
                    played = false;

                    // reset sentence
                    String testSentence = sentence.sentenceGenerator(level);

                    // calculate sentence score
                    finalScore = new sentenceScore();
                    score = finalScore.finalScore(testSentence);
                    System.out.println("SENTENCE SCORE: "+ score);

                    // reset UI
                    mainTextView.setText(testSentence);
                    userInput.setText("");
                    userInput.setHint("Please input the sentence above. :)");
                    msgTextView.setText("Please input the sentence above. :)");
                    buttonLogout.setText(user.getName());

                    // terminate any timer
                    if (timer != null) {
                        timer.cancel();
                    }

                    // reset time counting
                    startTime = System.currentTimeMillis();

                    // set Icon Timer
                    setConfigIcon(lowestTime);

                    // Welcome the new user
                    Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!", Toast.LENGTH_LONG).show();

                    // cancel dialog
                    dialog.cancel();
                }
            });

            // Make a "Cancel" button
            // that simply dismisses the alert
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {}
            });

            return alert.create();

        }
        else if (id == WARNING_DIALOG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
            builder.setTitle("Hint");
            builder.setMessage("To reach the score screen, please at least finish one game.");
            // this is the button to display
            builder.setPositiveButton("Yes!",
                    new DialogInterface.OnClickListener() {
                        // this is the method to call when the button is clicked
                        public void onClick(DialogInterface dialog, int id) {

                            // reset counting
                            startTime = System.currentTimeMillis();

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
        // read data from file
        String path= Environment.getExternalStorageDirectory().toString()+"/user1.ser";
        final File data = new File(path);


        if (!data.isFile()){
            try{
                new_flag = 1;
                data.createNewFile();
                System.out.println("created userList file!!!!!!\n");
            }catch(IOException i){
                i.printStackTrace();
            }
        }

        // read from existing file
        if (new_flag != 1) {
            try {
                FileInputStream fileIn = new FileInputStream(data);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                userList = (HashMap<String, User>) in.readObject();
                in.close();
                fileIn.close();

                // update userID
                int size_load = userList.size();
                userID = userID + size_load;
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("User class not found");
                c.printStackTrace();
                return;
            }
        }

        // Create user object
        user = new User();

        // Get username & setting from former activity
        Bundle extra = getIntent().getExtras();
        if (extra != null){

          user.setName(extra.getString("Name"));
          user.setLevel(extra.getString("Level"));
        }
        // loop through current list to see if new name exists
        for (HashMap.Entry<String, User> entry : userList.entrySet()) {

            String key_temp = entry.getKey();
            User user_temp = entry.getValue();
            System.out.println("Name: " + key_temp + "  ID: " + user_temp.getId());
            if (key_temp.equals(user.getName())){
                userList.put(key_temp+"_old",user_temp);
            }

        }
        user.setId(userID);
        System.out.println("Name: "+ user.getName() + "  ID: "+ user.getId());


        System.out.println("USER: "+ user);


        // choose level
        if (user.getLevel().equals("easy")){
            level = 4;
        }
        else if (user.getLevel().equals("medium")){
            level = 7;
        }
        else{
            level = 10;
        }


        // initialize controller for Icon
        configIcon = (ImageView)findViewById(R.id.config_icon);

        // initialize random sentence
        sentence = new RandomSentences();
        String testSentence = sentence.sentenceGenerator(level);

        // calculate sentence score
        finalScore = new sentenceScore();
        score = finalScore.finalScore(testSentence);
        System.out.println("SENTENCE SCORE: "+ score);
        count[level] = 0;

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
                count[level] +=1 ;



            }
        });

        buttonQuit = (Button)findViewById(R.id.button_quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            //handler for clicking on quit button
            public void onClick(View view) {

                try {

                    FileOutputStream fileOut = new FileOutputStream(data);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(userList);
                    out.close();
                    fileOut.close();
                    System.out.printf("Serialized data is saved in /user1.ser\n");
                }catch(IOException i)
                {
                    i.printStackTrace();
                }

                // finish garbage collection
                finish();
                // exit program
                System.exit(0);
            }
        });


        buttonLogout = (Button)findViewById(R.id.button_Logout);

        if (!user.getName().equals("")) {
            buttonLogout.setText(user.getName());
        }
        else{
            buttonLogout.setText("Logout");
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
//                                                Intent next = new Intent(MyActivity.this, Login.class);
//                                                startActivity(next);
                                                // just bump out a dialog
                                                removeDialog(LOGOUT_DIALOG);
                                                showDialog(LOGOUT_DIALOG);

                                            }
                                        });


        // show score routine
        buttonScore = (Button)findViewById(R.id.button_score);
        buttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userList.size() == 0)
                {
                    removeDialog(WARNING_DIALOG);
                    showDialog(WARNING_DIALOG);
                }
                else {
//                    save data to file when click on score button
                      // save userlist object to file
                        try {

                            FileOutputStream fileOut = new FileOutputStream(data);
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(userList);
                            out.close();
                            fileOut.close();
                            System.out.printf("Serialized data is saved in /user1.ser\n");
                        }catch(IOException i)
                            {
                                i.printStackTrace();
                            }


                    // debug
                    System.out.println("PARCEL SENT: " + userList.size());

                    Intent next = new Intent(MyActivity.this, showScore.class);
//                    next.putExtra("userList", userList);
                    startActivity(next);
                }
            }
        });


        // pick level during runtime
        buttonLevel = (Button)findViewById(R.id.button_level);
        buttonLevel.setText(user.getLevel());
        buttonLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDialog(CHANGE_DIALOG);
                showDialog(CHANGE_DIALOG);

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


