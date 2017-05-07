package ericwush.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  private static final String HEADER_STRING = "Authorization";

  public JWTLoginFilter(final String url, final AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            login.getUsername(),
            login.getPassword(),
            Collections.emptyList())
    );
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                          final FilterChain chain, final Authentication auth)
      throws IOException, ServletException {
    String token = TokenAuthenticationService.createToken(auth.getName());
    response.addHeader(HEADER_STRING, token);
  }

}
