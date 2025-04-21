package autocomplete;

import static autocomplete.Constants.REGEX_ALPHABETIC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * A utility class to build a Trie data structure from
 * a file resource. The file is expected to contain a
 * list of words, each word separated by whitespace or newlines.
 */
final class TrieFileBuilder {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TrieFileBuilder() {
        throw new UnsupportedOperationException(
            "TrieFileBuilder class cannot be instantiated."
        );
    }

    /**
     * Builds a Trie data structure from a dictionary file specified by
     * the path. This method initializes a new TrieNode as the root and
     * appends words from the dictionary file.
     *
     * @param dictionaryPath The file path to the dictionary file.
     * @return A {@link TrieNode} representing the root of the
     * constructed Trie.
     * @throws RuntimeException If the dictionary file is not found
     * or an error
     * occurs during reading.
     */
    public static TrieNode buildTrieFromFile(final String dictionaryPath) {
        TrieNode node = new TrieNode();
        return appendTrieFromFile(node, dictionaryPath);
    }

    /**
     * Appends words from a specified dictionary file to an existing TrieNode.
     * This method is useful for adding more words to an existing Trie data
     * structure or combining multiple dictionaries.
     *
     * @param node The root node of the Trie to which words are to be appended.
     * @param dictionaryPath The file path to the dictionary whose words are to
     * be appended.
     * @return The updated {@link TrieNode} after appending the words.
     * @throws RuntimeException If the file does not exist or an error occurs
     * during file reading.
     */
    public static TrieNode appendTrieFromFile(
        final TrieNode node,
        final String dictionaryPath
    ) {
        InputStream inputStream = null;

        try {
            inputStream = TrieFileBuilder.class.getClassLoader()
                .getResourceAsStream(dictionaryPath);

            if (inputStream == null) {
                File file = new File(dictionaryPath);
                inputStream = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + dictionaryPath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
            inputStream,
            StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains(" ") || !line.matches(REGEX_ALPHABETIC)) {
                    throw new RuntimeException("Invalid line: \"" + line
                        + "\". Each line must contain exactly one word and "
                        + "words must contain only letters."
                    );
                }
                ACLImplementation.insert(
                    node,
                    line.toLowerCase(Locale.ENGLISH)
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(
                "Error reading resource: "
                    + dictionaryPath);
        }

        return node;
    }
}
