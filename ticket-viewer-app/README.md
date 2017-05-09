# Ticket Viewer App
Ticket Viewer App provides REST APIs to view support tickets backed by Zendesk. It currently supports viewing single ticket by ticket id and list of tickets by page number (100 tickets per page).

Token is issued by the app after successful login and is required for each API call.

### Prerequisite
Java 8 - JDK or JRE 8 needs to be installed

### How to run
In project directory: 
* MAC
```sh
$ ./gradlew bootRun
```
* Windows
```sh
$ gradlew.bat bootRun
```

It might take some moments to resolve the dependencies for the first time.

### User
Default user
```
Username: admin
Password: password
```


### Framework/libs used
#### Spring Boot
Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

`web` module is used to serve as REST server and `security` module is used to authenticate API calls.

#### Javaslang
Javaslang is a functional library for Java 8+ that provides persistent data types and functional control structures.

In Javaslang, `Try` is a container for a computation which may result in an exception.

Without Javaslang, we would need to wrap the statement in a `try-catch` block. With Javaslang, we can wrap the same code in a Try instance and get a result
```
Try<Integer> result = Try.of(() -> 1 / 0);
assertTrue(result.isFailure());
```
Whether the computation was successful or not can then be inspected by choice at any point in the code. We have control over what happens after the computation, thanks to Javaslang’s `Try`.

#### JWT
JWT token is used to secure all API calls.

### Test
* Login

User must login via `http://localhost:8080/login` via `POST` below body
```
{"username":"admin","password":"password"}
```

A token will be issued after successful login. It needs to be provided via `Authorization` header for all API calls. 

Token expiration is currently set to an hour.

* Single ticket

Client can call REST endpoint `http://localhost:8080/api/tickets/{ticket_id}` via `GET` with `Authorization` header set with token issued by `Login`. The return body is produced in `application/json`.

* List tickets (100 per page)

Client can call REST endpoint `http://localhost:8080/api/tickets?page={page_number}` via `GET` with `Authorization` header set with token issued by `Login`. The return body is produced in `application/json`.