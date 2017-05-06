package ericwush.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZendeskConfig {

  @Value("${zendesk.api.baseUri}")
  private String baseUri;
  @Value("${zendesk.api.email}")
  private String email;
  @Value("${zendesk.api.token}")
  private String apiToken;
  @Value("${zendesk.api.path}")
  private String apiPath;

  public String getBaseUri() {
    return baseUri;
  }

  public void setBaseUri(final String baseUri) {
    this.baseUri = baseUri;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(final String apiToken) {
    this.apiToken = apiToken;
  }

  public String getApiPath() {
    return apiPath;
  }

  public void setApiPath(final String apiPath) {
    this.apiPath = apiPath;
  }

}
