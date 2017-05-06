package ericwush.model.converter;

import ericwush.model.Tickets;
import ericwush.model.dto.ZendeskRequest;
import ericwush.model.dto.ZendeskRequests;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;

import static ericwush.helper.TestHelper.assertTicket;
import static ericwush.helper.TestHelper.createZendeskRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ZendeskRequestsConverterTest {

  private ZendeskRequestsConverter converter;

  @Before
  public void setUp() {
    ZendeskRequestConverter requestConverter = new ZendeskRequestConverter();
    converter = new ZendeskRequestsConverter(requestConverter);
  }

  @Test
  public void shouldConvertRequests() {
    // Given
    ZendeskRequest.Request request = createZendeskRequest();

    ZendeskRequests requests = new ZendeskRequests();
    requests.setRequests(Arrays.array(request));
    requests.setCount(1L);
    requests.setNextPage("https://ericwush.zendesk.com/api/v2/requests.json?page=3");
    requests.setPreviousPage("https://ericwush.zendesk.com/api/v2/requests.json?page=1");

    // When
    Tickets tickets = converter.apply(requests);

    // Then
    assertThat(tickets.getTickets().length, is(1));
    assertTicket(tickets.getTickets()[0]);
    assertThat(tickets.getNextPage().get(), is(3L));
    assertThat(tickets.getPreviousPage().get(), is(1L));
  }

}