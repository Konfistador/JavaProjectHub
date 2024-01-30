package pub;

//@author bonm
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DrinkerTooYoungExceptionTest {
    
    @ParameterizedTest
    @CsvSource({
        "nl,NL,te jong",
        "en,US,too young",
        "es,ES,demasiado joven"
    })
    public void testLocalizedMessage(String language, String country, String message) {
        Locale.setDefault( new Locale( language, country ) );
        var description = String.format("DrinkerTooYoungException in %s_%s should contain %s", language, country, message);
        DrinkerTooYoungException ex = new DrinkerTooYoungException();
        assertThat(ex.getLocalizedMessage())
                .as(description)
                .contains(message.split(" "));
    }
}
