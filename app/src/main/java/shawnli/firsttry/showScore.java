package shawnli.firsttry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class showScore extends Activity {


//    public HashMap<String, User> userList  = new HashMap<String, User>();
    Button scoreBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        // Initialize multiple textViews
        TextView user1Name = (TextView)findViewById(R.id.user1_name);
        TextView user1EasyScore = (TextView)findViewById(R.id.user1_easy_score);
        TextView user1MediumScore = (TextView)findViewById(R.id.user1_medium_score);
        TextView user1HardScore = (TextView)findViewById(R.id.user1_hard_score);
        TextView user1EasyBest = (TextView)findViewById(R.id.user1_easy_best);
        TextView user1MediumBest = (TextView)findViewById(R.id.user1_medium_best);
        TextView user1HardBest = (TextView)findViewById(R.id.user1_hard_best);
        TextView user1EasyWorst = (TextView)findViewById(R.id.user1_easy_worst);
        TextView user1MediumWorst = (TextView)findViewById(R.id.user1_medium_worst);
        TextView user1HardWorst = (TextView)findViewById(R.id.user1_hard_worst);

        // Initialize multiple textViews
        TextView user2Name = (TextView)findViewById(R.id.user2_name);
        TextView user2EasyScore = (TextView)findViewById(R.id.user2_easy_score);
        TextView user2MediumScore = (TextView)findViewById(R.id.user2_medium_score);
        TextView user2HardScore = (TextView)findViewById(R.id.user2_hard_score);
        TextView user2EasyBest = (TextView)findViewById(R.id.user2_easy_best);
        TextView user2MediumBest = (TextView)findViewById(R.id.user2_medium_best);
        TextView user2HardBest = (TextView)findViewById(R.id.user2_hard_best);
        TextView user2EasyWorst = (TextView)findViewById(R.id.user2_easy_worst);
        TextView user2MediumWorst = (TextView)findViewById(R.id.user2_medium_worst);
        TextView user2HardWorst = (TextView)findViewById(R.id.user2_hard_worst);

        // for clear view when only one user enabled
        TextView clear1 = (TextView)findViewById(R.id.clear1);
        TextView clear2 = (TextView)findViewById(R.id.clear2);
        TextView clear3 = (TextView)findViewById(R.id.clear3);
        TextView clear4 = (TextView)findViewById(R.id.clear4);
        TextView clear5 = (TextView)findViewById(R.id.clear5);
        TextView clear6 = (TextView)findViewById(R.id.clear6);
        TextView clear7 = (TextView)findViewById(R.id.clear7);



        // to retrieve object in second Activity
//        Intent intent = getIntent();
//        HashMap<String, User> userList = (HashMap<String,User>) intent.getSerializableExtra("userList");

        // retrieve data from file "/tmp/user.ser"
        HashMap<String, User> userList = null;
        String path= Environment.getExternalStorageDirectory().toString()+"/user1.ser";
        File data = new File(path);
        try
        {
            FileInputStream fileIn = new FileInputStream(data);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userList = (HashMap<String,User>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("User class not found");
            c.printStackTrace();
            return;
        }

        int size = userList.size();
        // debug
        System.out.println("HASHMAP SIZE: "+ size);

        //update view
        if (size == 1){
            for (HashMap.Entry<String, User> entry : userList.entrySet()) {
                String key = entry.getKey();
                User user = entry.getValue();
                System.out.println("Name: "+ key + "  ID: "+ user.getId());
                // set user1
                user1Name.setText(key);
                // set bestTime
                if (user.getBestTimeHash().get("easy") != null) {
                    user1EasyBest.setText(user.getBestTimeHash().get("easy").toString());
                }
                if (user.getBestTimeHash().get("medium") != null) {
                    user1MediumBest.setText(user.getBestTimeHash().get("medium").toString());
                }
                if (user.getBestTimeHash().get("hard")!= null) {
                    user1HardBest.setText(user.getBestTimeHash().get("hard").toString());
                }
                // set worstTime
                if (user.getWorstTimeHash().get("easy")!= null) {
                    user1EasyWorst.setText(user.getWorstTimeHash().get("easy").toString());
                }
                if (user.getWorstTimeHash().get("medium")!=null) {
                    user1MediumWorst.setText(user.getWorstTimeHash().get("medium").toString());
                }
                if (user.getWorstTimeHash().get("hard")!=null) {
                    user1HardWorst.setText(user.getWorstTimeHash().get("hard").toString());
                }
                // set score
                if (user.getScore().get("easy")!=null) {
                    user1EasyScore.setText(user.getScore().get("easy").toString());
                }
                if (user.getScore().get("medium")!=null) {
                    user1MediumScore.setText(user.getScore().get("medium").toString());
                }
                if (user.getScore().get("hard")!= null) {
                    user1HardScore.setText(user.getScore().get("hard").toString());
                }

            }
            // clear user2 display
            user2Name.setText("");
            clear1.setText("");
            clear2.setText("");
            clear3.setText("");
            clear4.setText("");
            clear5.setText("");
            clear6.setText("");
            clear7.setText("");
        }
        else{
            // show the last two users

            for (HashMap.Entry<String, User> entry : userList.entrySet()) {

                String key = entry.getKey();
                User user = entry.getValue();
                System.out.println("Name: "+ key + "  ID: "+ user.getId());

                if (user.getId() == size){
                    // set user1
                    user1Name.setText(key);
                    // set bestTime
                    if (user.getBestTimeHash().get("easy") != null) {
                        user1EasyBest.setText(user.getBestTimeHash().get("easy").toString());
                    }
                    if (user.getBestTimeHash().get("medium") != null) {
                        user1MediumBest.setText(user.getBestTimeHash().get("medium").toString());
                    }
                    if (user.getBestTimeHash().get("hard")!= null) {
                        user1HardBest.setText(user.getBestTimeHash().get("hard").toString());
                    }
                    // set worstTime
                    if (user.getWorstTimeHash().get("easy")!= null) {
                        user1EasyWorst.setText(user.getWorstTimeHash().get("easy").toString());
                    }
                    if (user.getWorstTimeHash().get("medium")!=null) {
                        user1MediumWorst.setText(user.getWorstTimeHash().get("medium").toString());
                    }
                    if (user.getWorstTimeHash().get("hard")!=null) {
                        user1HardWorst.setText(user.getWorstTimeHash().get("hard").toString());
                    }
                    // set score
                    if (user.getScore().get("easy")!=null) {
                        user1EasyScore.setText(user.getScore().get("easy").toString());
                    }
                    if (user.getScore().get("medium")!=null) {
                        user1MediumScore.setText(user.getScore().get("medium").toString());
                    }
                    if (user.getScore().get("hard")!= null) {
                        user1HardScore.setText(user.getScore().get("hard").toString());
                    }

                }

                if (user.getId() == size-1){

                    // set user2
                    user2Name.setText(key);
                    // set bestTime
                    if (user.getBestTimeHash().get("easy") != null) {
                        user2EasyBest.setText(user.getBestTimeHash().get("easy").toString());
                    }
                    if (user.getBestTimeHash().get("medium") != null) {
                        user2MediumBest.setText(user.getBestTimeHash().get("medium").toString());
                    }
                    if (user.getBestTimeHash().get("hard")!= null) {
                        user2HardBest.setText(user.getBestTimeHash().get("hard").toString());
                    }
                    // set worstTime
                    if (user.getWorstTimeHash().get("easy")!= null) {
                        user2EasyWorst.setText(user.getWorstTimeHash().get("easy").toString());
                    }
                    if (user.getWorstTimeHash().get("medium")!=null) {
                        user2MediumWorst.setText(user.getWorstTimeHash().get("medium").toString());
                    }
                    if (user.getWorstTimeHash().get("hard")!=null) {
                        user2HardWorst.setText(user.getWorstTimeHash().get("hard").toString());
                    }
                    // set score
                    if (user.getScore().get("easy")!=null) {
                        user2EasyScore.setText(user.getScore().get("easy").toString());
                    }
                    if (user.getScore().get("medium")!=null) {
                        user2MediumScore.setText(user.getScore().get("medium").toString());
                    }
                    if (user.getScore().get("hard")!= null) {
                        user2HardScore.setText(user.getScore().get("hard").toString());
                    }

                }


            }

        }


        // return to mainActivity
        scoreBack = (Button)findViewById(R.id.button_score_back);
        scoreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kill score display then return to MyActivity
                finish();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_score, menu);
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
