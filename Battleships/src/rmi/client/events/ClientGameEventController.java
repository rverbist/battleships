package rmi.client.events;

import java.util.LinkedHashSet;
import java.util.Set;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

/**
 * an observable controller that exposes all events fired by server
 * @author rverbist
 */
public final class ClientGameEventController implements IClientGameEventListener
{
    private final Set<IClientGameEventListener> _listeners;

    public ClientGameEventController()
    {
        _listeners = new LinkedHashSet<IClientGameEventListener>();
    }

    /**
     * adds a listener
     * @param listener the listener to add
     */
    public void addListener(final IClientGameEventListener listener)
    {
        if (listener == this)
        {
            return; // caller is an idiot
        }
        _listeners.add(listener);
    }

    /**
     * removes a listener
     * @param listener the listener to remove
     */
    public void removeListener(final IClientGameEventListener listener)
    {
        _listeners.remove(listener);
    }

    /**
     * clears the listeners associated with this controller
     */
    public void clearListeners()
    {
        _listeners.clear();
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onConnected(domain.Player)
     */
    @Override
    public void onConnected(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onConnected(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onDisconnected(domain.Player)
     */
    @Override
    public void onDisconnected(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onDisconnected(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGlobalChatMessage(java.lang.String)
     */
    @Override
    public void onGlobalChatMessage(final String message)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGlobalChatMessage(message);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerJoinedTeam(domain.Player, int)
     */
    @Override
    public void onPlayerJoinedTeam(final Player player, final int team)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerJoinedTeam(player, team);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerLeftTeam(domain.Player)
     */
    @Override
    public void onPlayerLeftTeam(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerLeftTeam(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerAssigned(domain.Player)
     */
    @Override
    public void onPlayerAssigned(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerAssigned(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerUnassigned(domain.Player)
     */
    @Override
    public void onPlayerUnassigned(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerUnassigned(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onTeamChatMessage(java.lang.String)
     */
    @Override
    public void onTeamChatMessage(final String message)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onTeamChatMessage(message);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerIsReadyChanged(domain.Player)
     */
    @Override
    public void onPlayerIsReadyChanged(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerIsReadyChanged(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameStart(int, domain.Board)
     */
    @Override
    public void onGameStart(final int team, final Board board)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameStart(team, board);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameTurnStart(int)
     */
    @Override
    public void onGameTurnStart(final int turn)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameTurnStart(turn);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerAddSuggestion(domain.Player, domain.Location)
     */
    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerAddSuggestion(player, location);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerRemoveSuggestion(domain.Player)
     */
    @Override
    public void onPlayerRemoveSuggestion(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerRemoveSuggestion(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameTurnEnd(int, domain.MapSlot, domain.Location)
     */
    @Override
    public void onGameTurnEnd(int turn, final MapSlot slot, final Location location)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameTurnEnd(turn, slot, location);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onTeamHit(int, int, int)
     */
    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onTeamHit(team, health, maximumHealth);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameEnd(int)
     */
    @Override
    public void onGameEnd(int winner)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameEnd(winner);
        }
    }
}