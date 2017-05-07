package ericwush.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TokenAuthenticationServiceTest {

  private TokenAuthenticationService service;

  @Before
  public void setUp() {
    service = new TokenAuthenticationService();
    service.setSecret("AwesomeZendesk");
    service.setExpirationTime(60 * 10 * 1000);
  }

  @Test
  public void shouldCreateTokenAndParseToken() {
    // Given
    String token = TokenAuthenticationService.createToken("user1");

    // When
    Optional<Authentication> maybeAuth = TokenAuthenticationService.getAuthentication(token);

    // Then
    assertTrue(maybeAuth.isPresent());
    assertThat(maybeAuth.get().getPrincipal(), is("user1"));
  }

}