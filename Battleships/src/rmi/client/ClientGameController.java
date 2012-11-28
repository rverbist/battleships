package rmi.client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;

import rmi.client.events.ClientGameEventController;
import rmi.exceptions.NotConnectedException;
import rmi.server.IServerController;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public final class ClientGameController extends RmiClientController
{
    private final ClientGameEventController _events;
    
    private Player _player;
    private int _team;
    private int _currentTurn;
    private Board _board;

    public ClientGameController(final String host, final int port) throws IOException, AlreadyBoundException
    {
        super(host, port);
        _events = new ClientGameEventController();
    }

    public ClientGameEventController getEventController()
    {
        return _events;
    }
    
    public Player getPlayer()
    {
        return _player;
    }
    
    public int getTeam()
    {
        return _team;
    }
    
    public int getCurrentTurn()
    {
        return _currentTurn;
    }
    
    public Board getBoard()
    {
        return _board;
    }

    private void throwIfNotConnected()
    {
        if (_player == null)
        {
            throw new NotConnectedException();
        }
    }

    @Override
    public void close() throws IOException
    {
        if (_player != null)
        {
            final IServerController server = getServer().getInterface();
            server.disconnect(_player);
        }
        super.close();
    }

    /*
     * SERVER OPERATIONS methods that can be used to mutate server state
     */

    public void sendChatMessage(final String message)
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.sendGlobalChatMessage(_player, message);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void joinTeam(final int team)
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.joinTeam(_player, team);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void leaveTeam()
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.leaveTeam(_player);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void sendTeamChatMessage(final String message)
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.sendTeamChatMessage(_player, message);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void toggleReady()
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.setReady(_player, !_player.isReady());
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }
    
    public void addSuggestion(final Location location)
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.addSuggestion(_player, location);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }
    
    public void removeSuggestion()
    {
        throwIfNotConnected();
        try
        {
            final IServerController server = getServer().getInterface();
            server.removeSuggestion(_player);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
    }

    /*
     * SERVER GETTERS methods that can be used to query server state
     */

    public String getTeamName(final int team)
    {
        try
        {
            final IServerController server = getServer().getInterface();
            return server.getTeamName(team);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
        return "";
    }

    public Set<Player> getTeamPlayers(final int team)
    {
        try
        {
            final IServerController server = getServer().getInterface();
            return server.getTeamPlayers(team);
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
        return new TreeSet<Player>();
    }

    public Set<Player> getUnassignedPlayers()
    {
        try
        {
            final IServerController server = getServer().getInterface();
            return server.getUnassignedPlayers();
        }
        catch (RemoteException e)
        {
            getLogger().log(Level.SEVERE, null, e);
        }
        return new TreeSet<Player>();
    }

    /*
     * SERVER EVENTS these methods will be called by the server
     */

    @Override
    public void onConnected(final Player player) throws RemoteException
    {
        _player = player;
        _events.onConnected(player);
    }

    @Override
    public void onDisconnected(final Player player) throws RemoteException
    {
        _events.onDisconnected(player);
    }

    @Override
    public void onGlobalChatMessage(final String message) throws RemoteException
    {
        _events.onGlobalChatMessage(message);
    }

    @Override
    public void onPlayerAssigned(final Player player) throws RemoteException
    {
        _events.onPlayerAssigned(player);
    }

    @Override
    public void onPlayerUnassigned(final Player player) throws RemoteException
    {
        _events.onPlayerUnassigned(player);
    }

    @Override
    public void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException
    {
        _events.onPlayerJoinedTeam(player, team);
    }

    @Override
    public void onPlayerLeftTeam(final Player player) throws RemoteException
    {
        _events.onPlayerLeftTeam(player);
    }

    @Override
    public void onTeamChatMessage(final String message) throws RemoteException
    {
        _events.onTeamChatMessage(message);
    }

    @Override
    public void onPlayerIsReadyChanged(final Player player) throws RemoteException
    {
        if (_player.equals(player))
        {
            _player = player;
        }
        _events.onPlayerIsReadyChanged(player);
    }

    @Override
    public void onGameStart(final int team, final Board board) throws RemoteException
    {
        _team = team;
        _board = board;
        _events.onGameStart(team, board);
    }

    @Override
    public void onGameTurnStart(final int turn) throws RemoteException
    {
        _currentTurn = turn;
        _events.onGameTurnStart(turn);
    }

    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException
    {
        _events.onPlayerAddSuggestion(player, location);
    }

    @Override
    public void onPlayerRemoveSuggestion(final Player player) throws RemoteException
    {
        _events.onPlayerRemoveSuggestion(player);        
    }

    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException
    {
        _events.onGameTurnEnd(turn, slot, location);    
    }

    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException
    {
        _events.onTeamHit(team, health, maximumHealth);
    }
}
