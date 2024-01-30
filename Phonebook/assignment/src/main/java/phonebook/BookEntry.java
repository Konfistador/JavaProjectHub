package phonebook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author urs
 */
public class BookEntry {

    private final String name;
    private final Set<String> phoneNumbers;
    private String address;

    public BookEntry(String name, String address, Set<String> phoneNumbersList){
        this.name = name;
        this.address = address;
        this.phoneNumbers = phoneNumbersList;
    }

    public BookEntry(String name, String address, String number) {
       this(name,address,new HashSet<>(List.of(number)));
    }

    public String getName() {
        return name;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getAddress() {
        return address;
    }

    public void addPhoneNumber(String number) {
        this.phoneNumbers.add(number);
    }

    @Override
    public String toString() {
        return getName()+"\t"+ getAddress() + getPhoneNumbers().toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
