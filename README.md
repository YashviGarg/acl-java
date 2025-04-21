# **Auto-Completion Library (ACL)**

This project implements an Auto-Completion Library (ACL) using a Trie data structure. It provides methods for inserting, searching, retrieving words by prefix, and removing words.

## **How to Use the Library as a Dependency**

To use this library in your project, add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>io.github.yashvigarg</groupId>
  <artifactId>ACL_YashviGarg</artifactId>
  <version>2.0.0</version>
</dependency>
```
--- 

## **How to Check Out and Build the Library**

### **1. Clone the Repository**

```bash
git clone https://github.com/CS6510-SEA-SP25/hw3-YashviGarg.git
cd hw3-YashviGarg/ACL_YashviGarg
```

### **2. Build the Project**

Run the following Maven command:

```bash
mvn clean install
```
--- 

## **Generate Code Documentation (Javadoc)**

To generate Javadoc for the project, run:

```bash
mvn javadoc:javadoc
```

The generated documentation can be found in:
```bash
target/site/apidocs/index.html
```

Open index.html in your browser to view the Javadoc.

--- 

## **Run All Tests**

Run the following command to execute all unit tests:

```bash
mvn test
```

The results will be displayed in the console, and detailed test reports can be found at:

```bash
target/surefire-reports/
```

--- 

## **Generate Test Coverage Reports**

The project uses JaCoCo to generate test coverage reports. Run the following command:

```bash
mvn verify
```

Coverage reports will be available in:

```bash
target/site/jacoco/index.html
```

Open index.html in your browser to view the coverage metrics. The project requires at least 70% coverage for both lines and branches.

--- 

## **Generate Coding Style Reports**
The project uses Checkstyle to enforce coding standards. To generate Checkstyle reports, run:

```bash
mvn checkstyle:checkstyle
```

he report will be generated at:
```bash
target/site/checkstyle.html
```

--- 

## **Generate Static Analysis Report with SpotBugs**

The project uses SpotBugs for static analysis to detect potential bugs and code issues. Follow these steps to generate and review the SpotBugs report:

### **1. Run `mvn clean install`**

```bash
mvn clean install
```
### **2. Run SpotBugs:**

This will generate a SpotBugs report in the target/site directory (e.g., target/site/spotbugs.html). You can do this using:

```bash
mvn spotbugs:spotbugs
```

### **3. Run `mvn site`:**

This will incorporate the SpotBugs report into the Maven site.

```bash
mvn site
```


If you'd like to review errors interactively, open the SpotBugs GUI to inspect issues:

```bash
mvn spotbugs:gui
```

--- 

## **How to Generate a JAR Package**

To generate a JAR file for the library, run:

```bash
mvn package
```

The JAR file will be located in:

```bash
target/ACL_YashviGarg-2.0.0.jar
```
--- 

## **Using the Docker Image**

This section explains how to use the Docker image of the Auto-Completion Library (ACL) that is available on DockerHub. The Docker image allows users to run the ACL application without manually installing Java or other dependencies.

### **Prerequisites**

Docker must be installed on your machine. To install Docker, follow the instructions on the [Docker Official Site](https://www.docker.com/get-started).

### **Pulling the Docker Image**

To pull the latest version of the ACL Docker image from DockerHub, use the following command:

```bash
docker pull yashvigarg/acl:v2.0.0
```

To pull the Docker image for the appropriate platform, use the --platform option to specify your system's architecture. Example:

```bash
docker pull --platform <platform> yashvigarg/acl:v2.0.0
```

### **Running the Docker Image**

To run the ACL application using the Docker image, use the following command:

```bash
docker run yashvigarg/acl:v2.0.0
```

To run it for a specific architecture:

```bash
docker run --platform <platform> yashvigarg/acl:v2.0.0
```

The following command will generate auto-completion results for the prefix "ag" using the built-in dictionary.

```bash
docker run yashvigarg/acl:v2.0.0 ag
```

### **Docker Command Options**

The ACL application supports several command-line options to enhance its functionality:

- `--help`: Displays help information about how to use the application and all its options. This is useful for getting acquainted with the functionality and options available.

  Example:
  ```bash
  docker run yashvigarg/acl:v2.0.0 --help
  ```

- `--version`: Returns the program’s version. This can be helpful to confirm the version of the ACL application you are currently using.

  Example:
  ```bash
  docker run yashvigarg/acl:v2.0.0 --version
  ```

- `--dictionary|-d <filename>`: Specifies the path to a text file that contains English words (one word per line). This dictionary will override the built-in dictionary and be used exclusively for searching auto-completion candidates.

  Example:
  ```bash
  docker run -v /path/to/your/desktop:/host yashvigarg/acl:v2.0.0 --dictionary /host/new_words_alpha.txt ag
  ```

- `--union|-u`: This option is only valid when the --dictionary option is also given. It indicates that the text file specified by the --dictionary option should be added to the built-in dictionary. Candidates for auto-completion are calculated over the union of both the built-in and the provided dictionary.

  Example:
  ```bash
  docker run -v /path/to/your/desktop:/host yashvigarg/acl:v2.0.0 --dictionary /host/new_words_alpha.txt --union ag
  ```
--- 

### **DockerHub Repository**

For more details on the Docker image versions and to access the Docker image directly, visit the DockerHub repository:

🔗 [Docker Image on DockerHub](https://hub.docker.com/repository/docker/yashvigarg/acl/general)

--- 

## Published Maven Central Package

Once the library is published on Maven Central, you can access it using the link below:

🔗 [ACL_YashviGarg on Maven Central](https://central.sonatype.com/artifact/io.github.yashvigarg/ACL_YashviGarg)

---

## Sample Usage

Here’s an example of how to use the Auto-Completion Library (ACL) in a Java project:

```java
import autocomplete.ACLImplementation;

public class Main {
    public static void main(String[] args) {
        // Initialize the Auto-Completion Library
        ACLImplementation acl = new ACLImplementation();

        // Insert words into the library
        acl.insert("hello");
        acl.insert("help");
        acl.insert("hero");

        // Search for a word
        System.out.println(acl.search("hello")); // true

        // Retrieve words with a given prefix
        System.out.println(acl.getWordsWithPrefix("he")); // [hello, help, hero]

	// Remove a word
	acl.remove("help");

    }
}
```
---
