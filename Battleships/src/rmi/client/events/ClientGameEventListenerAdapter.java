package rmi.client.events;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

/**
 * an adapter for {@link IClientGameEventListener}
 * @author rverbist
 */
public class ClientGameEventListenerAdapter implements IClientGameEventListener
{
    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onConnected(domain.Player)
     */
    @Override
    public void onConnected(final Player player)
    {
    }
    
    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onDisconnected(domain.Player)
     */
    @Override
    public void onDisconnected(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGlobalChatMessage(java.lang.String)
     */
    @Override
    public void onGlobalChatMessage(final String message)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerAssigned(domain.Player)
     */
    @Override
    public void onPlayerAssigned(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerUnassigned(domain.Player)
     */
    @Override
    public void onPlayerUnassigned(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerJoinedTeam(domain.Player, int)
     */
    @Override
    public void onPlayerJoinedTeam(final Player player, final int team)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerLeftTeam(domain.Player)
     */
    @Override
    public void onPlayerLeftTeam(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onTeamChatMessage(java.lang.String)
     */
    @Override
    public void onTeamChatMessage(final String message)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerIsReadyChanged(domain.Player)
     */
    @Override
    public void onPlayerIsReadyChanged(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameStart(int, domain.Board)
     */
    @Override
    public void onGameStart(final int team, final Board board)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameTurnStart(int)
     */
    @Override
    public void onGameTurnStart(final int turn)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerAddSuggestion(domain.Player, domain.Location)
     */
    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onPlayerRemoveSuggestion(domain.Player)
     */
    @Override
    public void onPlayerRemoveSuggestion(final Player player)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onGameTurnEnd(int, domain.MapSlot, domain.Location)
     */
    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location)
    {
    }

    /* (non-Javadoc)
     * @see rmi.client.events.IClientGameEventListener#onTeamHit(int, int, int)
     */
    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth)
    {
    }
}
