############################################################################################################
###                    Stage where Docker is pulling all maven dependencies                              ###
############################################################################################################
FROM maven:3.8.5-openjdk-17 AS DEPENDENCIES_AND_BUILD
# copy just pom.xml (dependencies and dowload them all for offline access later - cache layer)
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
############################################################################################################
###                     Stage where Docker is building spring boot app using maven                       ###
############################################################################################################
# copy source files and compile them (.dockerignore should handle what to copy)
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests
############################################################################################################
###         Stage where Docker is running a java process to run a service built in previous stage        ###
############################################################################################################
FROM eclipse-temurin:17-jdk as RUNNABLE
EXPOSE 8080
RUN mkdir -p /app
WORKDIR /app
COPY --from=DEPENDENCIES_AND_BUILD /tmp/target/*.jar /app/product-administration-*.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","product-administration-*.jar"]
CMD ["-start"]
############################################################################################################