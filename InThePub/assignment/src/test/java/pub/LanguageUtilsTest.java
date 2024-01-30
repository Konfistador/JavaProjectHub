/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pub;

import java.util.Locale;
import java.util.ResourceBundle;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bonajo
 */
public class LanguageUtilsTest {

    /**
     * Test of getMessageOrDefault method, of class LanguageUtils.
     */
    @Test
    public void testGetMessage() {
        var bundle = ResourceBundle.getBundle( "pub.inthepub", Locale.US );
        String result = LanguageUtils.getMessageOrDefault(bundle, "hello", "Hello");
        assertThat(result)
                .as("Language utils should return message")
                .isEqualTo("Hello");
    }
    
    /**
     * Test of getMessageOrDefault method, of class LanguageUtils.
     */
    @Test
    public void testGetDefault() {
        Locale.setDefault(Locale.US);
        var bundle = ResourceBundle.getBundle( "pub.inthepub", Locale.CHINESE );
        String result = LanguageUtils.getMessageOrDefault(bundle, "hello", "Hello");
        assertThat(result)
                .as("Language utils should return default message")
                .isEqualTo("Hello");
    }
    
    /**
     * Needed for code coverage
     */
    @Test
    public void testInstance() {
        var languageUtils = new LanguageUtils();
        assertThat(languageUtils).isNotNull();
    }
    
}
