package ericwush.helper;

import ericwush.model.Ticket;
import ericwush.model.dto.ZendeskRequest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestHelper {

  public static ZendeskRequest.Request createZendeskRequest() {
    ZendeskRequest.Request request = new ZendeskRequest.Request();
    request.setId(100L);
    request.setAssigneeId(1999L);
    request.setOrganizationId(2000L);
    request.setRequesterId(3000L);
    request.setDescription("description");
    request.setStatus("Open");
    request.setSubject("Can't login");
    request.setUrl("http://someurl");
    request.setCreatedAt(LocalDateTime.of(2017, 5, 1, 10, 00, 00));
    request.setUpdatedAt(LocalDateTime.of(2017, 5, 1, 11, 10, 00));

    return request;
  }

  public static void assertTicket(final Ticket ticket) {
    assertThat(ticket.getId(), is(100L));
    assertThat(ticket.getSubject(), is("Can't login"));
    assertThat(ticket.getDescription(), is("description"));
    assertThat(ticket.getStatus(), is("Open"));
    assertThat(ticket.getUpdatedAt(), is(LocalDateTime.of(2017, 5, 1, 11, 10, 0)));
  }

}
