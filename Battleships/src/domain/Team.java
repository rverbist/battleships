package domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * represents a team of a battleships game
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Team implements Serializable
{
    private final String _name;
    private final Board _board;
    private final Set<Player> _players;
    private final Map<Player, Location> _suggestions;

    /**
     * creates a new team with the given name and board layout
     * @param name the name of the new team
     * @param board the board of the new team
     */
    public Team(final String name, final Board board)
    {
        _name = name;
        _board = board;
        _players = new TreeSet<Player>();
        _suggestions = new TreeMap<Player, Location>();
    }

    /**
     * gets the name
     * @return the name of this instance
     */
    public String getName()
    {
        return _name;
    }

    /**
     * gets the board
     * @return the board of this instance
     */
    public Board getBoard()
    {
        return _board;
    }

    /**
     * gets the players
     * @return an unmodifiable set of the players
     */
    public Set<Player> getPlayers()
    {
        return Collections.unmodifiableSet(_players);
    }

    /**
     * adds a player to this team
     * @param player the player to add to this team
     * @return true if the player has been added to the team, false otherwise
     */
    public boolean addPlayer(final Player player)
    {
        return _players.add(player);
    }

    /**
     * removes a player from this team
     * @param player the player to remove from this team
     * @return true if the player has been removed from the team, false otherwise
     */
    public boolean removePlayer(final Player player)
    {
        return _players.remove(player);
    }

    /**
     * checks if the team contains the specified player
     * @param player the player to check for
     * @return true if the player is part of this tema, false otherwise
     */
    public boolean containsPlayer(final Player player)
    {
        return _players.contains(player);
    }

    /**
     * clears all players in this team
     */
    public void clearPlayers()
    {
        _players.clear();
    }

    /**
     * gets the suggestions
     * @return an unmodifiable map of all suggestions for this team
     */
    public Map<Player, Location> getSuggestions()
    {
        return Collections.unmodifiableMap(_suggestions);
    }

    /**
     * adds a suggestion for the given player
     * @param player the player to add the suggestion for
     * @param location the suggested {@link Location}
     * @return true if the suggestion was added, false otherwise
     */
    public boolean addSuggestion(final Player player, final Location location)
    {
        final Location previous = _suggestions.put(player, location);
        return previous == null || !location.equals(previous);
    }

    /**
     * removes a suggestion for the given player
     * @param player the player to remove the suggestion for
     * @return true if the suggestion was removed, false otherwise
     */
    public boolean removeSuggestion(final Player player)
    {
        return _suggestions.remove(player) != null;
    }

    /**
     * clears all suggestions for this team
     */
    public void clearSuggestions()
    {
        _suggestions.clear();
    }

    /**
     * gets the health
     * @return the accumulated health of all ships on this team's board
     */
    public int getHealth()
    {
        int health = 0;
        for (final PlacedShip ship : _board.getShips())
        {
            health += ship.getHealth();
        }
        return health;
    }

    /**
     * gets the maximum health
     * @return the accumulated maximum health of all ship son this team's board
     */
    public int getMaximumHealth()
    {
        int health = 0;
        for (final PlacedShip ship : _board.getShips())
        {
            health += ship.getMaximumHealth();
        }
        return health;
    }

    /**
     * gets the ready status of this team
     * @return true if the team has members and each member is ready, false otherwise
     */
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

    /**
     * gets the empty status of this team
     * @return true if the team is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return _players.isEmpty();
    }

    /**
     * gets the suggestion {@link Location} by all members for this team
     * @return the suggestion {@link Location} by all members, null if no {@link Location} has been decided
     */
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
    
    /**
     * fires a missile at the given {@link Location}
     * @param location the {@link Location} to fire at
     * @return the slot status at the given {@link Location}
     */
    public MapSlot fire(final Location location)
    {
        return _board.fire(location);
    }
}
