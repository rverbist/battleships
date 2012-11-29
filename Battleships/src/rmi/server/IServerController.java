package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import domain.Location;
import domain.Player;

/**
 * this is the interface that the battleships server exposes to the clients
 * @author rverbist
 */
public interface IServerController extends Remote
{
    /**
     * connect to the server as a player with the given name
     * @param host the host name of the client
     * @param port the port of the client
     * @param resource the java rmi resource name of the client
     * @param name the name of the player
     * @throws RemoteException if an java rmi related exception occurs
     */
    void connect(final String host, final int port, final String resource, final String name) throws RemoteException;
    
    /**
     * disconnect the given player from the server
     * @param player the player to disconnect
     * @throws RemoteException if an java rmi related exception occurs
     */
    void disconnect(final Player player) throws RemoteException;
    
    /**
     * emits a global chat message
     * @param player the player who sent the global chat message
     * @param message the chat message
     * @throws RemoteException if an java rmi related exception occurs
     */
    void sendGlobalChatMessage(final Player player, final String message) throws RemoteException;
    
    /**
     * join the given team
     * @param player the player that joins the team
     * @param team the index of the team to join
     * @throws RemoteException if an java rmi related exception occurs
     */
    void joinTeam(final Player player, final int team) throws RemoteException;
    
    /**
     * leave the team
     * @param player the player that leaves the team
     * @throws RemoteException if an java rmi related exception occurs
     */
    void leaveTeam(final Player player) throws RemoteException;
    
    /**
     * emits a team chat message
     * @param player the player who sent the team chat message
     * @param message the chat message
     * @throws RemoteException if an java rmi related exception occurs
     */
    void sendTeamChatMessage(final Player player, final String message) throws RemoteException;
   
    /**
     * changes the ready status of the player
     * @param player the player whose ready status to change
     * @param isReady the new ready status
     * @throws RemoteException if an java rmi related exception occurs
     */
    void setReady(final Player player, final boolean isReady) throws RemoteException;
    
    /**
     * add a suggestion
     * @param player the player that performs the suggestion
     * @param suggestion the suggested location
     * @throws RemoteException if an java rmi related exception occurs
     */
    void addSuggestion(final Player player, final Location suggestion) throws RemoteException;
    
    /**
     * remove a suggestion
     * @param player the player that performs the suggestion
     * @throws RemoteException if an java rmi related exception occurs
     */
    void removeSuggestion(final Player player) throws RemoteException;
    
    /**
     * gets the name of a team
     * @param team the index of the team
     * @return the name of the team at index
     * @throws RemoteException if an java rmi related exception occurs
     */
    String getTeamName(final int team) throws RemoteException;
    
    /**
     * gets the players of a team
     * @param team the index of the team
     * @return the players of the team at index
     * @throws RemoteException if an java rmi related exception occurs
     */
    Set<Player> getTeamPlayers(final int team) throws RemoteException;
    
    /**
     * gets the players that haven't been assigned a team
     * @return the players that haven't been assigned a team
     * @throws RemoteException if an java rmi related exception occurs
     */
    Set<Player> getUnassignedPlayers() throws RemoteException;
}
