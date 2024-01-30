package validator;

import java.util.EnumSet;
import java.util.Objects;

import static java.util.EnumSet.noneOf;
import static java.util.stream.Collectors.toCollection;

/**
 *
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
enum Flaw {
    NOUPPER( 'U', "No UPPER case letter" ),
    NOLOWER( 'l', "No lower case letter" ),
    NODIGIT( '8', "No digit" ),
    NOSPECIAL( '#', "No special character" ),
    TOO_SHORT( 's', "Too short" );
    final char encoding;
    final String description;

    Flaw( char encoding, String description ) {
        this.encoding = encoding;
        this.description = description;
    }

    char getEncoding() {
        return encoding;
    }

    String getDescription() {
        return description;
    }

    static Flaw encodingToFlaw( char encoding ) {
        for ( validator.Flaw flaw : values() ) {
            if ( flaw.getEncoding() == encoding ) {
                return flaw;
            }
        }
        return null;
    }

    /**
     * Collect the encoded flaws into an initially empty set.
     * @param encoding the flaws
     * @return the set of flaws matching the encoding.
     */
    static EnumSet<Flaw> stringToFlawSet( String encoding ) {
        return encoding.chars()
                .mapToObj( c -> Flaw.encodingToFlaw( (char) c ) )
                .filter(Objects::nonNull)
                .collect( toCollection( () -> noneOf( Flaw.class ) ) );
    }
}
