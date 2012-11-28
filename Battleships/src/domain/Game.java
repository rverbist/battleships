package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("serial")
public final class Game implements Serializable
{
    private final static int DIMENSION = 10;

    public final Set<Player> _unassigned;
    public final List<Team> _teams;
    
    public byte _currentTeamIndex;

    public Game()
    {
        _unassigned = new TreeSet<Player>();
        _teams = new ArrayList<Team>(2);
        _teams.add(new Team("Great Britain", BoardFactory.getInstance().create(DIMENSION)));
        _teams.add(new Team("America", BoardFactory.getInstance().create(DIMENSION)));
        Collections.shuffle(_teams);
        _currentTeamIndex = 0;
    }

    public Set<Player> getUnassignedPlayers()
    {
        return Collections.unmodifiableSet(_unassigned);
    }
    public boolean addUnassignedPlayer(final Player player)
    {
        return _unassigned.add(player);
    }
    public boolean removeUnassignedPlayer(final Player player)
    {
        return _unassigned.remove(player);
    }
    public void clearUnassignedPlayers()
    {
        _unassigned.clear();
    }

    public Player createPlayer(final String name)
    {
        final Player player = PlayerFactory.getInstance().create(name);
        _unassigned.add(player);
        return player;
    }
    public boolean removePlayer(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return (team != null && team.removePlayer(player)) || _unassigned.remove(player);
    }
    
    public Team getTeam(final int index)
    {
        return _teams.get(index);
    }
    public int getTeamIndex(final Team team)
    {
        return _teams.indexOf(team);
    }
    public List<Team> getTeams()
    {
        return Collections.unmodifiableList(_teams);
    }
    public Team getCurrentTeam()
    {
        return _teams.get(_currentTeamIndex);
    }
    public boolean joinTeam(final Player player, final int team)
    {
        return removeUnassignedPlayer(player) && _teams.get(team).addPlayer(player);
    }
    public boolean leaveTeam(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.removePlayer(player) && addUnassignedPlayer(player);
    }
    public boolean addTeamSuggestion(final Player player, final Location suggestion)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.addSuggestion(player, suggestion);
    }
    public boolean removeTeamSuggestion(final Player player)
    {
        final Team team = getTeamForPlayer(player);
        return team != null && team.removeSuggestion(player);
    }
    
    public Team getTeamForPlayer(final Player player)
    {
        for(final Team team : _teams)
        {
            if (team.containsPlayer(player))
            {
                return team;
            }
        }
        return null;
    }
    
    public Team getOpposingTeamForPlayer(final Player player)
    {
        if (getTeamForPlayer(player) != null)
        {
            for(final Team team : _teams)
            {
                if (!team.containsPlayer(player))
                {
                    return team;
                }
            }
        }
        return null;
    }
    
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
        for(final Team team : _teams)
        {
            if (team.containsPlayer(player))
            {
                for(final Player p : team.getPlayers())
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
    
    public boolean setReady(final Player player, final boolean ready)
    {
        if (player.isReady() != ready)
        {
            player.setReady(ready);
            return true;
        }
        return false;
    }
    public boolean isReady()
    {
        for(final Team team : _teams)
        {
            if (!team.isReady())
            {
                return false;
            }
        }
        return !_teams.isEmpty();
    }
    
    public boolean isEmpty()
    {
        for(final Team team : _teams)
        {
            if (!team.isEmpty())
            {
                return true;
            }
        }
        return _unassigned.isEmpty();
    }
    
    public int nextTurn()
    {
        _currentTeamIndex += 1;
        _currentTeamIndex %= _teams.size();
        return _currentTeamIndex;
    }
}
