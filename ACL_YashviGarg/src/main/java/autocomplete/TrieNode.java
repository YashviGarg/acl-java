package autocomplete;

/**
 * Represents a node in the Trie data structure.
 * Each node contains an array of child nodes and a
 * flag indicating the end of a word.
 */
class TrieNode {

    /**
     * An array representing the child nodes for
     * each letter ('a' to 'z'). The index 0 corresponds
     * to 'a', and index 25 corresponds to 'z'.
     */
    private TrieNode[] childNode;

    /**
     * A flag indicating whether the current node marks the end of
     * a valid word.
     * {@code true} if this node represents the end of a word,
     * {@code false} otherwise.
     */
    private boolean wordEnd;

    /**
     * Constructs a new TrieNode with no children and the
     * {@code wordEnd} flag set to {@code false}.
     */
    TrieNode() {
        this.wordEnd = false;
        this.childNode = new TrieNode[Constants.ALPHABET_SIZE];
    }

    /**
     * Retrieves the array of child nodes for this TrieNode.
     *
     * @return an array of {@link TrieNode} objects representing
     * the child nodes.
     */
    public TrieNode[] getChildNode() {
        return childNode;
    }

    /**
     * Sets the array of child nodes for this TrieNode.
     *
     * @param children an array of {@link TrieNode} objects
     * to replace the current child nodes.
     */
    public void setChildNode(final TrieNode[] children) {
        this.childNode = children;
    }

    /**
     * Checks whether this node represents the end of a valid word.
     *
     * @return {@code true} if this node is the end of a word,
     * {@code false} otherwise.
     */
    public boolean isWordEnd() {
        return wordEnd;
    }

    /**
     * Sets the flag indicating whether this node represents the
     * end of a valid word.
     *
     * @param isWordEnd {@code true} to mark this node as the end
     * of a word, {@code false} otherwise.
     */
    public void setWordEnd(final boolean isWordEnd) {
        this.wordEnd = isWordEnd;
    }
}
