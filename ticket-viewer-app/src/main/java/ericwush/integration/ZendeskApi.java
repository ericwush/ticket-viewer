package ericwush.integration;

import ericwush.model.dto.ZendeskRequest;
import javaslang.control.Try;
import org.springframework.stereotype.Component;

@Component
public class ZendeskApi {

  private final ZendeskRestClient client;

  public ZendeskApi(final ZendeskRestClient client) {
    this.client = client;
  }

  public Try<ZendeskRequest> getRequest(final long id) {
    return client.get("/requests/{id}.json", ZendeskRequest.class, id);
  }

}
