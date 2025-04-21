package autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TrieNodeTest {

  @Test
  void testNewNodeIsNotWordEnd() {
    TrieNode node = new TrieNode();
    assertFalse(node.isWordEnd(), "Newly created TrieNode should not represent the end of a word.");
  }

  @Test
  void testSetWordEnd() {
    TrieNode node = new TrieNode();
    assertFalse(node.isWordEnd(), "Initially, node should not be the end of a word.");

    node.setWordEnd(true);
    assertTrue(node.isWordEnd(), "Node should be marked as the end of a word after setting wordEnd to true.");

    node.setWordEnd(false);
    assertFalse(node.isWordEnd(), "Node should not be marked as the end of a word after setting wordEnd to false.");
  }

  @Test
  void testChildNodeInitialization() {
    TrieNode node = new TrieNode();
    assertNotNull(node.getChildNode(), "Child node array should be initialized.");
    assertEquals(Constants.ALPHABET_SIZE, node.getChildNode().length, "Child node array should have size of ALPHABET_SIZE.");
  }

  @Test
  void testSetChildNode() {
    TrieNode node = new TrieNode();
    TrieNode[] newChildren = new TrieNode[Constants.ALPHABET_SIZE];
    node.setChildNode(newChildren);
    assertSame(newChildren, node.getChildNode(), "getChildNode should return the array set by setChildNode.");
  }
}
