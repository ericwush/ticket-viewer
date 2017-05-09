package ericwush.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.function.Function;

public interface InteractiveCommand extends Command {

  default void updateListenerAndExecute(final CommandLineListener listener) {
    listener.setListenNewCommand(false);
    System.out.println(getType().getDescription());
    try {
      execute(listener);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("back to top level menu");
    }
    listener.setListenNewCommand(true);
  }

  void execute(CommandLineListener listener);

  default Function<Throwable, IllegalStateException> parseException() {
    return e -> {
      if (e instanceof HttpClientErrorException &&
          ((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.FORBIDDEN)) {
        return new IllegalStateException("invalid/expired token, please login");
      } else if (e instanceof ResourceAccessException) {
        return new IllegalStateException("could not connect to server. Has server started?");
      } else if (e instanceof HttpServerErrorException) {
        return new IllegalStateException("Zendesk service currently unavailable");
      }
      System.out.println(e.getClass().getName());
      System.out.println(e.getMessage());
      return new IllegalStateException("unknown error");
    };
  }

}
