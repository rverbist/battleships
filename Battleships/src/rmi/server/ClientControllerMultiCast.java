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

public final class ClientControllerMultiCast implements IClientController
{
    private final Collection<IClientController> _controllers;

    public ClientControllerMultiCast(final Collection<RmiClient<IClientController>> controllers)
    {
        _controllers = new ArrayList<IClientController>();
        for(final RmiClient<IClientController> controller : controllers)
        {
            _controllers.add(controller.getInterface());
        }
    }

    @Override
    public void onConnected(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onConnected(player);
        }
    }

    @Override
    public void onDisconnected(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onDisconnected(player);
        }
    }

    @Override
    public void onGlobalChatMessage(final String message) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGlobalChatMessage(message);
        }
    }

    @Override
    public void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerJoinedTeam(player, team);
        }
    }

    @Override
    public void onPlayerLeftTeam(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerLeftTeam(player);
        }
    }

    @Override
    public void onPlayerAssigned(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerAssigned(player);
        }
    }

    @Override
    public void onPlayerUnassigned(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerUnassigned(player);
        }
    }

    @Override
    public void onTeamChatMessage(final String message) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onTeamChatMessage(message);
        }
    }

    @Override
    public void onPlayerIsReadyChanged(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerIsReadyChanged(player);
        }
    }

    @Override
    public void onGameStart(final int team, final Board board) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameStart(team, board);
        }
    }

    @Override
    public void onGameTurnStart(final int team) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameTurnStart(team);
        }
    }

    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerAddSuggestion(player, location);
        }
    }

    @Override
    public void onPlayerRemoveSuggestion(final Player player) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onPlayerRemoveSuggestion(player);
        }
    }

    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onGameTurnEnd(turn, slot, location);
        }
    }

    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException
    {
        for (final IClientController controller : _controllers)
        {
            controller.onTeamHit(team, health, maximumHealth);
        }
    }
}
