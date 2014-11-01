package shawnli.firsttry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by ShawnLi on 10/29/14.
 */

public class User implements Serializable{
    private int id;
    private String name;
    private String setting;
    private HashMap <String,Integer> score = new HashMap<String, Integer>();
    private HashMap <String,Double> bestTimeHash = new HashMap<String, Double>();
    private HashMap <String,Double> worstTimeHash = new HashMap<String, Double>();

    public String getName(){return name;}
    public int getId(){return id;}
    public String getLevel(){return setting;}
    public HashMap <String,Integer> getScore(){return score;}
    public HashMap <String,Double> getBestTimeHash(){return bestTimeHash;}
    public HashMap <String,Double> getWorstTimeHash(){return worstTimeHash;}

    public void setName(String input){
        name = input;
    }
    public void setLevel(String level){
        setting = level;
    }
    public void setId(Integer userId){id = userId;}

    public void updateBestTime(int sentenceScore, Double bestTime){
        score.put(setting,sentenceScore);
        bestTimeHash.put(setting,bestTime);
    }

    public void updateWorstTime(Double worstTime){
       worstTimeHash.put(setting,worstTime);
    }


}
