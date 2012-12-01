package rmi.client.events;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public interface IClientGameEventListener
{
    /**
     * occurs when the local player has successfully connected to the server
     * @param player the player that has connected
     */
    void onConnected(final Player player);
    
    /**
     * occurs when the local player has successfully disconnected from the server
     * @param player the player that has disconnected
     */
    void onDisconnected(final Player player);
    
    /**
     * occurs when a global chat message has been emitted
     * @param message the chat message
     */
    void onGlobalChatMessage(final String message);
    
    /**
     * occurs when a player has been assigned a team
     * @param player the player that has been assigned a team
     */
    void onPlayerAssigned(final Player player);
    
    /**
     * occurs when a player has been unassigned from a team
     * @param player the player that has been unassigned from a team
     */
    void onPlayerUnassigned(final Player player);
    
    /**
     * occurs when a player joined a team
     * @param player the player that joined a team
     * @param team the team index
     */
    void onPlayerJoinedTeam(final Player player, final int team);
    
    /**
     * occurs when a player left a team
     * @param player the player that left a team
     */
    void onPlayerLeftTeam(final Player player);
    
    /**
     * occurs when a team chat message has been emitted
     * @param message the chat message
     */
    void onTeamChatMessage(final String message);
    
    /**
     * occurs when the ready status of a player has changed
     * @param player the player whose ready status has changed
     */
    void onPlayerIsReadyChanged(final Player player);
    
    /**
     * occurs when the game starts, the definitive team id for the current player is given
     * @param team the team index of the local player
     * @param board the board of the team of the local player
     */
    void onGameStart(final int team, final Board board);
    
    /**
     * occurs when a new turn starts
     * @param team the index of the team whose turn it is
     */
    void onGameTurnStart(final int team);
    
    /**
     * occurs when a player in the team of the local player makes a suggestion
     * @param player the player that makes the suggestion
     * @param location the location of the suggestion
     */
    void onPlayerAddSuggestion(final Player player, final Location location);
    
    /**
     * occurs when a player in the team of the local player removes a suggestion
     * @param player the player that removes the suggestion
     */
    void onPlayerRemoveSuggestion(final Player player);
    
    /**
     * occurs when a team makes a successful missile hit
     * @param team the team index of the team that got hit
     * @param health the health of the team that got hit
     * @param maximumHealth the maximum health of the team that got hit
     */
    void onTeamHit(final int team, final int health, final int maximumHealth);
    
    /**
     * occurs when a turn ends
     * @param turn the index of the team whose turn has ended
     * @param slot the condition of the map slot that got fired upon
     * @param location the location of the map slot that got fired upon
     */
    void onGameTurnEnd(final int turn, final MapSlot slot, final Location location);

    /**
     * occurs when the game has ended and a winner has been declared
     * @param winner the index of the winning team
     */
    void onGameEnd(final int winner);
}