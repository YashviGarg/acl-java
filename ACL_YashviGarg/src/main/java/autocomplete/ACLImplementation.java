/**
 * This package contains classes for the autocomplete implementation.
 */
package autocomplete;

import static autocomplete.Constants.ALPHABET_SIZE;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of an Auto-Completion Library (ACL) using a
 * Trie data structure. Provides methods to insert words,
 * search for words, retrieve words by prefix, and remove
 * words from the Trie.
 */
public class ACLImplementation {
    /**
     * The root node of the Trie data structure.
     */
     private final TrieNode root;

    /**
     * Constructs an ACLImplementation with a default dictionary.
     * It loads words from a predefined dictionary file into the
     * Trie for immediate use.
     */
    public ACLImplementation() {
        root = TrieFileBuilder.buildTrieFromFile(Constants.DEFAULT_DICTIONARY);
    }

    /**
     * Constructs an ACLImplementation with a specific dictionary.
     * This constructor allows for the customization of the Trie
     * either by replacing the existing Trie or appending to it
     * based on the replace flag.
     *
     * @param dictionaryPath The path to the dictionary file to load.
     * @param replace If true, the existing Trie is replaced by the
     *                new dictionary; if false, the words from the
     *                new dictionary are appended to the existing Trie.
     */
    public ACLImplementation(
        final String dictionaryPath,
        final boolean replace
    ) {
        if (replace) {
            this.root = TrieFileBuilder.buildTrieFromFile(dictionaryPath);
        } else {
            this.root = TrieFileBuilder.appendTrieFromFile(
                TrieFileBuilder.buildTrieFromFile(Constants.DEFAULT_DICTIONARY),
                dictionaryPath
            );
        }
    }

    /**
     * Inserts a word into the Trie.
     *
     * @param word the word to insert into the Trie. The word is automatically
     * converted to lowercase before insertion.
     */
    public void insert(final String word) {
        TrieNode node = root;
        String lWord = word.toLowerCase(Locale.ENGLISH);
        for (char ch : lWord.toCharArray()) {
            int index = ch - 'a';
            if (node.getChildNode()[index] == null) {
                node.getChildNode()[index] = new TrieNode();
            }
            node = node.getChildNode()[index];
        }
        node.setWordEnd(true);
    }

    /**
     * Inserts a word into the Trie.
     *
     * @param word the word to insert into the Trie. The word is automatically
     * converted to lowercase before insertion.
     * @param trieNode The root node of the Trie where the word will begin
     * to be inserted
     */
    public static void insert(final TrieNode trieNode, final String word) {
        TrieNode node = trieNode;
        String lWord = word.toLowerCase(Locale.ENGLISH);
        for (char ch : lWord.toCharArray()) {
            int index = ch - 'a';
            if (node.getChildNode()[index] == null) {
                node.getChildNode()[index] = new TrieNode();
            }
            node = node.getChildNode()[index];
        }
        node.setWordEnd(true);
    }

    /**
     * Searches for a complete word in the Trie.
     *
     * @param word the word to search for. The word is
     * automatically converted to lowercase before searching.
     * @return {@code true} if the word exists in the Trie and
     * is marked as a complete word;
     * {@code false} otherwise.
     */
    public boolean search(final String word) {
        TrieNode node = root;
        String lWord = word.toLowerCase(Locale.ENGLISH);
        for (char ch : lWord.toCharArray()) {
            int index = ch - 'a';
            if (node.getChildNode()[index] == null) {
                return false;
            }
            node = node.getChildNode()[index];
        }
        return node.isWordEnd();
    }

    /**
     * Retrieves all words in the Trie that start with a given prefix.
     *
     * @param prefix the prefix to search for. The prefix is automatically
     * converted to lowercase before searching.
     * @return a list of words that start with the given prefix.
     * If no words match, returns an empty list.
     */
    public List<String> getWordsWithPrefix(final String prefix) {
        TrieNode node = root;
        String word = prefix.toLowerCase(Locale.ENGLISH);
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.getChildNode()[index] == null) {
                // Prefix not found
                return new ArrayList<>();
            }
            node = node.getChildNode()[index];
        }

        List<String> result = new ArrayList<>();
        collectWords(node, word, result);
        return result;
    }

    /**
     * Recursively collects all words starting from a
     * given TrieNode.
     *
     * @param node the starting TrieNode.
     * @param currentWord the current word being formed.
     * @param result the list to store the collected words.
     */
    private void collectWords(
        final TrieNode node,
        final String currentWord,
        final List<String> result
    ) {
        if (node.isWordEnd()) {
            result.add(currentWord);
        }

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.getChildNode()[i] != null) {
                char nextChar = (char) ('a' + i);
                collectWords(
                    node.getChildNode()[i],
                    currentWord + nextChar,
                    result);
            }
        }
    }

    /**
     * Removes a word from the Trie.
     *
     * @param word the word to remove. The word is automatically
     * converted to lowercase before removal. If the word does not
     * exist, no action is taken.
     */
    public void remove(final String word) {
        String wordLowerCase = word.toLowerCase(Locale.ENGLISH);
        if (!search(wordLowerCase)) {
            // Word does not exist, so no removal needed
            return;
        }
        removeHelper(root, wordLowerCase, 0);
    }

    /**
     * Helper function to recursively remove a word from the Trie.
     *
     * @param node the current TrieNode being processed.
     * @param word the word to remove. The word is
     * processed as lowercase.
     * @param depth the current depth of recursion
     * (character index in the word).
     * @return {@code true} if the current node can
     * be deleted; {@code false} otherwise.
     */
    private boolean removeHelper(
        final TrieNode node,
        final String word,
        final int depth
    ) {
        if (node == null) {
            return false;
        }

        // If we reach the end of the word
        if (depth == word.length()) {
            if (!node.isWordEnd()) {
                // Word does not exist
                return false;
            }
            node.setWordEnd(false);

            // Check if the node is now a leaf (no children)
            return isEmptyNode(node);
        }

        // Recur for the next character
        int index = word.charAt(depth) - 'a';
        boolean shouldDeleteCurrentNode = removeHelper(
            node.getChildNode()[index],
            word,
            depth + 1);

        if (shouldDeleteCurrentNode) {
            // Remove the child reference
            node.getChildNode()[index] = null;

            // Return true if the current node is now a leaf
            // and not the end of another word
            return isEmptyNode(node) && !node.isWordEnd();
        }
        return false;
    }

    /**
     * Checks if a TrieNode has no children.
     *
     * @param node the TrieNode to check.
     * @return {@code true} if the node has no
     * children; {@code false} otherwise.
     */
    private boolean isEmptyNode(final TrieNode node) {
        for (TrieNode child : node.getChildNode()) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
}
