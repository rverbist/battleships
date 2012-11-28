package rmi.client.events;

import java.util.LinkedHashSet;
import java.util.Set;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public final class ClientGameEventController implements IClientGameEventListener
{
    private final Set<IClientGameEventListener> _listeners;

    public ClientGameEventController()
    {
        _listeners = new LinkedHashSet<IClientGameEventListener>();
    }

    public void addListener(final IClientGameEventListener listener)
    {
        if (listener == this)
        {
            return; // caller is an idiot
        }
        _listeners.add(listener);
    }

    public void removeListener(final IClientGameEventListener listener)
    {
        _listeners.remove(listener);
    }

    public void clearListeners()
    {
        _listeners.clear();
    }

    @Override
    public void onConnected(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onConnected(player);
        }
    }

    @Override
    public void onDisconnected(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onDisconnected(player);
        }
    }

    @Override
    public void onGlobalChatMessage(final String message)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGlobalChatMessage(message);
        }
    }

    @Override
    public void onPlayerJoinedTeam(final Player player, final int team)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerJoinedTeam(player, team);
        }
    }

    @Override
    public void onPlayerLeftTeam(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerLeftTeam(player);
        }
    }

    @Override
    public void onPlayerAssigned(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerAssigned(player);
        }
    }

    @Override
    public void onPlayerUnassigned(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerUnassigned(player);
        }
    }

    @Override
    public void onTeamChatMessage(final String message)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onTeamChatMessage(message);
        }
    }

    @Override
    public void onPlayerIsReadyChanged(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerIsReadyChanged(player);
        }
    }

    @Override
    public void onGameStart(final int team, final Board board)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameStart(team, board);
        }
    }

    @Override
    public void onGameTurnStart(final int turn)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameTurnStart(turn);
        }
    }

    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerAddSuggestion(player, location);
        }
    }

    @Override
    public void onPlayerRemoveSuggestion(final Player player)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onPlayerRemoveSuggestion(player);
        }
    }

    @Override
    public void onGameTurnEnd(int turn, final MapSlot slot, final Location location)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onGameTurnEnd(turn, slot, location);
        }
    }

    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth)
    {
        for(final IClientGameEventListener listener : _listeners)
        {
            listener.onTeamHit(team, health, maximumHealth);
        }
    }
}