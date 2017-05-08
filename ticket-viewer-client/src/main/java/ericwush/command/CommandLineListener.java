package ericwush.command;

import java.util.Observable;
import java.util.Scanner;

/**
 * I listen to the command line and notify on input.
 */
public class CommandLineListener extends Observable implements Runnable {

  private boolean listenNewCommand = true;
  private boolean exit = false;
  private String token;

  @Override
  public void run() {
    while (!exit) {
      if (listenNewCommand) {
        String commandLineString = new Scanner(System.in).nextLine();
        setChanged();
        notifyObservers(commandLineString);
      }
    }
  }

  public void setListenNewCommand(final boolean listenNewCommand) {
    this.listenNewCommand = listenNewCommand;
  }

  public void setExit(final boolean exit) {
    this.exit = exit;
  }

  public String getToken() {
    return token;
  }

  public void setToken(final String token) {
    this.token = token;
  }

}
