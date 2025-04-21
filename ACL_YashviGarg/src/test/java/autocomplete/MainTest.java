package autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  private Main main;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
    main = new Main();
  }

  @Test
  void testProcessCommandsHelp() throws Exception {
    String[] args = {"-h"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(main.setUpOptions(), args);
    main.processCommands(cmd);
    assertTrue(outContent.toString().contains("Usage: java -jar ac.jar <option> <input>"));
  }

  @Test
  void testProcessCommandsVersion() throws Exception {
    String[] args = {"-v"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(main.setUpOptions(), args);
    main.processCommands(cmd);

    assertTrue(outContent.toString().contains(VersionUtil.getApplicationVersion()));
  }

  @Test
  void testProcessCommandsUnionWithoutDictionary() throws ParseException {
    String[] args = {"--union"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(main.setUpOptions(), args);
    main.processCommands(cmd);
    assertTrue(errContent.toString().contains("Error: '--union' requires '--dictionary' option"));
  }
  @Test
  void testSetUpOptions() {
    Options options = main.setUpOptions();
    assertNotNull(options.getOption("h"), "Help option should be configured");
    assertNotNull(options.getOption("v"), "Version option should be configured");
    assertNotNull(options.getOption("d"), "Dictionary option should be configured");
    assertNotNull(options.getOption("u"), "Union option should be configured");

    Option dictionary = options.getOption("d");
    assertTrue(dictionary.hasArg(), "Dictionary option should require an argument");
    assertEquals("filename", dictionary.getArgName(), "Arg name should be 'filename'");
  }

  @Test
  void testProcessCommandsDictionaryOnly() throws ParseException {
    String[] args = {"-d", Constants.TEST_DICTIONARY, "agrivoltaics"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(main.setUpOptions(), args);
    main.processCommands(cmd);
    assertFalse(errContent.toString().contains("Error"));
  }

  @Test
  void testProcessCommandsWithDictionaryAndUnion() throws ParseException {
    String[] args = {"-d", Constants.TEST_DICTIONARY, "--union", "agrivoltaics"};
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(main.setUpOptions(), args);
    main.processCommands(cmd);
    assertFalse(errContent.toString().contains("Error"));
  }

  @Test
  void testExecuteAutoCompletionWithNoInput() {
    String[] args = {};
    ACLImplementation acl = new ACLImplementation();
    main.executeAutoCompletion(acl, args);
    assertTrue(errContent.toString().contains("Error: Missing <input>"));
  }

  @Test
  void testExecuteAutoCompletionWithResults() {
    String[] args = {"apple"};
    ACLImplementation acl = new ACLImplementation();
    main.executeAutoCompletion(acl, args);
    assertTrue(outContent.toString().contains("Auto-completion results for 'apple':"));
  }

  @Test
  void testExecuteAutoCompletionWithNoResults() {
    String[] args = {"wokery"};
    ACLImplementation acl = new ACLImplementation();
    main.executeAutoCompletion(acl, args);
    assertTrue(errContent.toString().contains("No auto-completion candidates found for 'wokery'"));
  }
}