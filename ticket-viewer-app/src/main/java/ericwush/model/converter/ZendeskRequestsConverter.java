package ericwush.model.converter;

import ericwush.model.Ticket;
import ericwush.model.Tickets;
import ericwush.model.dto.ZendeskRequests;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Component
public class ZendeskRequestsConverter implements Function<ZendeskRequests, Tickets> {

  private static final String FIND_PAGE = "page=(\\d*)";
  private final ZendeskRequestConverter requestConverter;

  public ZendeskRequestsConverter(final ZendeskRequestConverter requestConverter) {
    this.requestConverter = requestConverter;
  }

  @Override
  public Tickets apply(final ZendeskRequests requests) {
    List<Ticket> tickets = Arrays.stream(requests.getRequests()).map(requestConverter).collect(toList());
    Optional<Long> nextPage = Optional.ofNullable(requests.getNextPage()).map(this::getPageId);
    Optional<Long> previousPage = Optional.ofNullable(requests.getPreviousPage()).map(this::getPageId);
    return new Tickets(tickets.toArray(new Ticket[0]), nextPage, previousPage);
  }

  private long getPageId(final String url) {
    long page = 0;
    Pattern pattern = Pattern.compile(FIND_PAGE);
    Matcher matcher = pattern.matcher(url);
    if (matcher.find()) {
      page = Long.valueOf(matcher.group(1));
    }
    return page;
  }

}
