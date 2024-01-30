package nl.fontys.sebivenlo.library;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        DefaultLibrary lib = new DefaultLibrary(Book.loadFromFile("library.csv"));

        lib.booksMatchSearchTerm("martin").forEach(System.out::println);
    }
}
