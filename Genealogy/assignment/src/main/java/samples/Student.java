package samples;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Student extends Person implements Cloneable, Serializable {

    private final long studentNummer;
    private final String course = "PRC2";

    public Student( long studentNummer, String name, LocalDate dob ) {
        super(name,dob);
        this.studentNummer = studentNummer;
    }

    public long getStudentNummer() {
        return studentNummer;
    }
        public String getCourse() {
        return course;
    }

}
