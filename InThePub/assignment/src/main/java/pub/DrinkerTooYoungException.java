package pub;

/**
 * We do not get you drunk too early, don't we?.
 * @author Linda Urselmans {@code l.urselmans@fontys.nl}
 */
class DrinkerTooYoungException extends PubException {

    public DrinkerTooYoungException() {
        super( "You are too young to drink" );
    }

}
