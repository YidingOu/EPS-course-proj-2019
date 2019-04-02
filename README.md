# Carnegie Mellon University Engineering Privacy in Software #

# Running spring boot application
The wrapper should work with different operating systems such as:

1
./mvnw clean install
And the following command for Batch:

2
./mvnw.cmd clean install
If we don’t have the specified Maven in the wrapper properties, it’ll be downloaded and installed in the folder $USER_HOME/.m2/wrapper/dists of the system.

Let’s run our Spring-Boot project:

3
./mvnw spring-boot:run

# Use swagger to check the available restAPI
When the application is running at your localhost
Go the this url at your browser:
http://localhost:8080/swagger-ui.html#/
