package samples;

import java.time.LocalDate;

/**
 *
 * @author Pieter van den Hombergh {@code Pieter.van.den.Hombergh@gmail.com}
 */
class Person {

    private static long citizenCount=0;
    private final long citizenNumber= ++citizenCount;
    private final String name;
    private final LocalDate dob;

    public Person( String name, LocalDate dob ) {
        this.name = name;
        this.dob = dob;
    }

    public long getCitizenNumber() {
        return citizenNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }
   
}
