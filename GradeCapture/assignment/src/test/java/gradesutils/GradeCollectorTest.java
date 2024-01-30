package gradesutils;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
/**
 *
 * @author m.bonajo@fontys.nl
 */
public class GradeCollectorTest {

    /**
     * Test of main method, of class GradeCollector.
     */
    @Test
    public void testMainDefault() {
        ThrowingCallable code = () -> {
            GradeCollector.main(new String[]{});
        };
        
        assertThatCode(code).doesNotThrowAnyException();
    }
    
    /**
     * Test of main method, of class GradeCollector.
     */
    @Test
    public void testMainOverride() {
        ThrowingCallable code = () -> {
            GradeCollector.main(new String[]{""});
        };
        
        assertThatCode(code).isExactlyInstanceOf(UncheckedIOException.class);
    }

    /**
     * Test of getGradesAsMap method, of class GradeCollector.
     */
    @Test
    public void testGetGradesAsMap() throws IOException {
        var collector = new GradeCollector("testdata.txt");
        var grades = collector.getGradesAsMap();
        assertThat(grades.size()).isEqualTo(70);
    }

}
