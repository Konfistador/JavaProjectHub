package validator;

//import static java.lang.Character.;

import java.nio.charset.CharsetEncoder;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static validator.Flaw.*;

/**
 * Password validator using lambdas and maps.
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class Validator {

    private EnumSet<Flaw> flaws;
    private final List<Consumer<Character>> consumersList = List.of(
            c -> {
                if(Character.isUpperCase(c)) flaws.remove(NOUPPER);
            },
            c -> {
                if(Character.isLowerCase(c)) flaws.remove(NOLOWER);
            },
            c -> {
                if(Character.isDigit(c)) flaws.remove(NODIGIT);
            },
            c -> {
                String specialChars = SpecialChars.getSpecialChars().stream().map(Objects::toString).collect(joining());
                if(specialChars.contains(c.toString())) flaws.remove(NOSPECIAL);
            }
    );

    void validate(String password) {
        flaws = EnumSet.allOf(Flaw.class);

        for (Consumer<Character> consumer : consumersList){
        password.chars().mapToObj(c -> (char) c)
                .forEach(consumer);
        }

        if(password.length() >= 10) flaws.remove(TOO_SHORT);
        if (!flaws.isEmpty()) throw new InvalidPasswordException(flaws.stream()
                .map(Flaw::getDescription)
                .collect(Collectors.joining("\n")));
    }
}
