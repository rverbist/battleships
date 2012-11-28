package rmi.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import rmi.RmiClient;
import rmi.client.IClientController;

import domain.Game;
import domain.Location;
import domain.MapSlot;
import domain.Player;
import domain.Team;

public final class ServerGameController extends RmiServerController
{
    private Game _game;

    public ServerGameController(final String host, final int port) throws IOException, AlreadyBoundException
    {
        super(host, port);
        newGame();
    }

    private void newGame()
    {
        _game = new Game();
    }

    protected final IClientController getClients()
    {
        return new ClientControllerMultiCast(_clients.values());
    }

    protected final IClientController getClientsInTeam()
    {
        final List<RmiClient<IClientController>> players = new ArrayList<RmiClient<IClientController>>();
        for (final Team team : _game.getTeams())
        {
            for (final Player player : team.getPlayers())
            {
                players.add(_clients.get(player));
            }
        }
        return new ClientControllerMultiCast(players);
    }

    protected final IClientController getClientsInTeam(final Team team)
    {
        final List<RmiClient<IClientController>> players = new ArrayList<RmiClient<IClientController>>();
        for (final Player player : team.getPlayers())
        {
            players.add(_clients.get(player));
        }
        return new ClientControllerMultiCast(players);
    }

    @Override
    protected Player createPlayer(final String name)
    {
        final Player player = _game.createPlayer(name);
        try
        {
            getClients().onPlayerUnassigned(player);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
        return player;
    }

    @Override
    protected void removePlayer(Player player)
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.removePlayer(player))
        {
            try
            {
                getClients().onPlayerAssigned(player);
                getClients().onPlayerLeftTeam(player);
            }
            catch (RemoteException e)
            {
                getLogger().log(Level.SEVERE, null, e);
            }
        }
        if (_game.isEmpty())
        {
            newGame();
        }
    }

    @Override
    public String getTeamName(final int team) throws RemoteException
    {
        return _game.getTeam(team).getName();
    }

    @Override
    public Set<Player> getTeamPlayers(final int team) throws RemoteException
    {
        return _game.getTeam(team).getPlayers();
    }

    @Override
    public Set<Player> getUnassignedPlayers() throws RemoteException
    {
        return _game.getUnassignedPlayers();
    }

    @Override
    public void sendGlobalChatMessage(final Player player, final String message) throws RemoteException
    {
        final String msg = String.format("%s says: %s", player.getName(), message);
        getClients().onGlobalChatMessage(msg);
    }

    @Override
    public void joinTeam(Player player, final int team) throws RemoteException
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.joinTeam(player, team))
        {
            if (_game.setReady(player, false))
            {
                getClients().onPlayerIsReadyChanged(player);
            }
            getClients().onPlayerAssigned(player);
            getClients().onPlayerJoinedTeam(player, team);
        }
    }

    @Override
    public void leaveTeam(Player player) throws RemoteException
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.leaveTeam(player))
        {
            if (_game.setReady(player, false))
            {
                getClients().onPlayerIsReadyChanged(player);
            }
            getClients().onPlayerUnassigned(player);
            getClients().onPlayerLeftTeam(player);
        }
    }

    @Override
    public void sendTeamChatMessage(final Player player, final String message) throws RemoteException
    {
        final Team team = _game.getTeamForPlayer(player);
        if (team != null)
        {
            final String msg = String.format("%s says: %s", player.getName(), message);
            getClientsInTeam(team).onTeamChatMessage(msg);
        }
    }

    @Override
    public void setReady(Player player, final boolean isReady) throws RemoteException
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.setReady(player, isReady))
        {
            getClients().onPlayerIsReadyChanged(player);
            if (_game.isReady())
            {
                for (final Team team : _game.getTeams())
                {
                    getClientsInTeam(team).onGameStart(_game.getTeamIndex(team), team.getBoard());
                }
                getClientsInTeam().onGameTurnStart(_game.nextTurn());
            }
        }
    }

    @Override
    public void addSuggestion(Player player, final Location suggestion) throws RemoteException
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.addTeamSuggestion(player, suggestion))
        {
            final Team team = _game.getTeamForPlayer(player);
            getClientsInTeam(team).onPlayerAddSuggestion(player, suggestion);

            final Location target = team.getSuggestedLocation();
            if (target != null)
            {
                team.clearSuggestions();
                final Team opposition = _game.getOpposingTeamForPlayer(player);
                final MapSlot slot = opposition.fire(target);
                if (slot == MapSlot.Hit)
                {
                    getClientsInTeam().onTeamHit(_game.getTeamIndex(opposition), opposition.getHealth(), opposition.getMaximumHealth());
                }
                getClientsInTeam().onGameTurnEnd(_game.getTeamIndex(team), slot, target);
                getClientsInTeam().onGameTurnStart(_game.nextTurn());
            }
        }
    }

    @Override
    public void removeSuggestion(Player player) throws RemoteException
    {
        player = _game.getLocalPlayer(player);
        if (player != null && _game.removeTeamSuggestion(player))
        {
            final Team team = _game.getTeamForPlayer(player);
            getClientsInTeam(team).onPlayerRemoveSuggestion(player);
        }
    }
}
