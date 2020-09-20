FROM java:8

# Add Author info
LABEL maintainer="manare@catholic.ac.kr"

# The application's jar file
ARG JAR_FILE=target/userinfo-service-0.0.1-SNAPSHOT.war

# Add the application's jar to the container
ADD ${JAR_FILE} user.war

# Run the jar file
ENTRYPOINT ["java","-jar","/user.war"]

