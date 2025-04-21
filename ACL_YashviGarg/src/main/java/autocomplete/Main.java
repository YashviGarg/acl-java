package autocomplete;

import static autocomplete.Constants.HELP_FORMATTER_USAGE_DIRECTION;
import static autocomplete.Constants.INCORRECT_UNION_USAGE_ERROR;
import static autocomplete.Constants.MISSING_INPUT_ERROR;
import static autocomplete.Constants.OPTION_DICTIONARY;
import static autocomplete.Constants.OPTION_DICTIONARY_ARGUMENT;
import static autocomplete.Constants.OPTION_DICTIONARY_DESCRIPTION;
import static autocomplete.Constants.OPTION_DICTIONARY_SHORT;
import static autocomplete.Constants.OPTION_HELP;
import static autocomplete.Constants.OPTION_HELP_DESCRIPTION;
import static autocomplete.Constants.OPTION_HELP_SHORT;
import static autocomplete.Constants.OPTION_UNION;
import static autocomplete.Constants.OPTION_UNION_DESCRIPTION;
import static autocomplete.Constants.OPTION_UNION_SHORT;
import static autocomplete.Constants.OPTION_VERSION;
import static autocomplete.Constants.OPTION_VERSION_DESCRIPTION;
import static autocomplete.Constants.OPTION_VERSION_SHORT;

import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class providing a command-line interface
 * for the auto-completion library.
 */
public final class Main {

  /**
   * A {@link HelpFormatter} instance used to format and display
   * command-line help messages.
   *
   * <p>This static field allows for centralized configuration of help
   * formatting across the application, ensuring consistency in the
   * appearance of help messages.</p>
   */
  private final HelpFormatter formatter;

  /**
   * Constructs a new instance of the {@code Main} class.
   * <p>
   * This constructor initializes the {@link HelpFormatter} instance,
   * which is used to format and display help messages for the
   * command-line interface.
   * </p>
   */
  public Main() {
    formatter = new HelpFormatter();
  }

  /**
   * Entry point of the application. Parses command-line arguments
   * to determine the operation mode.
   *
   * @param args the command line arguments passed to the program
   */
  public static void main(final String[] args) {
    Main main = new Main();
    Options options = main.setUpOptions();
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, args);
      main.processCommands(cmd);
    } catch (ParseException e) {
      System.err.println("Error: " + e.getMessage());
      main.printHelpFormatter();
    }
  }

  /**
   * Configures and returns the available command-line options
   * for the application.
   * <p>
   * This method sets up options for help, version, dictionary file,
   * and union functionality, each with corresponding short and long
   * flags, descriptions, and arguments (if applicable).
   * </p>
   *
   * @return an {@link Options} object containing all supported
   * command-line options.
   */
  public Options setUpOptions() {
    Options options = new Options();
    options.addOption(
        Option.builder(OPTION_HELP_SHORT)
            .longOpt(OPTION_HELP)
            .desc(OPTION_HELP_DESCRIPTION)
            .build()
    );
    options.addOption(
        Option.builder(OPTION_VERSION_SHORT)
            .longOpt(OPTION_VERSION)
            .desc(OPTION_VERSION_DESCRIPTION)
            .build()
    );
    options.addOption(
        Option.builder(OPTION_DICTIONARY_SHORT).
            longOpt(OPTION_DICTIONARY)
            .hasArg().argName(OPTION_DICTIONARY_ARGUMENT)
            .desc(OPTION_DICTIONARY_DESCRIPTION)
            .build()
    );
    options.addOption(
        Option.builder(OPTION_UNION_SHORT)
            .longOpt(OPTION_UNION)
            .desc(OPTION_UNION_DESCRIPTION)
            .build());
    return options;
  }

  /**
   * Processes the command-line options and executes corresponding actions.
   * <p>
   * Handles options like displaying help, showing version information,
   * and processing dictionaries for auto-completion. Validates required
   * options and executes the auto-completion logic based on user input.
   * </p>
   *
   * @param cmd the {@link CommandLine} object containing parsed
   * command-line options.
   */
  public void processCommands(
      final CommandLine cmd
  ) {
    try {
      if (cmd.hasOption(OPTION_HELP)) {
        printHelpFormatter();
        return;
      }
      if (cmd.hasOption(OPTION_VERSION)) {
        System.out.println(VersionUtil.getApplicationVersion());
        return;
      }
      if (cmd.hasOption(OPTION_UNION) && !cmd.hasOption(OPTION_DICTIONARY)) {
        System.err.println(INCORRECT_UNION_USAGE_ERROR);
        printHelpFormatter();
        return;
      }
      ACLImplementation acl = new ACLImplementation();
      if (cmd.hasOption(OPTION_DICTIONARY)) {
        String fileName = cmd.getOptionValue(OPTION_DICTIONARY);
        acl = new ACLImplementation(fileName, !cmd.hasOption(OPTION_UNION));
      }
      executeAutoCompletion(acl, cmd.getArgs());

    } catch (RuntimeException e) {
      System.err.println("Error: " + e.getMessage());
      printHelpFormatter();
    }
  }

  /**
   * Executes the auto-completion logic for the provided input
   * prefixes.
   * <p>
   * This method uses the given {@link ACLImplementation} instance
   * to retrieve words matching the specified prefixes and prints
   * the results. If no input is provided, it displays an error
   * message and the help formatter.
   * </p>
   *
   * @param acl  the {@link ACLImplementation} instance used for
   *             retrieving words with matching prefixes.
   * @param args the array of input prefixes for which
   *             auto-completion results are to be generated.
   */
  public void executeAutoCompletion(
      final ACLImplementation acl,
      final String[] args
  ) {
    if (args.length < 1) {
      System.err.println(MISSING_INPUT_ERROR);
      printHelpFormatter();
      return;
    }

    for (String prefix : args) {
      List<String> results = acl.getWordsWithPrefix(prefix);
      if (!results.isEmpty()) {
        System.out.println(
            "Auto-completion results for '" + prefix
                + "': " + String.join(", ", results));
      } else {
        System.err.println("No auto-completion candidates found for '"
            + prefix + "'. Please try a different search term.");
      }
    }
  }

  /**
   * Displays the help message for the application.
   * <p>
   * This method prints the usage instructions and available
   * command-line options to guide the user on how to use the
   * application effectively.
   * </p>
   */
  public void printHelpFormatter() {
    formatter.printHelp(
        HELP_FORMATTER_USAGE_DIRECTION,
        setUpOptions()
    );
  }
}
