package ericwush.model;

import java.util.Optional;

public class Tickets {

  private final Ticket[] tickets;
  private final Optional<Long> nextPage;
  private final Optional<Long> previousPage;

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
