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

/**
 * a concrete implementation of a battleships client
 * @author rverbist
 */
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

    /**
     * gets an event controller for this game
     * @return an event controller that exposes all events fired by the server
     */
    public ClientGameEventController getEventController()
    {
        return _events;
    }
    
    /**
     * gets the player
     * @return the local player, if any
     */
    public Player getPlayer()
    {
        return _player;
    }
    
    /**
     * gets the team
     * @return the team of the local player, if any
     */
    public int getTeam()
    {
        return _team;
    }
    
    /**
     * gets current turn
     * @return the team whose turn it currently is
     */
    public int getCurrentTurn()
    {
        return _currentTurn;
    }
    
    /**
     * gets the board
     * @return the board of the local player's team, if any
     */
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

    /**
     * gets the team name
     * @param team the index of the team
     * @return the name of the team at the given index
     * @see {@link IServerController#getTeamName}
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

    /**
     * gets the team players
     * @param team the index of the team
     * @return the name of the team at the given index
     * @see {@link IServerController#getTeamPlayers}
     */
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
    
    /**
     * gets the unassigned players
     * @return the unassigned players
     * @see {@link IServerController#getUnassignedPlayers}
     */
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

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onConnected(domain.Player)
     */
    @Override
    public void onConnected(final Player player) throws RemoteException
    {
        _player = player;
        _events.onConnected(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onDisconnected(domain.Player)
     */
    @Override
    public void onDisconnected(final Player player) throws RemoteException
    {
        _events.onDisconnected(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGlobalChatMessage(java.lang.String)
     */
    @Override
    public void onGlobalChatMessage(final String message) throws RemoteException
    {
        _events.onGlobalChatMessage(message);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerAssigned(domain.Player)
     */
    @Override
    public void onPlayerAssigned(final Player player) throws RemoteException
    {
        _events.onPlayerAssigned(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerUnassigned(domain.Player)
     */
    @Override
    public void onPlayerUnassigned(final Player player) throws RemoteException
    {
        _events.onPlayerUnassigned(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerJoinedTeam(domain.Player, int)
     */
    @Override
    public void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException
    {
        _events.onPlayerJoinedTeam(player, team);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerLeftTeam(domain.Player)
     */
    @Override
    public void onPlayerLeftTeam(final Player player) throws RemoteException
    {
        _events.onPlayerLeftTeam(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onTeamChatMessage(java.lang.String)
     */
    @Override
    public void onTeamChatMessage(final String message) throws RemoteException
    {
        _events.onTeamChatMessage(message);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerIsReadyChanged(domain.Player)
     */
    @Override
    public void onPlayerIsReadyChanged(final Player player) throws RemoteException
    {
        if (_player.equals(player))
        {
            _player = player;
        }
        _events.onPlayerIsReadyChanged(player);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameStart(int, domain.Board)
     */
    @Override
    public void onGameStart(final int team, final Board board) throws RemoteException
    {
        _team = team;
        _board = board;
        _events.onGameStart(team, board);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameTurnStart(int)
     */
    @Override
    public void onGameTurnStart(final int turn) throws RemoteException
    {
        _currentTurn = turn;
        _events.onGameTurnStart(turn);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerAddSuggestion(domain.Player, domain.Location)
     */
    @Override
    public void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException
    {
        _events.onPlayerAddSuggestion(player, location);
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onPlayerRemoveSuggestion(domain.Player)
     */
    @Override
    public void onPlayerRemoveSuggestion(final Player player) throws RemoteException
    {
        _events.onPlayerRemoveSuggestion(player);        
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameTurnEnd(int, domain.MapSlot, domain.Location)
     */
    @Override
    public void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException
    {
        _events.onGameTurnEnd(turn, slot, location);    
    }

    /* (non-Javadoc)
     * @see rmi.client.IClientController#onTeamHit(int, int, int)
     */
    @Override
    public void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException
    {
        _events.onTeamHit(team, health, maximumHealth);
    }
    
    /* (non-Javadoc)
     * @see rmi.client.IClientController#onGameEnd(int)
     */
    @Override
    public void onGameEnd(final int winner)
    {
        _events.onGameEnd(winner);
    }

    /* (non-Javadoc)
     * @see rmi.client.RmiClientController#close()
     */
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
}
