package validator;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

/**
 * Test validator with Parameterized test.
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class ValidatorTest {

    Validator sut;

    @BeforeEach
    public void setUp() {
        sut = new Validator();
    }

    // Write parameterized test method
    @ParameterizedTest
    @CsvSource({
            "'password','U8#s'",
            "'Password','8#s'",
            "'Password&', '8s'",
            "'Pass3rd&','s'",
            "'PASSWORD3@','l'",
    })
    public void invalidPasswordTest(String passwordInput, String flawEncoding) {
        var expectedMessage = Flaw.stringToFlawSet(flawEncoding).stream()
                .map(Flaw::getDescription)
                .collect(Collectors.joining("\n"));

        assertThatThrownBy(() -> sut.validate(passwordInput))
                .as("Expecting proper flaws")
                .isExactlyInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining(expectedMessage);
//        fail("invalidPasswordTest test method completed. You know what to do");
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "Password3@",
            "AffsD4@#fs5",
            "g[S]faw2efAAD"
    })
    public void validPasswordTest(String passwordInput) {
        assertThatCode(() -> sut.validate(passwordInput))
                .as("Expecting valid passwords to be validated")
                .doesNotThrowAnyException();
       // fail("validPasswordTest test method completed. You know what to do");
    }
    // Write parameterized test method
}
