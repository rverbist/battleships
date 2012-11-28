package rmi.client.events;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public class ClientGameEventListenerAdapter implements IClientGameEventListener
{
    @Override
    public void onConnected(final Player player)
    {
    }
    
    @Override
    public void onDisconnected(final Player player)
    {
    }

    @Override
    public void onGlobalChatMessage(final String message)
    {
    }

    @Override
    public void onPlayerAssigned(final Player player)
    {
    }

    @Override
    public void onPlayerUnassigned(final Player player)
    {
    }

    @Override
    public void onPlayerJoinedTeam(final Player player, final int team)
    {
    }

    @Override
    public void onPlayerLeftTeam(final Player player)
    {
    }

    @Override
    public void onTeamChatMessage(final String message)
    {
    }

    @Override
    public void onPlayerIsReadyChanged(final Player player)
    {
    }

    @Override
    public void onGameStart(final int team, final Board board)
    {
    }

    @Override
    public void onGameTurnStart(final int turn)
    {
    }

    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location)
    {
    }

    @Override
    public void onPlayerRemoveSuggestion(final Player player)
    {
    }

    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location)
    {
    }

    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth)
    {
    }
}
