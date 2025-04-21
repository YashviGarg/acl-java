package autocomplete;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to retrieve application version information.
 * <p>
 * This class provides a method to fetch the version of the application
 * from a properties file embedded within the application's resources.
 * </p>
 */
final class VersionUtil {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private VersionUtil() {
    throw new UnsupportedOperationException(
        "Constants class cannot be instantiated."
    );
  }

  /**
   * Retrieves the application's version from a properties file.
   * <p>
   * Attempts to load a properties file named 'version.properties'
   * from the classpath and extract the 'application.version' property
   * to provide the current application version.
   * </p>
   *
   * @return The application version as a string. Returns a default
   * error message if the version cannot be retrieved due to the file
   * not being found or an IO error occurring.
   */
  public static String getApplicationVersion() {
    Properties prop = new Properties();
    try (
        InputStream input = VersionUtil.class.getClassLoader().
            getResourceAsStream("version.properties")
    ) {
      if (input == null) {
        return "Version information not found";
      }
      prop.load(input);
      return prop.getProperty("application.version");
    } catch (IOException ex) {
      return "Error loading version information";
    }
  }
}

