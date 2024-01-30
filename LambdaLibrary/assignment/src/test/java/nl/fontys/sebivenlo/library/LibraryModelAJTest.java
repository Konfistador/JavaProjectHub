package nl.fontys.sebivenlo.library;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Use the AssertJ Library to do some tests.
 *
 * @author Jan Trienes
 */
public class  LibraryModelAJTest {

    private LibraryModel sut;
    private List<Book> books;

    @BeforeEach
    void setUp() throws Exception {
        books = new ArrayList<>();
        books = Book.loadFromFile("library.csv");
        sut = new DefaultLibrary(books);
    }

    /**
     * Use the AssertJ containsExacltyElementsOf.
     * <p>
     * Test if all elements in books are available in the same order as in the
     * imported books list.
     */
    //@Disabled( "Think TDD" )
    @Test
    void testGetBooks() {
        assertThat(sut.getBooks())
                .as("Expecting all books to be returned from the library")
                .containsExactlyElementsOf(books);
        // fail("test reached end");
    }

    //@Disabled( "Think TDD" )
    @Test
    void testBooksMatchSearchTerm() {

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(sut.booksMatchSearchTerm("Eric Freeman"))
                    .as("Expecting the proper books returned, when passing an author's name as a search term.")
                    .containsExactlyElementsOf(books.stream().filter(b -> b.getAuthor().equals("Eric Freeman")).collect(Collectors.toList()));

            softly.assertThat(sut.booksMatchSearchTerm("Head First Design Patterns"))
                    .as("Expecting the proper books to be returned, when passing title's name as a search term")
                    .containsExactlyElementsOf(books.stream().filter(b -> b.getTitle().equals("Head First Design Patterns")).collect(Collectors.toList()));

            softly.assertThat(sut.booksMatchSearchTerm("Kaczanowisk"))
                    .as("Expecting the proper books to be returned, when passing publisher's name as a search term")
                    .containsExactlyElementsOf(books.stream().filter(b -> b.getPublisher().equals("Kaczanowisk")).collect(Collectors.toList()));
        });

        //    fail( "test not yet implemented" );
    }

    //@Disabled( "Think TDD" )
    @Test
    void testAuthorsMatchSearchTerm() {
        assertThat(sut.authorsMatchSearchTerm("Eric"))
                .as("Expecting a proper list with author's name, if it matches the search term")
                .containsExactlyElementsOf(books.stream().map(Book::getAuthor).filter(author -> author.contains("Eric")).distinct().collect(Collectors.toList()));
        // fail("test not yet implemented");
    }

    /**
     * Use assertj's
     * {@code Assertions.assertThat( resultBooks ).containsExactlyInAnyOrder(....)}.
     */
    //@Disabled( "Think TDD" )
    @Test
    void testBooksMatchPredicate() {
        assertThat(sut.booksMatchPredicate((b -> b.getAuthor().contains("Eric"))))
                .as("Expecting a proper list of books, when passed a Predicate")
                .containsExactlyElementsOf(books.stream().filter(b -> b.getAuthor().contains("Eric")).collect(Collectors.toUnmodifiableList()));
        //fail("test not yet implemented");
    }

    /**
     * Test with a few books. Use default junit asserts.
     */
    //@Disabled( "Think TDD" )
    @ParameterizedTest
    @CsvSource(value = {
            "1,'Head First Design Patterns'",
            "12,'Computer Networks and Internets: With Internet Applications'",
            "14,'Practical Unit Testing with JUnit and Mockito'",
            "120, 'Null object'"}
    )
    void testGetBookById(int bookId, String title) {
        assertThat(sut.getBookById(bookId).getTitle())
                .as("Expecting a proper book to be returned, when the id is being passed")
                .isEqualTo(title);

     //   fail("test not yet implemented");
    }

    /**
     * Write test to see that a mutating (e.g. remove) operation throws an
     * UnsupportedOperationException using
     * {@code AssertJK.assertThatThrownBy(ThrowableAssert.ThrowingCallable)}.
     */
    //@Disabled( "Think TDD" )
    @Test
    void testCollectionsIsUnmodifiable() {
        List<Book> list = sut.getBooks();
        Assertions.assertThatThrownBy(() -> list.remove(0)).isInstanceOf(
                UnsupportedOperationException.class);
    }

    //@Disabled( "Think TDD" )
    @Test
    void testToString() {
        System.out.println(sut);
    }

    /**
     * Helper method to get a book from the standard set.
     *
     * @param b index in list
     * @return the chosen book
     */
    Book book(int b) {
        return books.get(b);
    }

    /**
     * Gets books with given id into array. Test helper.
     *
     * @param ids of books
     * @return the array containing the selected books.
     */
    Book[] books(int... ids) {
        Book[] result = new Book[ids.length];
        int b = 0;
        for (int i : ids) {
            result[b++] = book(i);
        }
        return result;
    }

}
