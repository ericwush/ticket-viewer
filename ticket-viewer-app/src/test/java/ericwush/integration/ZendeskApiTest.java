package ericwush.integration;

import ericwush.model.dto.ZendeskRequest;
import ericwush.model.dto.ZendeskRequests;
import javaslang.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for ZendeskApi, testing with Zendesk test account
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZendeskApiTest {

  @Autowired
  private ZendeskApi api;

  @Test
  public void shouldGetRequest() {
    // Given

    // When
    Try<ZendeskRequest> tryRequest = api.getRequest(3);

    // Then
    assertTrue(tryRequest.isSuccess());
    ZendeskRequest.Request request = tryRequest.get().getBody();
    assertThat(request.getId(), is(3L));
    assertThat(request.getSubject(), is("Test Ticket"));
    assertThat(request.getDescription(), is("This is a test ticket"));
    assertThat(request.getStatus(), is("open"));
  }

  @Test
  public void shouldGetRequests() {
    // Given

    // When
    Try<ZendeskRequests> tryRequests = api.getRequests(1);

    // Then
    assertTrue(tryRequests.isSuccess());
    ZendeskRequests zendeskRequests = tryRequests.get();
    ZendeskRequest.Request[] requests = zendeskRequests.getRequests();
    assertThat(requests.length, is(1));
    ZendeskRequest.Request request = requests[0];
    assertThat(request.getId(), is(3L));
    assertThat(request.getSubject(), is("Test Ticket"));
    assertThat(request.getDescription(), is("This is a test ticket"));
    assertThat(request.getStatus(), is("open"));
    assertThat(zendeskRequests.getCount(), is(1L));
    assertNull(zendeskRequests.getNextPage());
    assertNull(zendeskRequests.getPreviousPage());
  }

}