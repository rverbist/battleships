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

/**
 * a concrete implementation of a battleships server
 * @author rverbist
 */
public final class ServerGameController extends RmiServerController
{
    private Game _game;

    public ServerGameController(final String host, final int port) throws IOException, AlreadyBoundException
    {
        super(host, port);
        newGame();
    }

    /**
     * starts a new game
     */
    private void newGame()
    {
        _game = new Game();
    }

    /**
     * gets a multicast interface for all connected clients
     * @return a client interface whose methods delegate to all connected clients
     * @see ClientControllerMultiCast
     */
    protected final IClientController getClients()
    {
        return new ClientControllerMultiCast(_clients.values());
    }

    /**
     * gets a multicast interface for all clients that are in a team
     * @return a client interface whose methods delegate to all clients that are in a team
     * @see ClientControllerMultiCast
     */
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

    /**
     * gets a multicast interface for all clients in the given team
     * @param team the team to include in the multicast
     * @return a client interface whose methods delegate to all clients in the given team
     * @see ClientControllerMultiCast
     */
    protected final IClientController getClientsInTeam(final Team team)
    {
        final List<RmiClient<IClientController>> players = new ArrayList<RmiClient<IClientController>>();
        for (final Player player : team.getPlayers())
        {
            players.add(_clients.get(player));
        }
        return new ClientControllerMultiCast(players);
    }

    /* (non-Javadoc)
     * @see rmi.server.RmiServerController#createPlayer(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see rmi.server.RmiServerController#removePlayer(domain.Player)
     */
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

    /* (non-Javadoc)
     * @see rmi.server.IServerController#getTeamName(int)
     */
    @Override
    public String getTeamName(final int team) throws RemoteException
    {
        return _game.getTeam(team).getName();
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#getTeamPlayers(int)
     */
    @Override
    public Set<Player> getTeamPlayers(final int team) throws RemoteException
    {
        return _game.getTeam(team).getPlayers();
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#getUnassignedPlayers()
     */
    @Override
    public Set<Player> getUnassignedPlayers() throws RemoteException
    {
        return _game.getUnassignedPlayers();
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#sendGlobalChatMessage(domain.Player, java.lang.String)
     */
    @Override
    public void sendGlobalChatMessage(final Player player, final String message) throws RemoteException
    {
        final String msg = String.format("%s says: %s", player.getName(), message);
        // fire the onGlobalChatMessage event for all players
        getClients().onGlobalChatMessage(msg);
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#joinTeam(domain.Player, int)
     */
    @Override
    public void joinTeam(Player player, final int team) throws RemoteException
    {
        // capture a reference the the server local instance of the player
        player = _game.getLocalPlayer(player);
        // join the team
        if (player != null && _game.joinTeam(player, team))
        {
            // set the ready status of the player to false
            if (_game.setReady(player, false))
            {
                // set the ready status of the player to false so that the player
                // can't accidently start a game by changing teams
                getClients().onPlayerIsReadyChanged(player);
            }
            // fire the onPlayerAssigned event for all players
            getClients().onPlayerAssigned(player);
            // fire the onPlayerJoinedTeam event for all players
            getClients().onPlayerJoinedTeam(player, team);
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#leaveTeam(domain.Player)
     */
    @Override
    public void leaveTeam(Player player) throws RemoteException
    {
        // capture a reference the the server local instance of the player
        player = _game.getLocalPlayer(player);
        // leave the team
        if (player != null && _game.leaveTeam(player))
        {
            // set the ready status of the player to false so that the player
            // can't accidently start a game by changing teams
            if (_game.setReady(player, false))
            {
                // fire the onPlayerIsReadyChanged for all players
                getClients().onPlayerIsReadyChanged(player);
            }
            // fire the onPlayerUnassigned event for all players
            getClients().onPlayerUnassigned(player);
            // fire the onPlayerLeftTeam event for all players
            getClients().onPlayerLeftTeam(player);
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#sendTeamChatMessage(domain.Player, java.lang.String)
     */
    @Override
    public void sendTeamChatMessage(final Player player, final String message) throws RemoteException
    {
        final Team team = _game.getTeamForPlayer(player);
        if (team != null)
        {
            final String msg = String.format("%s says: %s", player.getName(), message);
            // fire the onPlayerLeftTeam event for the entire team
            getClientsInTeam(team).onTeamChatMessage(msg);
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#setReady(domain.Player, boolean)
     */
    @Override
    public void setReady(Player player, final boolean isReady) throws RemoteException
    {
        // capture a reference the the server local instance of the player
        player = _game.getLocalPlayer(player);
        // change the ready status of the player
        if (player != null && _game.setReady(player, isReady))
        {
            // fire the onPlayerLeftTeam event for all players
            getClients().onPlayerIsReadyChanged(player);
            // if every player has changed their status to ready, the game can start
            if (_game.isReady())
            {
                // notify each team that the game has started
                for (final Team team : _game.getTeams())
                {
                    // fire the getClientsInTeam event for all players in this team
                    getClientsInTeam(team).onGameStart(_game.getTeamIndex(team), team.getBoard());
                }
                // fire the onGameTurnStart for all players in a team
                getClientsInTeam().onGameTurnStart(_game.nextTurn());
            }
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#addSuggestion(domain.Player, domain.Location)
     */
    @Override
    public void addSuggestion(Player player, final Location suggestion) throws RemoteException
    {
        // capture a reference the the server local instance of the player
        player = _game.getLocalPlayer(player);
        // add the suggestion
        if (player != null && _game.addTeamSuggestion(player, suggestion))
        {
            final Team team = _game.getTeamForPlayer(player);
            // fire the onPlayerAddSuggestion for all players in this team
            getClientsInTeam(team).onPlayerAddSuggestion(player, suggestion);
            // if every team member has suggested a location, the game can fire
            // a missile on the suggested location
            final Location target = team.getSuggestedLocation();
            if (target != null)
            {
                // get the opposing team
                final Team opposition = _game.getOpposingTeamForPlayer(player);
                // launch a missile at the proposed location
                final MapSlot slot = opposition.fire(target);
                if (slot == MapSlot.Hit)
                {
                    // fire the onTeamHit event for all players in a team
                    getClientsInTeam().onTeamHit(_game.getTeamIndex(opposition), opposition.getHealth(), opposition.getMaximumHealth());
                }
                // clear suggestions
                team.clearSuggestions();
                if (_game.isFinished())
                {
                    final Team winner = _game.getWinningTeam();
                    getClientsInTeam().onGameEnd(_game.getTeamIndex(winner));
                }
                else
                {
                    // end the current turn
                    // fire the onGameTurnEnd event for all players in a team
                    getClientsInTeam().onGameTurnEnd(_game.getTeamIndex(team), slot, target);
                    // start a new turn
                    // fire the onGameTurnStart event for all players in a team
                    getClientsInTeam().onGameTurnStart(_game.nextTurn());
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see rmi.server.IServerController#removeSuggestion(domain.Player)
     */
    @Override
    public void removeSuggestion(Player player) throws RemoteException
    {
        // capture a reference the the server local instance of the player
        player = _game.getLocalPlayer(player);
        // remove the suggestion
        if (player != null && _game.removeTeamSuggestion(player))
        {
            final Team team = _game.getTeamForPlayer(player);
            // fire the onPlayerRemoveSuggestion for all players in this team
            getClientsInTeam(team).onPlayerRemoveSuggestion(player);
        }
    }
}
