package shawnli.firsttry;

import java.util.Random;

public class RandomSentences{

    final static int NO_WORDS = 5;	// These constants must be static
    final static int NO_SENTS = 20;	// if they are going to be used in
    final static String SPACE = " ";	// a static method like main().
    final static String PERIOD = ".";

    static Random r = new Random();

    public static void main( String args[] ){

        String article[] = { "the", "a", "one", "some", "any" };
        String noun[] = { "boy", "girl", "dog", "town", "car" };
        String verb[] = { "drove", "jumped", "ran", "walked", "skipped" };
        String preposition[] = { "to", "from", "over", "under", "on" };

        String sentence;
        for (int i = 0; i < NO_SENTS; i++){
            sentence = article[rand()];
            char c = sentence.charAt(0);
            sentence = sentence.replace( c, Character.toUpperCase(c) );
            sentence += SPACE + noun[rand()] + SPACE;
            sentence += (verb[rand()] + SPACE + preposition[rand()]);
            sentence += (SPACE + article[rand()] + SPACE + noun[rand()]);
            sentence += PERIOD;
            System.out.println(sentence);
            sentence = "";
        }
    }

    static int rand(){
        int ri = r.nextInt() % NO_WORDS;
        if ( ri < 0 )
            ri += NO_WORDS;
        return ri;
    }
}
