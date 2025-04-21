package autocomplete;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ACLImplementationTest {
    private ACLImplementation aCLImplementation;

    @BeforeEach
    void setUp() {
        aCLImplementation = new ACLImplementation();
        aCLImplementation.insert("apple");
        aCLImplementation.insert("app");
        aCLImplementation.insert("application");
    }

    @Test
    void testSearchWordExists() {
        assertTrue(aCLImplementation.search("apple"),
            "The word 'apple' should exist in the Trie.");
    }

    @Test
    void testSearchWordDoesNotExist() {
        assertFalse(aCLImplementation.search("aaq"),
            "The word 'aaq' should not exist in the Trie.");
    }

    @Test
    void testWordsWithExistingPrefix() {
        List<String> words = aCLImplementation.getWordsWithPrefix("app");
        assertTrue(words.contains("app"),
            "The prefix 'app' should return the word 'app'.");
        assertTrue(words.contains("apple"),
            "The prefix 'app' should return the word 'apple'.");
        assertTrue(
                words.contains("application"),
                "The prefix 'app' should return the word 'application'.");
    }

    @Test
    void testWordsWithExistingPrefix2() {
        List<String> words = aCLImplementation.getWordsWithPrefix("acanthodea");
        assertEquals(2, words.size(), "The list should contain exactly two words.");
        List<String> expectedWords = Arrays.asList("acanthodea", "acanthodean");
        assertEquals(expectedWords, words, "The lists of words should match exactly.");
    }

    @Test
    void testWordsWithNonExistingPrefix() {
        List<String> words = aCLImplementation.getWordsWithPrefix("aaq");
        assertEquals(0, words.size(), "The prefix 'aaq' does not exist.");
    }

    @Test
    void removeWord() {
        aCLImplementation.remove("apple");
        assertFalse(aCLImplementation.search("apple"), "Word 'apple' has been removed");
    }

    @Test
    void removeWord2() {
        aCLImplementation.remove("aaq");
        assertFalse(aCLImplementation.search("aaq"), "Word 'aaq' has been removed");
    }
}
