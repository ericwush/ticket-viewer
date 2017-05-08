package ericwush.client;

import java.util.Optional;

public class Tickets {

  private Ticket[] tickets;
  private Optional<Long> nextPage;
  private Optional<Long> previousPage;

  public Tickets() {}

  public Tickets(final Ticket[] tickets, Optional<Long> nextPage, Optional<Long> previousPage) {
    this.tickets = tickets;
    this.nextPage = nextPage;
    this.previousPage = previousPage;
  }

  public Ticket[] getTickets() {
    return tickets;
  }

  public Optional<Long> getNextPage() {
    return nextPage;
  }

  public Optional<Long> getPreviousPage() {
    return previousPage;
  }

}
