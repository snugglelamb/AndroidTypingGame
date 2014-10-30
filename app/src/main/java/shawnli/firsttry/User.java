package shawnli.firsttry;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by ShawnLi on 10/29/14.
 */

public class User {
    public String name;
    public String setting;
    public HashMap <String,Integer> score;
    public HashMap <String,Double> Time;

    public void addUser(String input){
        this.name = input;
    }

    public void addLevel(String level){
        this.setting = level;
    }

    public void addScore(int sentenceScore, Double bestTime){
       score.put(this.setting,sentenceScore);
       Time.put(this.setting,bestTime);
    }

    public String currentLevel(){
        return this.setting;
    }

}
