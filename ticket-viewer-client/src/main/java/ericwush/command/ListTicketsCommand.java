package ericwush.command;

import ericwush.client.TicketViewerClient;
import ericwush.client.Tickets;
import javaslang.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

public class ListTicketsCommand implements InteractiveCommand {

  private final static CommandType TYPE = CommandType.LIST_TICKETS;
  private final TicketViewerClient client;

  public ListTicketsCommand(final TicketViewerClient client) {
    this.client = client;
  }

  @Override
  public void update(final Observable listener, final Object commandLineString) {
    if (isCommand((String) commandLineString)) {
      updateListenerAndExecute((CommandLineListener) listener);
    }
  }

  private long getPageNumber() {
    try {
      System.out.print("page number: ");
      return new Scanner(System.in).nextLong();
    } catch (NoSuchElementException e) {
      return getPageNumber();
    }
  }

  @Override
  public CommandType getType() {
    return TYPE;
  }

  @Override
  public void execute(final CommandLineListener listener) {
    Try<Tickets> maybeTickets = client.listTickets(getPageNumber(), listener.getToken());
    Tickets tickets = maybeTickets.getOrElseThrow(e -> {
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
    Arrays.stream(tickets.getTickets()).forEach(System.out::println);
    tickets.getPreviousPage().ifPresent(page -> System.out.println("previous page: " + page));
    tickets.getNextPage().ifPresent(page -> System.out.println("next page: " + page));
  }
}
