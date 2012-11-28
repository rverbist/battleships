package domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("serial")
public final class Team implements Serializable
{
    private final String _name;
    private final Board _board;
    private final Set<Player> _players;
    private final Map<Player, Location> _suggestions;

    public Team(final String name, final Board board)
    {
        _name = name;
        _board = board;
        _players = new TreeSet<Player>();
        _suggestions = new TreeMap<Player, Location>();
    }

    public String getName()
    {
        return _name;
    }

    public Board getBoard()
    {
        return _board;
    }

    public Set<Player> getPlayers()
    {
        return Collections.unmodifiableSet(_players);
    }

    public boolean addPlayer(final Player player)
    {
        return _players.add(player);
    }

    public boolean removePlayer(final Player player)
    {
        return _players.remove(player);
    }

    public boolean containsPlayer(final Player player)
    {
        return _players.contains(player);
    }

    public void clearPlayers()
    {
        _players.clear();
    }

    public Map<Player, Location> getSuggestions()
    {
        return Collections.unmodifiableMap(_suggestions);
    }

    public boolean addSuggestion(final Player player, final Location location)
    {
        final Location previous = _suggestions.put(player, location);
        return previous == null || !location.equals(previous);
    }

    public boolean removeSuggestion(final Player player)
    {
        return _suggestions.remove(player) != null;
    }

    public void clearSuggestions()
    {
        _suggestions.clear();
    }

    public Location getSuggestedLocation()
    {
        final Set<Location> suggestions = new HashSet<Location>();
        for (final Player player : _players)
        {
            final Location suggestion = _suggestions.get(player);
            if (suggestion == null)
            {
                return null;
            }
            suggestions.add(suggestion);
        }
        if (suggestions.size() == 1)
        {
            for (final Location suggestion : suggestions)
            {
                return suggestion;
            }
        }
        return null;
    }

    public int getHealth()
    {
        int health = 0;
        for (final PlacedShip ship : _board.getShips())
        {
            health += ship.getHealth();
        }
        return health;
    }

    public int getMaximumHealth()
    {
        int health = 0;
        for (final PlacedShip ship : _board.getShips())
        {
            health += ship.getMaximumHealth();
        }
        return health;
    }

    public boolean isReady()
    {
        for (final Player player : _players)
        {
            if (!player.isReady())
            {
                return false;
            }
        }
        return !isEmpty();
    }

    public boolean isEmpty()
    {
        return _players.isEmpty();
    }

    public MapSlot fire(final Location location)
    {
        return _board.fire(location);
    }
}
