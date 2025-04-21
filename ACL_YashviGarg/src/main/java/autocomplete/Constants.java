package autocomplete;

/**
 * A utility class for storing constants used across the application.
 * <p>
 * This class is designed to provide commonly used constants such as the
 * total number of lowercase letters in the English alphabet.
 * </p>
 */
final class Constants {

  /**
   * The total number of lowercase letters in the English alphabet.
   * This constant is used to define the size of the child nodes array
   * in each TrieNode, where each index corresponds to a specific
   * lowercase letter ('a' to 'z').
   */
  public static final int ALPHABET_SIZE = 26;

  /**
   * Default path to the dictionary file used by the Trie for loading words.
   * This path points to a resource file within the project structure that
   * contains a list of words, each separated by newlines, used to populate
   * the Trie during initialization.
   */
  public static final String DEFAULT_DICTIONARY =
      "words_alpha.txt";

  /**
   * Path to the dictionary file used for testing.
   */
  public static final String TEST_DICTIONARY =
      "new_words_alpha.txt";

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private Constants() {
    throw new UnsupportedOperationException(
        "Constants class cannot be instantiated."
    );
  }

  /**
   * Option name for specifying a custom dictionary file.
   */
  public static final String OPTION_DICTIONARY = "dictionary";

  /**
   * Short flag for the dictionary option.
   */
  public static final String OPTION_DICTIONARY_SHORT = "d";

  /**
   * Argument name for the dictionary option.
   */
  public static final String OPTION_DICTIONARY_ARGUMENT = "filename";

  /**
   * Option name for combining custom and built-in dictionaries.
   */
  public static final String OPTION_UNION = "union";

  /**
   * Short flag for the union option.
   */
  public static final String OPTION_UNION_SHORT = "u";

  /**
   * Option name for displaying the help message.
   */
  public static final String OPTION_HELP = "help";

  /**
   * Short flag for the help option.
   */
  public static final String OPTION_HELP_SHORT = "h";


  /**
   * Option name for displaying version information.
   */
  public static final String OPTION_VERSION = "version";

  /**
   * Short flag for the version option.
   */
  public static final String OPTION_VERSION_SHORT = "v";

  /**
   * Description for the union option.
   */
  public static final String OPTION_UNION_DESCRIPTION =
      "Combine the specified dictionary with the built-in dictionary."
          + "\nUse like: --union --dictionary <filename> <input>";

  /**
   * Description for the dictionary option.
   */
  public static final String OPTION_DICTIONARY_DESCRIPTION  =
      "Specify the path to a dictionary file. Can be used alone "
          + "or with '--union' to combine dictionaries."
          + " \n Use like: --dictionary <filename> <input>";

  /**
   * Description for the help option.
   */
  public static final String OPTION_HELP_DESCRIPTION =
      "Displays this help message";

  /**
   * Description for the version option.
   */
  public static final String OPTION_VERSION_DESCRIPTION =
      "Display version information";

  /**
   * Usage instructions for the help formatter.
   */
  public static final String HELP_FORMATTER_USAGE_DIRECTION =
      "Usage: java -jar ac.jar <option> <input>";

  /**
   * Error message for missing input parameter.
   */
  public static final String MISSING_INPUT_ERROR =
      "Error: Missing <input> parameter";

  /**
   * Error message for incorrect use of --union option.
   */
  public static final String INCORRECT_UNION_USAGE_ERROR =
      "Error: '--union' requires '--dictionary' option";

  /**
   * Regex pattern for validating that a string contains only
   * alphabetic characters (a-z, A-Z).
   */
  public static final String REGEX_ALPHABETIC = "^[a-zA-Z]+$";
}
