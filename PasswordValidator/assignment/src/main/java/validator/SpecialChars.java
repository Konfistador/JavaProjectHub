package validator;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility method to get all 'special' chars, which are ASCII (0x20(space)-127not alpha and not digit.
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
public class SpecialChars {
    public static List<Character> getSpecialChars() {
        return IntStream
                .iterate( 0x21, x -> x < 127, x->x+1 )
                .filter( x -> !(isAlphabetic( x)  || isDigit( x )))
                .mapToObj(y -> (char) y )
                .collect(Collectors.toList());
    }
}
