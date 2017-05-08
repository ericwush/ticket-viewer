package ericwush.command;

import ericwush.client.Ticket;
import ericwush.client.TicketViewerClient;
import javaslang.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

public class GetTicketCommand implements InteractiveCommand {

  private final static CommandType TYPE = CommandType.GET_TICKET;
  private final TicketViewerClient client;

  public GetTicketCommand(final TicketViewerClient client) {
    this.client = client;
  }

  @Override
  public void update(final Observable listener, final Object commandLineString) {
    if (isCommand((String) commandLineString)) {
      updateListenerAndExecute((CommandLineListener) listener);
    }
  }

  private long getTicketId() {
    try {
      System.out.print("ticket id: ");
      return new Scanner(System.in).nextLong();
    } catch (NoSuchElementException e) {
      return getTicketId();
    }
  }

  @Override
  public CommandType getType() {
    return TYPE;
  }

  @Override
  public void execute(final CommandLineListener listener) {
    Try<Ticket> maybeTicket = client.getTicket(getTicketId(), listener.getToken());
    Ticket ticket = maybeTicket.getOrElseThrow(e -> {
      if (e instanceof HttpClientErrorException &&
          ((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.FORBIDDEN)) {
        return new IllegalStateException("invalid/expired token, please login");
      } else if (e instanceof ResourceAccessException) {
        return new IllegalStateException("could not connect to server. Has server started?");
      }
      System.out.println(e.getClass().getName());
      System.out.println(e.getMessage());
      return new IllegalStateException("unknown error");
    });
    System.out.println(ticket);
  }
}
