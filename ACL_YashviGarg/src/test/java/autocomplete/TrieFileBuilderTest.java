package autocomplete;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

class TrieFileBuilderTest {

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<TrieFileBuilder> constructor = TrieFileBuilder.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception = assertThrows(
        InvocationTargetException.class,
        () -> constructor.newInstance(),
        "Expected to throw InvocationTargetException"
    );

    assertTrue(exception.getCause() instanceof UnsupportedOperationException,
        "Expected cause to be UnsupportedOperationException");
  }

  @Test
  void testAppendTrieFromFile_FileNotFound() {
    String dictionaryPath = "nonexistent_dictionary.txt";
    assertThrows(RuntimeException.class, () -> TrieFileBuilder.appendTrieFromFile(new TrieNode(), dictionaryPath),
        "Expected to throw when file does not exist");
  }

  @Test
  void testAppendTrieFromFile_SuccessfulAppend() {
    TrieNode node = TrieFileBuilder.appendTrieFromFile(new TrieNode(),"src/main/resources/words_alpha.txt");
    assertNotNull(node);
  }

}