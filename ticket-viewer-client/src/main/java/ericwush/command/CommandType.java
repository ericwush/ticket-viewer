package ericwush.command;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {

  MENU(0, "0 - Menu"), LOGIN(1, "1 - Login"), GET_TICKET(2, "2 - Get ticket"),
  LIST_TICKETS(3, "3 - List tickets"), EXIT(4, "4 - Exit");

  private final int code;
  private final String description;

  CommandType(final int code, final String description) {
    this.code = code;
    this.description = description;
  }

  public static Optional<CommandType> getByCode(final String code) {
    Integer parsedCode;
    try {
      parsedCode = Integer.valueOf(code);
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
    return Arrays.stream(CommandType.values()).filter(type -> type.code == parsedCode).findFirst();
  }

  public String getDescription() {
    return description;
  }

}
