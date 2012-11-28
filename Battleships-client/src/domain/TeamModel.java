package domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import domain.util.Model;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

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
                    // note
                    // i'm well aware that doing this creates a new set, however
                    // n will always be extremely small and this event will not
                    // occur often enough to become a performance problem
                    final Set<Player> players = new TreeSet<Player>(getPlayers());
                    if (players.add(player))
                    {
                        setPlayers(players);
                    }
                }
            }

            @Override
            public void onPlayerLeftTeam(final Player player)
            {
                // note
                // i'm well aware that doing this creates a new set, however
                // n will always be extremely small and this event will not
                // occur often enough to become a performance problem
                final Set<Player> players = new TreeSet<Player>(getPlayers());
                if (players.remove(player))
                {
                    setPlayers(players);
                }
            }

            @Override
            public void onTeamChatMessage(final String message)
            {
                // note
                // this is very far from optimal performance and memory consumption
                // might get way out of hand with huge chat buffers!
                // TODO: use an immutable linked list
                final List<String> messages = new LinkedList<String>(getMessages());
                messages.add(message);
                setMessages(messages);
            }
        });
    }

    public void join()
    {
        _controller.joinTeam(_team);
    }

    public void leave()
    {
        _controller.leaveTeam();
    }

    public void sendChatMessage(final String format, final String... args)
    {
        sendChatMessage(String.format(format, (Object[]) args));
    }

    public void sendChatMessage(final String message)
    {
        _controller.sendTeamChatMessage(message);
    }

    public String getName()
    {
        return getProperty("name");
    }

    public Set<Player> getPlayers()
    {
        return getProperty("players");
    }

    private void setPlayers(final Set<Player> players)
    {
        setProperty("players", players);
    }

    public List<String> getMessages()
    {
        return getProperty("messages");
    }

    private void setMessages(final List<String> messages)
    {
        setProperty("messages", messages);
    }
}
