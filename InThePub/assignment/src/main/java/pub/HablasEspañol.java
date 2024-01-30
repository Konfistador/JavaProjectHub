package pub;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import static pub.LanguageUtils.getMessageOrDefault;

/**
 * This class is for playing around with the internationalization
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class HablasEspañol {

    public static void main( String[] args ) {

        Locale spain = new Locale( "es", "ES" );
        ResourceBundle bundle = ResourceBundle.getBundle( "pub.inthepub", Locale.getDefault() );
        ResourceBundle bundleES = ResourceBundle.getBundle( "pub.inthepub", spain );

        Locale locale = bundle.getLocale();
        System.out.println( "locale = " + locale );
        Iterator<String> keys = bundle.getKeys().asIterator();
        while ( keys.hasNext() ) {
            System.out.println( keys.next() );
        }

        String hi = bundle.getString( "hello" );
        System.out.println( "hi = " + hi );
        String utterance = "Good Morning";
        hi = getMessageOrDefault( bundle, utterance, utterance );
        System.out.println( "hi = " + hi );
        String hiEs = getMessageOrDefault( bundleES, utterance, utterance );
        System.out.println( "hiEs = " + hiEs );
        String message = new DrinkerFullException().getMessage();
        System.out.println( "message = " + message );

    }

}
