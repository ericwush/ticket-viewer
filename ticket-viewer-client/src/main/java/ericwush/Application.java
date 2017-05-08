package ericwush;

import ericwush.client.TicketViewerClient;
import ericwush.command.CommandLineListener;
import ericwush.command.CommandType;
import ericwush.command.ExitCommand;
import ericwush.command.GetTicketCommand;
import ericwush.command.ListTicketsCommand;
import ericwush.command.LoginCommand;
import ericwush.command.MenuCommand;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Application {

  public static void main(final String[] args) {
    RestTemplate restTemplate = new RestTemplate();
    TicketViewerClient ticketViewerClient = new TicketViewerClient(restTemplate);

    MenuCommand menuCommand = new MenuCommand();
    LoginCommand loginCommand = new LoginCommand(ticketViewerClient);
    GetTicketCommand getTicketCommand = new GetTicketCommand(ticketViewerClient);
    ListTicketsCommand listTicketsCommand = new ListTicketsCommand(ticketViewerClient);
    ExitCommand exitCommand = new ExitCommand();
    CommandLineListener commandLineListener = new CommandLineListener();
    commandLineListener.addObserver(menuCommand);
    commandLineListener.addObserver(loginCommand);
    commandLineListener.addObserver(getTicketCommand);
    commandLineListener.addObserver(listTicketsCommand);
    commandLineListener.addObserver(exitCommand);
    new Thread(commandLineListener).start();

    Arrays.stream(CommandType.values()).forEach(commandType -> System.out.println(commandType.getDescription()));
  }

}
