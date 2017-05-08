package ericwush.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class TokenAuthenticationService {
  private static long EXPIRATION_TIME;
  private static String SECRET;
  private static final String TOKEN_PREFIX = "Bearer";

  public static String createToken(final String username) {
    return TOKEN_PREFIX + " " + Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  public static Optional<Authentication> getAuthentication(final String token) {
    try {
      return Optional.ofNullable(token)
          .map(t ->
              Jwts.parser()
                  .setSigningKey(SECRET)
                  .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                  .getBody()
                  .getSubject())
          .map(user -> new UsernamePasswordAuthenticationToken(user, null, emptyList()));
    } catch (ExpiredJwtException e) {
      return Optional.empty();
    }
  }

  @Value("${jwt.secret}")
  public void setSecret(final String secret) {
    SECRET = secret;
  }

  @Value("${jwt.expiration_time}")
  public void setExpirationTime(final long expirationTime) {
    EXPIRATION_TIME = expirationTime;
  }

}
