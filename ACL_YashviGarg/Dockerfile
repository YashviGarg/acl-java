# Use an official base image with Java Runtime Environment (JRE)
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/ac.jar /app/ac.jar

# Set the entrypoint to launch the Java application
ENTRYPOINT ["java", "-jar", "ac.jar"]

# Set default CMD to show help if no other command is specified
CMD ["--help"]

