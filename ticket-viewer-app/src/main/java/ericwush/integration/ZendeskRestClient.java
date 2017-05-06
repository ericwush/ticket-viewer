package ericwush.integration;

import javaslang.control.Try;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * RESTful client that talks to specific Zendesk APIs
 */
@Component
public class ZendeskRestClient {

  private final RestTemplate restTemplate;
  private final ZendeskConfig zendeskConfig;

  public ZendeskRestClient(final RestTemplate restTemplate, final ZendeskConfig zendeskConfig) {
    this.restTemplate = restTemplate;
    this.zendeskConfig = zendeskConfig;
  }

  public <T> Try<T> get(final String apiPath, final Class<T> clazz, final Object... uriVariables) {
    return Try.of(() ->
        restTemplate.exchange(zendeskConfig.getBaseUri() + zendeskConfig.getApiPath() + apiPath,
            HttpMethod.GET, getHttpEntity(), clazz, uriVariables).getBody());
  }

  private HttpEntity getHttpEntity() {
    return new HttpEntity<>(createHeaders(zendeskConfig.getEmail() + "/token", zendeskConfig.getApiToken()));
  }

  private HttpHeaders createHeaders(final String username, final String password) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", authHeader(username, password));
    headers.add("Content-Type", "application/json");
    headers.add("Accept", "application/json");

    return headers;
  }

  private String authHeader(final String username, final String password) {
    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
    return "Basic " + new String(encodedAuth);
  }

}
