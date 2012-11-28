package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import domain.Location;
import domain.Player;

public interface IServerController extends Remote
{
    /*
     * OPERATIONS
     */
    void connect(final String host, final int port, final String resource, final String name) throws RemoteException;
    void disconnect(final Player player) throws RemoteException;
    void sendGlobalChatMessage(final Player player, final String message) throws RemoteException;
    void joinTeam(final Player player, final int team) throws RemoteException;
    void leaveTeam(final Player player) throws RemoteException;
    void sendTeamChatMessage(final Player player, final String message) throws RemoteException;
    void setReady(final Player player, final boolean isReady) throws RemoteException;
    void addSuggestion(final Player player, final Location suggestion) throws RemoteException;
    void removeSuggestion(final Player player) throws RemoteException;
    
    /*
     * GETTERS
     */
    String getTeamName(final int team) throws RemoteException;
    Set<Player> getTeamPlayers(final int team) throws RemoteException;
    Set<Player> getUnassignedPlayers() throws RemoteException;
}
