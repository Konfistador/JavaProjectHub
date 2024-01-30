package phonebook;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author urs
 */
public class PhonebookTest {

    Phonebook sut;

    @BeforeEach
    public void setUp() {
        sut = new Phonebook();
    }

    @Test
    public void addEntryAddsAnEntry() {
        sut.addEntry("Mino", "555985678");

        assertThat(sut.searchByName("Mino"))
                .as("Expecting new entry to be retrieved after addition.")
                .isEqualTo("Mino\t[555985678]");
        // fail("addEntryAddsAnEntry test method completed. You know what to do");
    }

    @Test
    public void addEntryAddsAdditionalNumber(){
        sut.addEntry("Mino", "555-555-555");
        sut.addEntry("Mino", "333-333-333");

        assertThat(sut.searchByName("Mino"))
                .as("Expecting add Entry to add an additional number if record is already present.")
                        .contains("Mino\t[555-555-555, 333-333-333]");
       // fail("addEntryAddsAdditionalNumber test method completed. You know what to do");
    }

    @Test
    public void addAddressUpdatesAddress() {
        sut.addAddress("Mino", "Kazbek 1");

        assertThat(sut.searchByName("Mino"))
                .as("Expecting Phonebook to add an address to a record.")
                .contains("Kazbek 1");

        //fail("addAddressUpdatesAddress test method completed. You know what to do");
    }

    @Test
    public void addAddressUpdatesAnExistingAddress(){
        sut.addAddress("Mino", "Kazbek 1");
        sut.addAddress("Mino", "Koninginnesingel 5");

        assertThat(sut.searchByName("Mino"))
                .as("Expecting addAddress() to update an already existing address.")
                        .contains("Mino\tKoninginnesingel 5");
      //  fail("addAddressUpdatesAnExistingAddress test method completed. You know what to do");
    }

    @Test
    public void deleteEntryRemovesRecord() {
        sut.addAddress("Mino", "555-555-555");

        sut.deleteEntry("Mino");

        assertThat(sut.searchByNumber("555-555-555"))
                .as("Expecting deleteEntry to remove the record.")
                .isNull();
      // fail("deleteEntryRemovesRecord test method completed. You know what to do");
    }

}
