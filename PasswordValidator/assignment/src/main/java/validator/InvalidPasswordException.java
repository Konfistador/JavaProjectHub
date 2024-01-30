package validator;

/**
 *
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException( String message ) {
        super( message );
    }
    
}
