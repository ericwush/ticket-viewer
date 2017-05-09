# Ticket Viewer Client
Ticket Viewer Client provides command line (textual) UI to view tickets by calling the ticket viewer app's REST APIs.

The client is implemented by using `Observer Pattern` which listens to user's command line input and the right command will be executed.

### Prerequisite
Java 8 - JDK or JRE 8 needs to be installed

### How to build
In project directory: 
* MAC
```sh
$ ./gradlew clean build
```
* Windows
```sh
$ gradlew.bat clean build
```
Artifact is built into build/libs folder

### How to run
```
java -jar <path-to>/ticket-viewer-client.jar
```

### Input

Choose from menu
```
0 - Menu
1 - Login
2 - Get ticket
3 - List tickets
4 - Exit
```
