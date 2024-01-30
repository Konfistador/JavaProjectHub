package pub;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
class PubException extends Exception {

    Object[] params;

    public PubException( String defaultMessage, Object... params ) {
        super( defaultMessage );
        this.params = params;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessageOrDefault();
    }

    public String getMessageOrDefault() {

        var bundle = ResourceBundle.getBundle("pub.inthepub", Locale.getDefault());
        var defMsg = getMessage();
        var key = defMsg.toLowerCase().replaceAll("\\s", "_");
        var output = bundle.containsKey(key) ? bundle.getString(key) : defMsg;

        return MessageFormat.format(output, params);
    }


}
