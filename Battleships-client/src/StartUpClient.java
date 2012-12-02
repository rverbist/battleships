import gui.BattleshipWindow;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.AlreadyBoundException;

import rmi.client.ClientGameController;

public class StartUpClient
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            final ClientGameController controller = new ClientGameController("localhost", 1099);
            /*
            controller.getEventController().addListener(new IClientGameEventListener()
            {
                @Override
                public void onConnected(Player player)
                {
                    System.out.println("onConnected");
                }

                @Override
                public void onPlayerJoinedTeam(Player player, int team)
                {
                    System.out.println("onPlayerJoinedTeam");
                }

                @Override
                public void onPlayerLeftTeam(Player player)
                {
                    System.out.println("onPlayerLeftTeam");
                }

                @Override
                public void onPlayerAssigned(Player player)
                {
                    System.out.println("onPlayerAssigned");
                }

                @Override
                public void onPlayerUnassigned(Player player)
                {
                    System.out.println("onPlayerUnassigned");
                }

                @Override
                public void onGlobalChatMessage(String message)
                {
                    System.out.println("onChatMessage");
                }

                @Override
                public void onTeamChatMessage(String message)
                {
                    System.out.println("onTeamChatMessage");
                }

                @Override
                public void onDisconnected(Player player)
                {
                    System.out.println("onDisconnected");
                }

                @Override
                public void onPlayerIsReadyChanged(Player player)
                {
                    System.out.println("onPlayerIsReadyChanged");
                }

                @Override
                public void onGameStart(int team, Board board)
                {
                    System.out.println("onGameStart");
                }

                @Override
                public void onGameTurnStart(int team)
                {
                    System.out.println("onGameTurnStart");
                }

                @Override
                public void onPlayerAddSuggestion(final Player player, Location location)
                {
                    System.out.println("onPlayerAddSuggestion");
                }

                @Override
                public void onPlayerRemoveSuggestion(Player player)
                {
                    System.out.println("onPlayerRemoveSuggestion");
                }

                @Override
                public void onGameTurnEnd(int turn, MapSlot slot, Location location)
                {
                    System.out.println("onGameTurnEnd");
                }

                @Override
                public void onTeamHit(int team, int health, int maximumHealth)
                {
                    System.out.println("onTeamHit");
                }

                @Override
                public void onGameEnd(int winner)
                {
                    System.out.println("onGameEnd");
                }
            });
            */

            final BattleshipWindow w = new BattleshipWindow(controller);
            w.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    try
                    {
                        controller.close();
                    }
                    catch (IOException ex)
                    {
                    }
                }
            });
            w.setVisible(true);
        }
        catch (IOException e)
        {
            System.err.println("An error has occured while starting the client");
            e.printStackTrace(System.err);
        }
        catch (AlreadyBoundException e)
        {
            System.err.println("Unable to start the client, another client is already running with the same resource name. Please try again.");
            e.printStackTrace(System.err);
        }
    }
}
