package shawnli.firsttry;


import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ShawnLi on 10/30/14.
 */
public class sentenceScore {

    public static HashMap <Character, Integer> scoreDict;

    public int finalScore(String sentence){
        // run dictionary
        generateDict();
        int score = 0;

        // uncapitalize
        sentence = uncaps(sentence);
        System.out.println("uncap output:  "+ sentence);

        // gsub spaces
        sentence = sentence.replace(" ","");
        sentence = sentence.replace(".","");
        System.out.println("no space output:  "+ sentence);

        // String to char
        char[] charArray = sentence.toCharArray();
        Arrays.sort(charArray);

        for (int i=0; i<charArray.length; i++){

            score += letterScore(charArray[i]);
        }

        System.out.println("sentence score: "+ score);

        return score;
    }

    public static String uncaps(String string) {
        if (string.length() == 0) {
            return string;
        }
        char ch = string.charAt(0);
        if (Character.isUpperCase(ch)) {
            ch = Character.toLowerCase(ch);
            return ch + string.substring(1);
        }
        return string;
    }

    public static int letterScore(char x){
        return scoreDict.get(x);
    }

    public static void generateDict(){
        // build dictionary, score = min {distance from f or j}
        scoreDict = new HashMap<Character, Integer>();
        scoreDict.put('a',3);
        scoreDict.put('s',2);
        scoreDict.put('d',1);
        scoreDict.put('f',0);
        scoreDict.put('g',1);
        scoreDict.put('h',1);
        scoreDict.put('j',0);
        scoreDict.put('k',1);
        scoreDict.put('l',2);
        scoreDict.put('q',4);
        scoreDict.put('w',3);
        scoreDict.put('e',2);
        scoreDict.put('r',1);
        scoreDict.put('t',1);
        scoreDict.put('y',2);
        scoreDict.put('u',1);
        scoreDict.put('i',1);
        scoreDict.put('o',2);
        scoreDict.put('p',3);
        scoreDict.put('z',3);
        scoreDict.put('x',2);
        scoreDict.put('c',1);
        scoreDict.put('v',1);
        scoreDict.put('b',2);
        scoreDict.put('n',1);
        scoreDict.put('m',1);
    }

}
