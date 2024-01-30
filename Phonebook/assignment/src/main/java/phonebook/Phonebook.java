package phonebook;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * PhoneBook class to manage contacts.
 *
 * @author urs
 */
public class Phonebook {

    List<BookEntry> entriesList;

    /**
     * Initializes your phone book.
     */
    public Phonebook() {
        entriesList = new ArrayList<>();
    }

    /**
     * Adds entry to your phone book. If an entry with this name already exists,
     * just an additional phone number is added.
     *
     * @param name   of a relative person
     * @param number belonging to the name.
     */
    public void addEntry(String name, String number) {
        boolean entryExists = entriesList.stream()
                .map(BookEntry::getName).anyMatch(p -> p.equals(name));
        if (entryExists) {
            entriesList.stream()
                    .filter(bookEntry -> bookEntry.getName().equals(name))
                    .findFirst().get().addPhoneNumber(number);
        } else {
            entriesList.add(new BookEntry(name, "", number));
        }

    }

    /**
     * Search your phone book by name and return all information about the person
     * with this name as text.
     *
     * @param name to lookup
     * @return all info about this person, or null if not found
     */
    public String searchByName(String name) {
        return entriesList.stream()
                .filter(bookEntry -> bookEntry.getName().equals(name))
                .findFirst()
                .map(BookEntry::toString).orElse(null);
    }

    /**
     * Search all information belonging to a person with the given phone number.
     *
     * @param number to search
     * @return all info about the belonging person, or null if not found.
     */
    public String searchByNumber(String number) {
        return entriesList.stream()
                .filter(entry -> entry.getPhoneNumbers().contains(number))
                .findFirst()
                .map(BookEntry::toString).orElse(null);
    }

    /**
     * Add address to name. Adds a new address if there is no address yet, otherwise
     * the address is updated.
     *
     * @param name    to add address to
     * @param address address to add
     */
    public void addAddress(String name, String address) {
        if (entriesList.stream().anyMatch(entry -> entry.getName().equals(name))) {
            entriesList.stream()
                    .filter(entry -> entry.getName().equals(name))
                    .findFirst().ifPresent(s -> s.setAddress(address));
        } else {
            entriesList.add(new BookEntry(name, address, new HashSet<>()));
        }
    }

    /**
     * Delete entry from phone book.
     *
     * @param name whose entry should be deleted.
     */
    public void deleteEntry(String name) {
        entriesList.removeIf(entry -> entry.getName().equals(name));
    }
}
