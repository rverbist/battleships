package rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

/**
 * this is the interface that the battleships client exposes to the server
 * @author rverbist
 */
public interface IClientController extends Remote
{
    /**
     * occurs when the local player has successfully connected to the server
     * @param player the player that has connected
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onConnected(final Player player) throws RemoteException;
    
    /**
     * occurs when the local player has successfully disconnected from the server
     * @param player the player that has disconnected
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onDisconnected(final Player player) throws RemoteException;
    
    /**
     * occurs when a global chat message has been emitted
     * @param message the chat message
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onGlobalChatMessage(final String message) throws RemoteException;
    
    /**
     * occurs when a player has been assigned a team
     * @param player the player that has been assigned a team
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerAssigned(final Player player) throws RemoteException;
    
    /**
     * occurs when a player has been unassigned from a team
     * @param player the player that has been unassigned from a team
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerUnassigned(final Player player) throws RemoteException;
    
    /**
     * occurs when a player joined a team
     * @param player the player that joined a team
     * @param team the team index
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException;
    
    /**
     * occurs when a player left a team
     * @param player the player that left a team
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerLeftTeam(final Player player) throws RemoteException;
    
    /**
     * occurs when a team chat message has been emitted
     * @param message the chat message
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onTeamChatMessage(final String message) throws RemoteException;
    
    /**
     * occurs when the ready status of a player has changed
     * @param player the player whose ready status has changed
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerIsReadyChanged(final Player player) throws RemoteException;
    
    /**
     * occurs when the game starts, the definitive team id for the current player is given
     * @param team the team index of the local player
     * @param board the board of the team of the local player
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onGameStart(final int team, final Board board) throws RemoteException;
    
    /**
     * occurs when a new turn starts
     * @param team the index of the team whose turn it is
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onGameTurnStart(final int team) throws RemoteException;
    
    /**
     * occurs when a player in the team of the local player makes a suggestion
     * @param player the player that makes the suggestion
     * @param location the location of the suggestion
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException;
    
    /**
     * occurs when a player in the team of the local player removes a suggestion
     * @param player the player that removes the suggestion
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onPlayerRemoveSuggestion(final Player player) throws RemoteException;
    
    /**
     * occurs when a team makes a successful missile hit
     * @param team the team index of the team that got hit
     * @param health the health of the team that got hit
     * @param maximumHealth the maximum health of the team that got hit
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException;
    
    /**
     * occurs when a turn ends
     * @param turn the index of the team whose turn has ended
     * @param slot the condition of the map slot that got fired upon
     * @param location the location of the map slot that got fired upon
     * @throws RemoteException if an java rmi related exception occurs
     */
    void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException;
}