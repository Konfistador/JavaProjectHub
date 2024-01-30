package minibar;

/**
 * This exception should be thrown when a guest get the StomachException.
 *
 */
public class DrunkenException extends RuntimeException {

    public DrunkenException( String message ) {
        super( message );
    }
}
