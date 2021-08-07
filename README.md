# navi

This is a gradle project.

It connects to sequel db. So kindly give the username and password of your local sql setup in application.properties file.

If running on a mac, **./gradlew build** will build the project and run it as a spring boot application.

It has swagger enabled on port 8080. http://localhost:8080/swagger-ui.html url can be used to run through the exposed APIS.

2 APIs have been exposed in the controller. One excepts a String body in request Body while the other expects a csv file.

log statements have been written in debug level and can be enabled if required from the appclication.properties.
