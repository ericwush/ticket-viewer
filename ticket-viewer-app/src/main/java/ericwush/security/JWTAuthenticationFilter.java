package ericwush.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

  private static final String HEADER_STRING = "Authorization";

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
      throws IOException, ServletException {
    String token = ((HttpServletRequest) request).getHeader(HEADER_STRING);
    TokenAuthenticationService.getAuthentication(token)
        .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
    filterChain.doFilter(request,response);
  }

}
