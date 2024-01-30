package pub;

/**
 * Well, I am sorry now.
 *
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
class EmptyStockException extends PubException {

    public EmptyStockException( double stockLeft ) {
        super( "beer is sold out",stockLeft );
    }

}
