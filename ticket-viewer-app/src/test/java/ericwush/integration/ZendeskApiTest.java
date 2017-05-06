package ericwush.integration;

import ericwush.model.dto.ZendeskRequest;
import javaslang.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
    ZendeskRequest request = tryRequest.get();
    assertThat(request.getId(), is(3L));
    assertThat(request.getSubject(), is("Test Ticket"));
    assertThat(request.getDescription(), is("This is a test ticket"));
    assertThat(request.getStatus(), is("open"));
  }

}