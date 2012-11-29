package domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import domain.util.Model;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

/**
 * a model for a team
 * @author rverbist
 */
public final class TeamModel extends Model
{
    private final ClientGameController _controller;
    private final int _team;

    public TeamModel(final ClientGameController controller, final int team)
    {
        _team = team;
        _controller = controller;

        addProperty("name", _controller.getTeamName(_team));
        addProperty("players", new TreeSet<Player>());
        addProperty("messages", new LinkedList<String>());
        _controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onPlayerJoinedTeam(final Player player, final int team)
            {
                if (_team == team)
                {
                    final Set<Player> players = getPlayers();
                    if (players.add(player))
                    {
                        notifyPropertyChanged("players", players, players);
                    }
                }
            }

            @Override
            public void onPlayerLeftTeam(final Player player)
            {
                final Set<Player> players = getPlayers();
                if (players.remove(player))
                {
                    notifyPropertyChanged("players", players, players);
                }
            }

            @Override
            public void onTeamChatMessage(final String message)
            {
                final List<String> messages = getMessages();
                if (messages.add(message))
                {
                    notifyPropertyChanged("messages", messages, messages);
                }
            }
        });
    }

    /**
     * joins the team
     */
    public void join()
    {
        _controller.joinTeam(_team);
    }

    /**
     * leaves the team
     */
    public void leave()
    {
        _controller.leaveTeam();
    }

    /**
     * sends a formatted chat message to the team
     * @param format the format string
     * @param args the format arguments
     */
    public void sendChatMessage(final String format, final String... args)
    {
        sendChatMessage(String.format(format, (Object[]) args));
    }

    /**
     * sends a chat message to the team
     * @param message the message
     */
    public void sendChatMessage(final String message)
    {
        _controller.sendTeamChatMessage(message);
    }

    /**
     * gets the name
     * @return the name of the team
     */
    public String getName()
    {
        return getProperty("name");
    }

    /**
     * gets the players
     * @return a mutable set of the players in the team
     */
    public Set<Player> getPlayers()
    {
        return getProperty("players");
    }

    /**
     * gets the messages
     * @return a mutable list of the messages in the team
     */
    public List<String> getMessages()
    {
        return getProperty("messages");
    }
}
