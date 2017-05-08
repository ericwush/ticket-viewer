package ericwush.command;

import ericwush.client.Login;
import ericwush.client.TicketViewerClient;
import javaslang.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

public class LoginCommand implements InteractiveCommand {

  private final static CommandType TYPE = CommandType.LOGIN;
  private final TicketViewerClient client;

  public LoginCommand(final TicketViewerClient client) {
    this.client = client;
  }

  @Override
  public void update(final Observable listener, final Object commandLineString) {
    if (isCommand((String) commandLineString)) {
      updateListenerAndExecute((CommandLineListener) listener);
    }
  }

  private String getUsername() {
    try {
      System.out.print("username: ");
      return new Scanner(System.in).next();
    } catch (NoSuchElementException e) {
      return getUsername();
    }
  }

  private String getPassword() {
    try {
      System.out.print("password: ");
      return new Scanner(System.in).next();
    } catch (NoSuchElementException e) {
      return getPassword();
    }
  }

  @Override
  public CommandType getType() {
    return TYPE;
  }

  @Override
  public void execute(final CommandLineListener listener) {
    Try<String> login = client.login(new Login(getUsername(), getPassword()));
    listener.setToken(login.getOrElseThrow(e -> {
      if (e instanceof HttpClientErrorException &&
          ((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
        return new IllegalStateException("invalid username/password");
      } else if (e instanceof ResourceAccessException) {
        return new IllegalStateException("could not connect to server. Has server started?");
      }
      return new IllegalStateException("unknown error");
    }));
    System.out.println("successful login");
  }

}
