package domain;

import java.util.Set;
import java.util.TreeSet;

import domain.util.Model;

import rmi.client.ClientGameController;
import rmi.client.events.ClientGameEventListenerAdapter;

public final class UnassignedModel extends Model
{
    public UnassignedModel(final ClientGameController controller)
    {
        addProperty("players", new TreeSet<Player>());
        controller.getEventController().addListener(new ClientGameEventListenerAdapter()
        {
            @Override
            public void onPlayerAssigned(final Player player)
            {
                final Set<Player> players = getPlayers();
                if (players.remove(player))
                {
                    notifyPropertyChanged("players", players, players);
                }
            }

            @Override
            public void onPlayerUnassigned(final Player player)
            {
                final Set<Player> players = getPlayers();
                if (players.add(player))
                {
                    notifyPropertyChanged("players", players, players);
                }
            }
        });
    }

    public Set<Player> getPlayers()
    {
        return getProperty("players");
    }
}
