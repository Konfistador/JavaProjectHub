package pub;

import java.util.ResourceBundle;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class LanguageUtils {
    static String getMessageOrDefault( ResourceBundle bundle, String utterance, String defaultString ) {
        String key=utterance.toLowerCase().replace("\\s","_)");
        return bundle.containsKey( key ) ? bundle.getString( key ) : defaultString;

    }

}
