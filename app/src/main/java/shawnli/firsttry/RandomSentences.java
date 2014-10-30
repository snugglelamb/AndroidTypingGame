package shawnli.firsttry;

import java.util.Random;

public class RandomSentences{

    final static int NO_WORDS = 5;	// These constants must be static
    final static String SPACE = " ";	// a static method like main().
    final static String PERIOD = ".";

    static Random r = new Random();

    public String sentenceGenerator( int words ){

        String article[] = { "the", "a", "one", "some", "any" };
        String adj[] = {"funny","weird","pretty","awkward","big"};
        String noun[] = { "boy", "girl", "dog", "train", "car" };
        String verb[] = { "drove", "jumped", "ran", "walked", "skipped" };
        String preposition[] = { "to", "from", "over", "under", "on" };

        String sentence;

            if (words == 4) {
                sentence = noun[rand()];
                char c = sentence.charAt(0);
                sentence = sentence.replace(c, Character.toUpperCase(c));
                sentence += SPACE + (verb[rand()] + SPACE + preposition[rand()]);
                sentence += SPACE + noun[rand()];
                sentence += PERIOD;
            }
            else if (words == 7){
                sentence = article[rand()];
                char c = sentence.charAt(0);
                sentence = sentence.replace(c, Character.toUpperCase(c));
                sentence += SPACE + adj[rand()];
                sentence += SPACE + noun[rand()] + SPACE;
                sentence += (verb[rand()] + SPACE + preposition[rand()]);
                sentence += (SPACE + article[rand()] + SPACE + noun[rand()]);
                sentence += PERIOD;
            }

            else {
                sentence = article[rand()];
                char c = sentence.charAt(0);
                sentence = sentence.replace(c, Character.toUpperCase(c));
                sentence += SPACE + adj[rand()];
                sentence += SPACE + noun[rand()] + SPACE + "and";
                sentence += SPACE + article[rand()];
                sentence += SPACE + noun[rand()] + SPACE;
                sentence += (verb[rand()] + SPACE + preposition[rand()]);
                sentence += (SPACE + article[rand()] + SPACE + noun[rand()]);
                sentence += PERIOD;
            }
        return sentence;

    }

    static int rand(){
        int ri = r.nextInt() % NO_WORDS;
        if ( ri < 0 )
            ri += NO_WORDS;
        return ri;
    }
}