package rmi.server;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.ArrayList;
import rmi.RmiClient;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

import rmi.client.IClientController;

/**
 * a decorator for a collection of client controllers that delegates
 * each method invocation to this internal collection of client controllers.
 * @author rverbist
 */
public final class ClientControllerMultiCast implements IClientController
{
    private final Collection<IClientController> _controllers;

    /**
     * creates a new multicast controller
     * @param controllers the client controllers to multicast to
     */
    public ClientControllerMultiCast(final Collection<RmiClient<IClientController>> controllers)
    {
        _controllers = new ArrayList<IClientController>();
        for(final RmiClient<IClientController> controller : controllers)
        {
            _controllers.add(controller.getInterface());
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onConnected(domain.Player)
     */
    @Override
    public void onConnected(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onConnected(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onDisconnected(domain.Player)
     */
    @Override
    public void onDisconnected(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onDisconnected(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGlobalChatMessage(java.lang.String)
     */
    @Override
    public void onGlobalChatMessage(final String message) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGlobalChatMessage(message);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerJoinedTeam(domain.Player, int)
     */
    @Override
    public void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerJoinedTeam(player, team);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerLeftTeam(domain.Player)
     */
    @Override
    public void onPlayerLeftTeam(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerLeftTeam(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerAssigned(domain.Player)
     */
    @Override
    public void onPlayerAssigned(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerAssigned(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerUnassigned(domain.Player)
     */
    @Override
    public void onPlayerUnassigned(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerUnassigned(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onTeamChatMessage(java.lang.String)
     */
    @Override
    public void onTeamChatMessage(final String message) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onTeamChatMessage(message);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerIsReadyChanged(domain.Player)
     */
    @Override
    public void onPlayerIsReadyChanged(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerIsReadyChanged(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameStart(int, domain.Board)
     */
    @Override
    public void onGameStart(final int team, final Board board) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameStart(team, board);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameTurnStart(int)
     */
    @Override
    public void onGameTurnStart(final int team) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameTurnStart(team);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerAddSuggestion(domain.Player, domain.Location)
     */
    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerAddSuggestion(player, location);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerRemoveSuggestion(domain.Player)
     */
    @Override
    public void onPlayerRemoveSuggestion(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerRemoveSuggestion(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameTurnEnd(int, domain.MapSlot, domain.Location)
     */
    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameTurnEnd(turn, slot, location);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onTeamHit(int, int, int)
     */
    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onTeamHit(team, health, maximumHealth);
        }
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameEnd(int)
     */
    @Override
    public void onGameEnd(final int winner) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameEnd(winner);
        }
    }
}
