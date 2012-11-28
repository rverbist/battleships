package rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import domain.Board;
import domain.Location;
import domain.MapSlot;
import domain.Player;

public interface IClientController extends Remote
{
    /*
     * EVENTS
     *  these methods will be called by the server, once handled by
     *  the client controller they are delegated to the corresponding
     *  method in IClientGameEventListener
     */
    void onConnected(final Player player) throws RemoteException;
    void onDisconnected(final Player player) throws RemoteException;
    void onGlobalChatMessage(final String message) throws RemoteException;
    void onPlayerAssigned(final Player player) throws RemoteException;
    void onPlayerUnassigned(final Player player) throws RemoteException;
    void onPlayerJoinedTeam(final Player player, final int team) throws RemoteException;
    void onPlayerLeftTeam(final Player player) throws RemoteException;
    void onTeamChatMessage(final String message) throws RemoteException;
    void onPlayerIsReadyChanged(final Player player) throws RemoteException;
    void onGameStart(final int team, final Board board) throws RemoteException;
    void onGameTurnStart(final int team) throws RemoteException;
    void onPlayerAddSuggestion(final Player player, final Location location) throws RemoteException;
    void onPlayerRemoveSuggestion(final Player player) throws RemoteException;
    void onTeamHit(final int team, final int health, final int maximumHealth) throws RemoteException;
    void onGameTurnEnd(final int turn, final MapSlot slot, final Location location) throws RemoteException;
}