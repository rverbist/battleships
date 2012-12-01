package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * represents a battleship game
 * 
 * @author rverbist
 */
@SuppressWarnings("serial")
public final class Game implements Serializable
{
    private final static int DIMENSION = 10;

    private final Set<Player> _unassigned;
    private final List<Team> _teams;
    private byte _currentTeamIndex;

    /**
     * creates a new battleships game with 2 teams of default map size
     */
    public Game()
    {
        _unassigned = new TreeSet<Player>();
        _teams = new ArrayList<Team>(2);
        _teams.add(new Team("Great Britain", BoardFactory.getInstance().create(DIMENSION)));
        _teams.add(new Team("America", BoardFactory.getInstance().create(DIMENSION)));
        Collections.shuffle(_teams);
        _currentTeamIndex = 0;
    }

    /**
     * gets the unassigned players
     * @return an unmodifiable set of players that have not been assigned a team
     */
    public Set<Player> getUnassignedPlayers()
    {
        return Collections.unmodifiableSet(_unassigned);
    }

    /**
     * adds a player to the unassigned set
     * @param player the player to add to the unassigned set
     * @return true if the player has been added to the unassigned set, false otherwise
     */
    public boolean addUnassignedPlayer(final Player player)
    {
        return _unassigned.add(player);
    }

    /**
     * remove a player from the unassigned set
     * @param player the player to remove from the unassigned set
     * @return true if the player has been removed from the unassigned set, false otherwise
     */
    public boolean removeUnassignedPlayer(final Player player)
    {
        return _unassigned.remove(player);
    }

    /**
     * clears all players from the unassigned set
     */
    public void clearUnassignedPlayers()
    {
        _unassigned.clear();
    }

    /**
     * creates a new player with the given name and adds him to the unassigned set
     * @param name the name of the player
     * @return the new player
     */
    public Player createPlayer(final String name)
    {
        final Player player = PlayerFactory.getInstance().create(name);
        _unassigned.add(player);
        return player;
    }

    /**
     * removes a player
     * @param player the player to remove
     * @return true if the player has been removed, false otherwise
     */
    public boolean removePlayer(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return (team != null && team.removePlayer(player)) || _unassigned.remove(player);
    }

    /**
     * gets a team
     * @param index the index of the team
     * @return the team with the given index
     */
    public Team getTeam(final int index)
    {
        return _teams.get(index);
    }

    /**
     * gets the index of a team
     * @param team the team to get the index from
     * @return the index of the given team
     */
    public int getTeamIndex(final Team team)
    {
        return _teams.indexOf(team);
    }

    /**
     * gets the teams
     * @return an unmodifiable list of all teams
     */
    public List<Team> getTeams()
    {
        return Collections.unmodifiableList(_teams);
    }

    /**
     * gets the current team
     * @return the team whose turn it is
     */
    public Team getCurrentTeam()
    {
        return _teams.get(_currentTeamIndex);
    }

    /**
     * joins the player to the team at the given index
     * @param player the player to add to the team
     * @param team the index of the team
     * @return true if the player has been added to the team, false otherwise
     */
    public boolean joinTeam(final Player player, final int team)
    {
        return removeUnassignedPlayer(player) && _teams.get(team).addPlayer(player);
    }

    /**
     * leaves the player from his team
     * @param player the player to leave team
     * @return true if the player has been removed from his team, false otherwise
     */
    public boolean leaveTeam(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.removePlayer(player) && addUnassignedPlayer(player);
    }

    /**
     * adds a suggestion for the given player's team
     * @param player the player whom to add a suggestion for
     * @param suggestion the suggested location
     * @return true if the suggestion has been added, false otherwise
     */
    public boolean addTeamSuggestion(final Player player, final Location suggestion)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.addSuggestion(player, suggestion);
    }

    /**
     * removes a suggestion from the given player's team
     * @param player the player whom to remove a suggestion for
     * @return true if the suggestion has been removed, false otherwise
     */
    public boolean removeTeamSuggestion(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.removeSuggestion(player);
    }

    /**
     * gets the team of the player
     * @param player the player whom to retrieve the team for
     * @return the team of the player
     */
    public Team getTeamForPlayer(final Player player)
    {
        for (final Team team : _teams)
        {
            if (team.containsPlayer(player))
            {
                return team;
            }
        }
        return null;
    }

    /**
     * gets the opposing team of the player
     * @param player the player whom to retrieve the opposite team for
     * @return the opposing team of the player
     */
    public Team getOpposingTeamForPlayer(final Player player)
    {
        if (getTeamForPlayer(player) != null)
        {
            for (final Team team : _teams)
            {
                if (!team.containsPlayer(player))
                {
                    return team;
                }
            }
        }
        return null;
    }

    /**
     * gets the local object that represents the given player
     * @param player the player to retrieve the local instance for
     * @return the local player instance
     */
    public Player getLocalPlayer(final Player player)
    {
        if (_unassigned.contains(player))
        {
            for (final Player p : _unassigned)
            {
                if (p.equals(player))
                {
                    return p;
                }
            }
        }
        for (final Team team : _teams)
        {
            if (team.containsPlayer(player))
            {
                for (final Player p : team.getPlayers())
                {
                    if (p.equals(player))
                    {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * marks the player as ready
     * @param player the player to change the ready state for
     * @param ready the ready state
     * @return true if the ready state has changed, false otherwise
     */
    public boolean setReady(final Player player, final boolean ready)
    {
        if (player.isReady() != ready)
        {
            player.setReady(ready);
            return true;
        }
        return false;
    }

    /**
     * gets the ready state of the game
     * @return true if all players in teams are ready, false otherwise
     */
    public boolean isReady()
    {
        for (final Team team : _teams)
        {
            if (!team.isReady())
            {
                return false;
            }
        }
        return !_teams.isEmpty();
    }

    /**
     * gets the empty state of the game
     * @return true if the game is empty, false otherwise
     */
    public boolean isEmpty()
    {
        for (final Team team : _teams)
        {
            if (!team.isEmpty())
            {
                return true;
            }
        }
        return _unassigned.isEmpty();
    }

    /**
     * advances the game one turn
     * @return the index of the team whose turn it is
     */
    public int nextTurn()
    {
        _currentTeamIndex += 1;
        _currentTeamIndex %= _teams.size();
        return _currentTeamIndex;
    }
    
    /**
     * gets the winning team
     * @return the winning team, null if no such team exists
     */
    public Team getWinningTeam()
    {
        final ArrayList<Team> teams = new ArrayList<Team>(_teams);
        final ListIterator<Team> iter = teams.listIterator();
        while (iter.hasNext())
        {
            final Team team = iter.next();
            if (team.getHealth() <= 0)
            {
                iter.remove();
            }
        }
        return teams.size() == 1 ? teams.get(0) : null;
    }

    /**
     * gets if the game is finished
     * @return true if the game has been finished, false otherwise
     */
    public boolean isFinished()
    {
        return getWinningTeam() != null;
    }
}
